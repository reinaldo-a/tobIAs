package com.tobias.config;

import io.github.cdimascio.dotenv.Dotenv;

public class AiConfig {
    private static final Dotenv DOTENV = Dotenv.configure()
            .ignoreIfMalformed()
            .ignoreIfMissing()
            .load();

    private AiConfig() {
    }

    public static String getOpenAiApiKey() {
        return getEnv("OPENAI_API_KEY");
    }

    public static String getOpenAiModel() {
        String model = getEnv("OPENAI_MODEL");
        return model == null || model.isBlank() ? "gpt-4.1-mini" : model;
    }

    public static String getOllamaBaseUrl() {
        String baseUrl = getEnv("OLLAMA_BASE_URL");
        return baseUrl == null || baseUrl.isBlank() ? "http://ollama:11434" : baseUrl;
    }

    public static String getOllamaModel() {
        String model = getEnv("OLLAMA_MODEL");
        return model == null || model.isBlank() ? "llama3.2:1b" : model;
    }

    private static String getEnv(String key) {
        String systemValue = System.getenv(key);
        if (systemValue != null && !systemValue.isBlank()) {
            return systemValue;
        }

        return DOTENV.get(key);
    }
}
