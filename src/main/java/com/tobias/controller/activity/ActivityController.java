package com.tobias.controller.activity;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.tobias.application.FlashMessage;
import com.tobias.dao.ActivityDAO;
import com.tobias.dao.DisciplineDAO;
import com.tobias.dao.QuestionDAO;
import com.tobias.dao.StudentDAO;
import com.tobias.dao.SubmissionDAO;
import com.tobias.dao.TeacherDAO;
import com.tobias.model.Activity;
import com.tobias.model.ActivitySubmission;
import com.tobias.model.ClosedQuestion;
import com.tobias.model.OpenQuestion;
import com.tobias.model.Question;
import com.tobias.model.Student;
import com.tobias.model.User;

@WebServlet({"/Activity"})
public class ActivityController extends HttpServlet {

    private ActivityDAO dao = new ActivityDAO();
    private QuestionDAO questionDAO = new QuestionDAO();
    private DisciplineDAO disciplineDAO = new DisciplineDAO();
    private StudentDAO studentDAO = new StudentDAO();
    private TeacherDAO teacherDAO = new TeacherDAO();
    private SubmissionDAO submissionDAO = new SubmissionDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        FlashMessage.get(request);

        switch (action == null ? "" : action) {
            case "new":
                showCreateForm(request, response);
                break;
            case "view":
                showActivityDetails(request, response);
                return; 
            case "edit":
                showActivityEditForm(request, response);
                break;
            default:
                request.setAttribute("pageHeading", "Atividades");
                request.setAttribute("contentPage", "/WEB-INF/templates/activity/activity.jsp");
                break;
        }

        if (!response.isCommitted()) {
            request.setAttribute("pageTitle", "Gestão Acadêmica");
            request.setAttribute("pageCss", "/assets/css/disciplines.css");
            request.getRequestDispatcher("/WEB-INF/templates/layout/base.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
            default:
                response.sendRedirect(request.getContextPath() + "/Activity");
                break;
        }
    }

    private void showCreateForm(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int disciplineId = Integer.parseInt(request.getParameter("disciplineId"));
        if (!isProfessor(request, disciplineId)) {
            FlashMessage.set(request, "danger", "Somente o professor pode criar atividades.");
            response.sendRedirect(request.getContextPath() + "/Disciplines?action=view&id=" + disciplineId + "&tab=atividades");
            return;
        }
        request.setAttribute("pageHeading", "Nova Atividade");
        request.setAttribute("contentPage", "/WEB-INF/templates/activity/form.jsp");
        request.setAttribute("pageJs", "/assets/js/activity.js");
    }

    private void showActivityDetails(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int activityId = Integer.parseInt(request.getParameter("id"));
        Activity activity = dao.getById(activityId);
        User user = getLoggedUser(request);
        User participant = activity != null && user != null ? resolveParticipant(activity.getIdDiscipline(), user) : null;

        if (activity == null || participant == null) {
            FlashMessage.set(request, "danger", "Você não tem acesso a essa atividade.");
            response.sendRedirect(request.getContextPath() + "/Disciplines");
            return;
        }

        List<Question> questions = questionDAO.listByActivity(activityId);
        boolean hasSubmission = false;
        List<ActivitySubmission> submissions = null;

        if (participant.canSubmitActivity() && participant instanceof Student) {
            hasSubmission = submissionDAO.hasSubmission(activityId, ((Student) participant).getStudentId());
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
        
        request.setAttribute("pageTitle", "Gestão Acadêmica");
        request.setAttribute("pageCss", "/assets/css/disciplines.css");
        request.getRequestDispatcher("/WEB-INF/templates/layout/base.jsp").forward(request, response);
    }

    private void showActivityEditForm(HttpServletRequest request, HttpServletResponse response) {
        int activityId = Integer.parseInt(request.getParameter("id"));
        Activity activity = dao.getById(activityId);

        if (activity != null && !isProfessor(request, activity.getIdDiscipline())) {
            FlashMessage.set(request, "danger", "Somente o professor pode editar atividades.");
            activity = null;
        }

        request.setAttribute("activity", activity);
        request.setAttribute("pageHeading", "Editar Atividade");
        request.setAttribute("pageJs", "/assets/js/activity.js");
        request.setAttribute("contentPage", "/WEB-INF/templates/activity/edit.jsp");
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
        saveQuestionsFromRequest(request, activityId);

        FlashMessage.set(request, "success", "Atividade atualizada com sucesso! As submissões anteriores foram resetadas.");
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

    private Activity buildActivityFromRequest(HttpServletRequest request, int activityId, int disciplineId) {
        String title = request.getParameter("title");
        LocalDate submitDate = LocalDate.parse(request.getParameter("submitDate"));
        String deliveryDateValue = request.getParameter("deliveryDate");
        LocalDate deliveryDate = deliveryDateValue == null || deliveryDateValue.isBlank() ? null : LocalDate.parse(deliveryDateValue);
        float weight = parseFloat(request.getParameter("weight"));

        return new Activity(title, submitDate, deliveryDate, weight, activityId, disciplineId);
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

        if (questionTexts == null) return;

        for (int i = 0; i < questionTexts.length; i++) {
            String questionText = questionTexts[i];
            if (questionText == null || questionText.isBlank()) continue;

            float questionWeight = questionWeights != null && i < questionWeights.length ? parseFloat(questionWeights[i]) : 0;
            String questionType = valueAt(questionTypes, i);
            Question question;

            if ("FECHADA".equals(questionType)) {
                question = new ClosedQuestion(0, questionWeight, questionText, activityId,
                        normalizeOptionLetter(valueAt(correctOptions, i)),
                        valueAt(optionsA, i), valueAt(optionsB, i), valueAt(optionsC, i), valueAt(optionsD, i));
            } else {
                question = new OpenQuestion(0, questionWeight, questionText, activityId, valueAt(expectedAnswers, i));
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
        return (value == null || value.isBlank()) ? 0 : Float.parseFloat(value);
    }

    private boolean isProfessor(HttpServletRequest request, int disciplineId) {
        User user = getLoggedUser(request);
        User participant = user != null ? resolveParticipant(disciplineId, user) : null;
        return participant != null && participant.canManageDiscipline();
    }

    private User resolveParticipant(int disciplineId, User user) {
        String role = disciplineDAO.getUserRole(disciplineId, user.getId());
        if ("PROFESSOR".equals(role)) return teacherDAO.getTeacherByUserId(user.getId());
        if ("ALUNO".equals(role)) return studentDAO.getStudentByUserId(user.getId());
        return null;
    }

    private User getLoggedUser(HttpServletRequest request) {
        return (User) request.getSession().getAttribute("usuarioLogado");
    }
}