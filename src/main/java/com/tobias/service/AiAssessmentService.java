package com.tobias.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tobias.config.AiConfig;
import com.tobias.model.Activity;
import com.tobias.model.ActivitySubmission;
import com.tobias.model.SubmissionAnswer;

public class AiAssessmentService {
    private static final String OPENAI_RESPONSES_URL = "https://api.openai.com/v1/responses";
    private static final String INSTRUCTIONS = "Voce e um assistente pedagogico. Gere um relatorio em Markdown, em portugues do Brasil, " +
            "claro, respeitoso e acionavel para o professor. Analise dominio do conteudo, erros recorrentes, pontos fortes, " +
            "lacunas de aprendizagem e sugestoes de intervencao. Nao invente notas numericas se elas nao forem fornecidas.";

    private final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(20))
            .build();
    private final ObjectMapper mapper = new ObjectMapper();

    public String generateAssessment(Activity activity, ActivitySubmission submission, List<SubmissionAnswer> answers)
            throws IOException, InterruptedException {
        String apiKey = AiConfig.getOpenAiApiKey();

        if (apiKey != null && !apiKey.isBlank()) {
            return generateWithOpenAi(apiKey, activity, submission, answers);
        }

        return generateWithOllama(activity, submission, answers);
    }

    private String generateWithOpenAi(String apiKey, Activity activity, ActivitySubmission submission, List<SubmissionAnswer> answers)
            throws IOException, InterruptedException {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("model", AiConfig.getOpenAiModel());
        payload.put("instructions", INSTRUCTIONS);
        payload.put("input", buildPrompt(activity, submission, answers));
        payload.put("max_output_tokens", 1600);
        payload.put("text", Map.of("format", Map.of("type", "text")));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(OPENAI_RESPONSES_URL))
                .timeout(Duration.ofSeconds(60))
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(payload)))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            throw new IOException("Erro da API de IA: HTTP " + response.statusCode() + " - " + response.body());
        }

        String text = extractOutputText(response.body());
        if (text == null || text.isBlank()) {
            throw new IOException("A API de IA nao retornou texto de avaliacao.");
        }

        return text.trim();
    }

    private String generateWithOllama(Activity activity, ActivitySubmission submission, List<SubmissionAnswer> answers)
            throws IOException, InterruptedException {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("model", AiConfig.getOllamaModel());
        payload.put("prompt", INSTRUCTIONS + "\n\n" + buildPrompt(activity, submission, answers));
        payload.put("stream", false);

        String baseUrl = AiConfig.getOllamaBaseUrl().replaceAll("/+$", "");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/api/generate"))
                .timeout(Duration.ofSeconds(120))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(payload)))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            throw new IOException("Erro do Ollama: HTTP " + response.statusCode() + " - " + response.body());
        }

        JsonNode root = mapper.readTree(response.body());
        JsonNode generated = root.get("response");
        if (generated == null || generated.asText().isBlank()) {
            throw new IOException("O Ollama nao retornou texto de avaliacao.");
        }

        return generated.asText().trim();
    }

    private String buildPrompt(Activity activity, ActivitySubmission submission, List<SubmissionAnswer> answers) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("# Contexto da avaliacao\n");
        prompt.append("Atividade: ").append(activity.getTitle()).append('\n');
        prompt.append("Peso da atividade: ").append(activity.getPeso()).append('\n');
        prompt.append("Aluno: ").append(submission.getStudentName()).append(" <").append(submission.getStudentEmail()).append(">\n");
        prompt.append("Data de envio: ").append(submission.getSubmittedAt()).append("\n\n");
        prompt.append("# Respostas do aluno\n");

        int index = 1;
        for (SubmissionAnswer answer : answers) {
            prompt.append("## Questao ").append(index++).append('\n');
            prompt.append("Tipo: ").append(answer.getQuestionType()).append('\n');
            prompt.append("Peso: ").append(answer.getQuestionWeight()).append('\n');
            prompt.append("Enunciado: ").append(answer.getQuestionText()).append('\n');

            if ("FECHADA".equals(answer.getQuestionType())) {
                prompt.append("Alternativas:\n");
                prompt.append("A) ").append(nullToEmpty(answer.getOptionA())).append('\n');
                prompt.append("B) ").append(nullToEmpty(answer.getOptionB())).append('\n');
                prompt.append("C) ").append(nullToEmpty(answer.getOptionC())).append('\n');
                prompt.append("D) ").append(nullToEmpty(answer.getOptionD())).append('\n');
                prompt.append("Alternativa correta: ").append(nullToEmpty(answer.getCorrectOption())).append('\n');
            } else {
                prompt.append("Resposta esperada pelo professor: ").append(nullToEmpty(answer.getExpectedAnswer())).append('\n');
            }

            prompt.append("Resposta do aluno: ").append(nullToEmpty(answer.getAnswerText())).append("\n\n");
        }

        prompt.append("# Formato esperado\n");
        prompt.append("Use os titulos: Resumo geral, Pontos fortes, Dificuldades observadas, Analise por questao, Recomendacoes pedagogicas e Proximos passos.\n");
        prompt.append("Evite linguagem punitiva. Seja objetivo e util para o professor.\n");

        return prompt.toString();
    }

    private String extractOutputText(String body) throws IOException {
        JsonNode root = mapper.readTree(body);

        JsonNode outputText = root.get("output_text");
        if (outputText != null && outputText.isTextual()) {
            return outputText.asText();
        }

        JsonNode output = root.get("output");
        if (output != null && output.isArray()) {
            StringBuilder text = new StringBuilder();
            for (JsonNode item : output) {
                JsonNode content = item.get("content");
                if (content == null || !content.isArray()) {
                    continue;
                }

                for (JsonNode contentItem : content) {
                    JsonNode type = contentItem.get("type");
                    JsonNode value = contentItem.get("text");
                    if (type != null && "output_text".equals(type.asText()) && value != null) {
                        text.append(value.asText()).append('\n');
                    }
                }
            }
            return text.toString();
        }

        return null;
    }

    private String nullToEmpty(String value) {
        return value == null ? "" : value;
    }
}
