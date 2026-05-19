package com.tobias.controller.activity;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.tobias.application.FlashMessage;
import com.tobias.dao.ActivityDAO;
import com.tobias.dao.DisciplineDAO;
import com.tobias.dao.QuestionDAO;
import com.tobias.dao.ReportDAO;
import com.tobias.dao.StudentDAO;
import com.tobias.dao.SubmissionDAO;
import com.tobias.dao.TeacherDAO;
import com.tobias.model.Activity;
import com.tobias.model.ActivitySubmission;
import com.tobias.model.Aluno;
import com.tobias.model.Questoes;
import com.tobias.model.QuestoesAbertas;
import com.tobias.model.QuestoesFechadas;
import com.tobias.model.Report;
import com.tobias.model.SubmissionAnswer;
import com.tobias.model.User;
import com.tobias.service.AiAssessmentService;
import com.tobias.service.PdfReportService;

@WebServlet({
    "/Activity"
})
public class ActivityController extends HttpServlet {

    private ActivityDAO dao = new ActivityDAO();
    private QuestionDAO questionDAO = new QuestionDAO();
    private DisciplineDAO disciplineDAO = new DisciplineDAO();
    private StudentDAO studentDAO = new StudentDAO();
    private TeacherDAO teacherDAO = new TeacherDAO();
    private SubmissionDAO submissionDAO = new SubmissionDAO();
    private ReportDAO reportDAO = new ReportDAO();
    private AiAssessmentService aiAssessmentService = new AiAssessmentService();
    private PdfReportService pdfReportService = new PdfReportService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        FlashMessage.get(request);

        switch (action == null ? "" : action) {
            case "new":
                int disciplineId = Integer.parseInt(request.getParameter("disciplineId"));
                if (!isProfessor(request, disciplineId)) {
                    FlashMessage.set(request, "danger", "Somente o professor pode criar atividades.");
                    response.sendRedirect(request.getContextPath() + "/Disciplines?action=view&id=" + disciplineId + "&tab=atividades");
                    return;
                }
                request.setAttribute("pageHeading", "Nova Atividade");
                request.setAttribute("contentPage", "/WEB-INF/templates/activity/form.jsp");
                request.setAttribute("pageJs", "/assets/js/activity.js");
                break;
            case "view":
                if (!showActivityDetails(request, response)) {
                    return;
                }
                break;
            case "edit":
                showActivityEditForm(request);
                break;
            case "edit-question":
                showQuestionEditForm(request);
                break;
            case "submission":
                if (!showSubmissionDetails(request, response)) {
                    return;
                }
                break;
            case "download-report":
                downloadReport(request, response);
                return;
            default:
                request.setAttribute("pageHeading", "Atividades");
                request.setAttribute("contentPage", "/WEB-INF/templates/activity/activity.jsp");
                break;
        }

        request.setAttribute("pageTitle", "Gestão Acadêmica");
        request.setAttribute("pageCss", "/assets/css/disciplines.css");
        request.getRequestDispatcher("/WEB-INF/templates/layout/base.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        switch (action == null ? "" : action) {
            case "new":
                createActivity(request, response);
                break;
            case "update":
                updateActivity(request, response);
                break;
            case "delete":
                deleteActivity(request, response);
                break;
            case "new-question":
                createQuestion(request, response);
                break;
            case "update-question":
                updateQuestion(request, response);
                break;
            case "delete-question":
                deleteQuestion(request, response);
                break;
            case "submit":
                submitActivity(request, response);
                break;
            case "generate-report":
                generateReport(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/Activity");
                break;
        }
    }

    private boolean showActivityDetails(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int activityId = Integer.parseInt(request.getParameter("id"));
        Activity activity = dao.getById(activityId);
        User user = getLoggedUser(request);
        User participant = activity != null && user != null
                ? resolveParticipant(activity.getIdDiscipline(), user)
                : null;

        if (activity == null || participant == null) {
            FlashMessage.set(request, "danger", "Você não tem acesso a essa atividade.");
            response.sendRedirect(request.getContextPath() + "/Disciplines");
            return false;
        }

        List<Questoes> questions = questionDAO.listByActivity(activityId);
        boolean hasSubmission = false;
        List<ActivitySubmission> submissions = null;

        if (participant.canSubmitActivity() && participant instanceof Aluno) {
            hasSubmission = submissionDAO.hasSubmission(activityId, ((Aluno) participant).getStudentId());
        }

        if (participant.canManageDiscipline()) {
            submissions = submissionDAO.listByActivity(activityId);
        }

        request.setAttribute("activity", activity);
        request.setAttribute("questions", questions);
        request.setAttribute("submissions", submissions);
        request.setAttribute("participant", participant);
        request.setAttribute("userRole", participant.getRoleName());
        request.setAttribute("hasSubmission", hasSubmission);
        request.setAttribute("pageHeading", "Detalhes da Atividade");
        request.setAttribute("pageJs", "/assets/js/activity.js");
        request.setAttribute("contentPage", "/WEB-INF/templates/activity/details.jsp");
        return true;
    }

    private void showActivityEditForm(HttpServletRequest request) {
        int activityId = Integer.parseInt(request.getParameter("id"));
        Activity activity = dao.getById(activityId);

        if (activity != null && !isProfessor(request, activity.getIdDiscipline())) {
            FlashMessage.set(request, "danger", "Somente o professor pode editar atividades.");
            activity = null;
        }

        request.setAttribute("activity", activity);
        request.setAttribute("pageHeading", "Editar Atividade");
        request.setAttribute("contentPage", "/WEB-INF/templates/activity/edit.jsp");
    }

    private void showQuestionEditForm(HttpServletRequest request) {
        int questionId = Integer.parseInt(request.getParameter("id"));
        Questoes question = questionDAO.getById(questionId);

        if (question != null) {
            Activity activity = dao.getById(question.getIdActivity());
            if (activity != null && !isProfessor(request, activity.getIdDiscipline())) {
                FlashMessage.set(request, "danger", "Somente o professor pode editar questões.");
                question = null;
            }
        }

        request.setAttribute("question", question);
        request.setAttribute("pageHeading", "Editar Questão");
        request.setAttribute("pageJs", "/assets/js/activity.js");
        request.setAttribute("contentPage", "/WEB-INF/templates/activity/question_edit.jsp");
    }

    private boolean showSubmissionDetails(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int submissionId = Integer.parseInt(request.getParameter("id"));
        ActivitySubmission submission = submissionDAO.getById(submissionId);
        Activity activity = submission != null ? dao.getById(submission.getActivityId()) : null;

        if (submission == null || activity == null || !isProfessor(request, activity.getIdDiscipline())) {
            FlashMessage.set(request, "danger", "Somente o professor da disciplina pode ver as respostas.");
            response.sendRedirect(request.getContextPath() + "/Disciplines");
            return false;
        }

        List<SubmissionAnswer> answers = submissionDAO.listAnswersBySubmission(submissionId);
        Report report = reportDAO.getBySubmissionId(submissionId);

        request.setAttribute("activity", activity);
        request.setAttribute("submission", submission);
        request.setAttribute("answers", answers);
        request.setAttribute("report", report);
        request.setAttribute("pageHeading", "Respostas do Aluno");
        request.setAttribute("contentPage", "/WEB-INF/templates/activity/submission_details.jsp");
        return true;
    }

    private void generateReport(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int submissionId = Integer.parseInt(request.getParameter("submissionId"));
        ActivitySubmission submission = submissionDAO.getById(submissionId);
        Activity activity = submission != null ? dao.getById(submission.getActivityId()) : null;

        if (submission == null || activity == null || !isProfessor(request, activity.getIdDiscipline())) {
            FlashMessage.set(request, "danger", "Somente o professor da disciplina pode gerar relatórios.");
            response.sendRedirect(request.getContextPath() + "/Disciplines");
            return;
        }

        List<SubmissionAnswer> answers = submissionDAO.listAnswersBySubmission(submissionId);
        if (answers.isEmpty()) {
            FlashMessage.set(request, "danger", "Esta entrega não tem respostas para analisar.");
            response.sendRedirect(request.getContextPath() + "/Activity?action=submission&id=" + submissionId);
            return;
        }

        try {
            String assessment = aiAssessmentService.generateAssessment(activity, submission, answers);
            String title = "Relatório de desempenho - " + activity.getTitle();
            Integer reportId = reportDAO.saveForSubmission(submission.getStudentId(), submissionId, title, assessment);

            if (reportId == null) {
                FlashMessage.set(request, "danger", "A IA gerou a avaliação, mas não foi possível salvar o relatório.");
            } else {
                FlashMessage.set(request, "success", "Relatório gerado e salvo com sucesso!");
            }
        } catch (IllegalStateException e) {
            FlashMessage.set(request, "danger", "Configure a IA antes de gerar o relatório: " + e.getMessage());
        } catch (Exception e) {
            FlashMessage.set(request, "danger", "Não foi possível gerar o relatório com IA. Verifique se o Ollama está rodando e se o modelo foi baixado. Detalhe: " + e.getMessage());
        }

        response.sendRedirect(request.getContextPath() + "/Activity?action=submission&id=" + submissionId);
    }

    private void downloadReport(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int submissionId = Integer.parseInt(request.getParameter("submissionId"));
        ActivitySubmission submission = submissionDAO.getById(submissionId);
        Activity activity = submission != null ? dao.getById(submission.getActivityId()) : null;

        if (submission == null || activity == null || !isProfessor(request, activity.getIdDiscipline())) {
            FlashMessage.set(request, "danger", "Somente o professor da disciplina pode baixar relatórios.");
            response.sendRedirect(request.getContextPath() + "/Disciplines");
            return;
        }

        Report report = reportDAO.getBySubmissionId(submissionId);
        if (report == null) {
            FlashMessage.set(request, "danger", "Gere o relatório antes de baixar o PDF.");
            response.sendRedirect(request.getContextPath() + "/Activity?action=submission&id=" + submissionId);
            return;
        }

        byte[] pdf = pdfReportService.generatePdf(report, activity, submission);
        String fileName = "relatorio-" + submission.getStudentName() + "-submissao-" + submission.getId() + ".pdf";
        String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8).replace("+", "%20");

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + encodedFileName);
        response.setContentLength(pdf.length);
        response.getOutputStream().write(pdf);
    }

    private void createActivity(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int disciplineId = Integer.parseInt(request.getParameter("disciplineId"));
        if (!isProfessor(request, disciplineId)) {
            FlashMessage.set(request, "danger", "Somente o professor pode criar atividades.");
            response.sendRedirect(request.getContextPath() + "/Disciplines?action=view&id=" + disciplineId + "&tab=atividades");
            return;
        }

        Activity activity = buildActivityFromRequest(request, 0, disciplineId);
        Integer activityId = dao.createActivity(activity);

        if (activityId != null) {
            saveQuestionsFromRequest(request, activityId);
            FlashMessage.set(request, "success", "Atividade criada com sucesso!");
        } else {
            FlashMessage.set(request, "danger", "Não foi possível criar a atividade.");
        }

        response.sendRedirect(request.getContextPath() + "/Disciplines?action=view&id=" + disciplineId + "&tab=atividades");
    }

    private void updateActivity(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int activityId = Integer.parseInt(request.getParameter("activityId"));
        int disciplineId = Integer.parseInt(request.getParameter("disciplineId"));
        if (!isProfessor(request, disciplineId)) {
            FlashMessage.set(request, "danger", "Somente o professor pode atualizar atividades.");
            response.sendRedirect(request.getContextPath() + "/Activity?action=view&id=" + activityId);
            return;
        }

        Activity activity = buildActivityFromRequest(request, activityId, disciplineId);

        dao.updateActivity(activity);
        submissionDAO.deleteByActivity(activityId);
        FlashMessage.set(request, "success", "Atividade atualizada com sucesso! Os alunos podem enviar novamente.");
        response.sendRedirect(request.getContextPath() + "/Activity?action=view&id=" + activityId);
    }

    private void deleteActivity(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int activityId = Integer.parseInt(request.getParameter("activityId"));
        int disciplineId = Integer.parseInt(request.getParameter("disciplineId"));
        if (!isProfessor(request, disciplineId)) {
            FlashMessage.set(request, "danger", "Somente o professor pode excluir atividades.");
            response.sendRedirect(request.getContextPath() + "/Activity?action=view&id=" + activityId);
            return;
        }

        dao.deleteActivity(activityId);
        FlashMessage.set(request, "success", "Atividade excluída com sucesso!");
        response.sendRedirect(request.getContextPath() + "/Disciplines?action=view&id=" + disciplineId + "&tab=atividades");
    }

    private void createQuestion(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int activityId = Integer.parseInt(request.getParameter("activityId"));
        Activity activity = dao.getById(activityId);
        if (activity == null || !isProfessor(request, activity.getIdDiscipline())) {
            FlashMessage.set(request, "danger", "Somente o professor pode criar questões.");
            response.sendRedirect(request.getContextPath() + "/Activity?action=view&id=" + activityId);
            return;
        }

        Questoes question = buildQuestionFromRequest(request, 0, activityId);

        questionDAO.createQuestion(question);
        submissionDAO.deleteByActivity(activityId);
        FlashMessage.set(request, "success", "Questão criada com sucesso! Os alunos podem enviar novamente.");
        response.sendRedirect(request.getContextPath() + "/Activity?action=view&id=" + activityId);
    }

    private void updateQuestion(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int questionId = Integer.parseInt(request.getParameter("questionId"));
        int activityId = Integer.parseInt(request.getParameter("activityId"));
        Activity activity = dao.getById(activityId);
        if (activity == null || !isProfessor(request, activity.getIdDiscipline())) {
            FlashMessage.set(request, "danger", "Somente o professor pode atualizar questões.");
            response.sendRedirect(request.getContextPath() + "/Activity?action=view&id=" + activityId);
            return;
        }

        Questoes question = buildQuestionFromRequest(request, questionId, activityId);

        questionDAO.updateQuestion(question);
        submissionDAO.deleteByActivity(activityId);
        FlashMessage.set(request, "success", "Questão atualizada com sucesso! Os alunos podem enviar novamente.");
        response.sendRedirect(request.getContextPath() + "/Activity?action=view&id=" + activityId);
    }

    private void deleteQuestion(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int questionId = Integer.parseInt(request.getParameter("questionId"));
        int activityId = Integer.parseInt(request.getParameter("activityId"));
        Activity activity = dao.getById(activityId);
        if (activity == null || !isProfessor(request, activity.getIdDiscipline())) {
            FlashMessage.set(request, "danger", "Somente o professor pode excluir questões.");
            response.sendRedirect(request.getContextPath() + "/Activity?action=view&id=" + activityId);
            return;
        }

        questionDAO.deleteQuestion(questionId);
        submissionDAO.deleteByActivity(activityId);
        FlashMessage.set(request, "success", "Questão excluída com sucesso! Os alunos podem enviar novamente.");
        response.sendRedirect(request.getContextPath() + "/Activity?action=view&id=" + activityId);
    }

    private void submitActivity(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int activityId = Integer.parseInt(request.getParameter("activityId"));
        Activity activity = dao.getById(activityId);
        User user = getLoggedUser(request);
        User participant = activity != null && user != null
                ? resolveParticipant(activity.getIdDiscipline(), user)
                : null;

        if (activity == null || !(participant instanceof Aluno) || !participant.canSubmitActivity()) {
            FlashMessage.set(request, "danger", "Somente alunos da disciplina podem enviar a atividade.");
            response.sendRedirect(request.getContextPath() + "/Activity?action=view&id=" + activityId);
            return;
        }

        int studentId = ((Aluno) participant).getStudentId();
        if (submissionDAO.hasSubmission(activityId, studentId)) {
            FlashMessage.set(request, "danger", "Você já enviou essa atividade.");
            response.sendRedirect(request.getContextPath() + "/Activity?action=view&id=" + activityId);
            return;
        }

        List<Questoes> questions = questionDAO.listByActivity(activityId);
        Map<Integer, String> answers = new LinkedHashMap<>();

        for (Questoes question : questions) {
            String answer = request.getParameter("answer_" + question.getId());
            if (answer != null && !answer.isBlank()) {
                answers.put(question.getId(), answer.trim());
            }
        }

        if (answers.isEmpty()) {
            FlashMessage.set(request, "danger", "Preencha pelo menos uma resposta antes de enviar.");
            response.sendRedirect(request.getContextPath() + "/Activity?action=view&id=" + activityId);
            return;
        }

        Integer submissionId = submissionDAO.createSubmission(activityId, studentId, answers);
        if (submissionId != null) {
            FlashMessage.set(request, "success", "Atividade enviada com sucesso!");
        } else {
            FlashMessage.set(request, "danger", "Não foi possível enviar a atividade.");
        }

        response.sendRedirect(request.getContextPath() + "/Activity?action=view&id=" + activityId);
    }

    private Activity buildActivityFromRequest(HttpServletRequest request, int activityId, int disciplineId) {
        String title = request.getParameter("title");
        LocalDate submitDate = LocalDate.parse(request.getParameter("submitDate"));
        String deliveryDateValue = request.getParameter("deliveryDate");
        LocalDate deliveryDate = deliveryDateValue == null || deliveryDateValue.isBlank()
                ? null
                : LocalDate.parse(deliveryDateValue);
        float weight = parseFloat(request.getParameter("weight"));

        return new Activity(title, submitDate, deliveryDate, weight, activityId, disciplineId);
    }

    private Questoes buildQuestionFromRequest(HttpServletRequest request, int questionId, int activityId) {
        String text = request.getParameter("questionText");
        float weight = parseFloat(request.getParameter("questionWeight"));
        String type = request.getParameter("questionType");

        if ("FECHADA".equals(type)) {
            return new QuestoesFechadas(
                    questionId,
                    weight,
                    text,
                    activityId,
                    normalizeOptionLetter(request.getParameter("correctOption")),
                    request.getParameter("optionA"),
                    request.getParameter("optionB"),
                    request.getParameter("optionC"),
                    request.getParameter("optionD"));
        }

        return new QuestoesAbertas(questionId, weight, text, activityId, request.getParameter("expectedAnswer"));
    }

    private void saveQuestionsFromRequest(HttpServletRequest request, int activityId) {
        String[] questionTexts = request.getParameterValues("questionText");
        String[] questionWeights = request.getParameterValues("questionWeight");
        String[] questionTypes = request.getParameterValues("questionType");
        String[] expectedAnswers = request.getParameterValues("expectedAnswer");
        String[] correctOptions = request.getParameterValues("correctOption");
        String[] optionsA = request.getParameterValues("optionA");
        String[] optionsB = request.getParameterValues("optionB");
        String[] optionsC = request.getParameterValues("optionC");
        String[] optionsD = request.getParameterValues("optionD");

        if (questionTexts == null) {
            return;
        }

        for (int i = 0; i < questionTexts.length; i++) {
            String questionText = questionTexts[i];

            if (questionText == null || questionText.isBlank()) {
                continue;
            }

            float questionWeight = questionWeights != null && i < questionWeights.length
                    ? parseFloat(questionWeights[i])
                    : 0;

            String questionType = valueAt(questionTypes, i);
            Questoes question;

            if ("FECHADA".equals(questionType)) {
                question = new QuestoesFechadas(
                        0,
                        questionWeight,
                        questionText,
                        activityId,
                        normalizeOptionLetter(valueAt(correctOptions, i)),
                        valueAt(optionsA, i),
                        valueAt(optionsB, i),
                        valueAt(optionsC, i),
                        valueAt(optionsD, i));
            } else {
                question = new QuestoesAbertas(
                        0,
                        questionWeight,
                        questionText,
                        activityId,
                        valueAt(expectedAnswers, i));
            }

            questionDAO.createQuestion(question);
        }
    }

    private String valueAt(String[] values, int index) {
        return values != null && index < values.length ? values[index] : null;
    }

    private String normalizeOptionLetter(String value) {
        return value == null || value.isBlank() ? "A" : value.trim().toUpperCase();
    }

    private float parseFloat(String value) {
        if (value == null || value.isBlank()) {
            return 0;
        }

        return Float.parseFloat(value);
    }

    private boolean isProfessor(HttpServletRequest request, int disciplineId) {
        User user = getLoggedUser(request);
        User participant = user != null ? resolveParticipant(disciplineId, user) : null;
        return participant != null && participant.canManageDiscipline();
    }

    private User resolveParticipant(int disciplineId, User user) {
        String role = disciplineDAO.getUserRole(disciplineId, user.getId());

        if ("PROFESSOR".equals(role)) {
            return teacherDAO.getProfessorByUserId(user.getId());
        }

        if ("ALUNO".equals(role)) {
            return studentDAO.getAlunoByUserId(user.getId());
        }

        return null;
    }

    private User getLoggedUser(HttpServletRequest request) {
        return (User) request.getSession().getAttribute("usuarioLogado");
    }
}
