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
import com.tobias.dao.QuestionDAO;
import com.tobias.model.Activity;
import com.tobias.model.Question;

@WebServlet({
    "/Activity"
})
public class ActivityController extends HttpServlet {

    private ActivityDAO dao = new ActivityDAO();
    private QuestionDAO questionDAO = new QuestionDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        FlashMessage.get(request);

        switch (action == null ? "" : action) {
            case "new":
                request.setAttribute("pageHeading", "Nova Atividade");
                request.setAttribute("contentPage", "/WEB-INF/templates/activity/form.jsp");
                request.setAttribute("pageJs", "/assets/js/activity.js");
                break;
            case "view":
                showActivityDetails(request);
                break;
            case "edit":
                showActivityEditForm(request);
                break;
            case "edit-question":
                showQuestionEditForm(request);
                break;
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
            default:
                response.sendRedirect(request.getContextPath() + "/Activity");
                break;
        }
    }

    private void showActivityDetails(HttpServletRequest request) {
        int activityId = Integer.parseInt(request.getParameter("id"));
        Activity activity = dao.getById(activityId);
        List<Question> questions = questionDAO.listByActivity(activityId);

        request.setAttribute("activity", activity);
        request.setAttribute("questions", questions);
        request.setAttribute("pageHeading", "Detalhes da Atividade");
        request.setAttribute("contentPage", "/WEB-INF/templates/activity/details.jsp");
    }

    private void showActivityEditForm(HttpServletRequest request) {
        int activityId = Integer.parseInt(request.getParameter("id"));
        Activity activity = dao.getById(activityId);

        request.setAttribute("activity", activity);
        request.setAttribute("pageHeading", "Editar Atividade");
        request.setAttribute("contentPage", "/WEB-INF/templates/activity/edit.jsp");
    }

    private void showQuestionEditForm(HttpServletRequest request) {
        int questionId = Integer.parseInt(request.getParameter("id"));
        Question question = questionDAO.getById(questionId);

        request.setAttribute("question", question);
        request.setAttribute("pageHeading", "Editar Questão");
        request.setAttribute("contentPage", "/WEB-INF/templates/activity/question_edit.jsp");
    }

    private void createActivity(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int disciplineId = Integer.parseInt(request.getParameter("disciplineId"));
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
        Activity activity = buildActivityFromRequest(request, activityId, disciplineId);

        dao.updateActivity(activity);
        FlashMessage.set(request, "success", "Atividade atualizada com sucesso!");
        response.sendRedirect(request.getContextPath() + "/Activity?action=view&id=" + activityId);
    }

    private void deleteActivity(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int activityId = Integer.parseInt(request.getParameter("activityId"));
        int disciplineId = Integer.parseInt(request.getParameter("disciplineId"));

        dao.deleteActivity(activityId);
        FlashMessage.set(request, "success", "Atividade excluída com sucesso!");
        response.sendRedirect(request.getContextPath() + "/Disciplines?action=view&id=" + disciplineId + "&tab=atividades");
    }

    private void createQuestion(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int activityId = Integer.parseInt(request.getParameter("activityId"));
        Question question = buildQuestionFromRequest(request, 0, activityId);

        questionDAO.createQuestion(question);
        FlashMessage.set(request, "success", "Questão criada com sucesso!");
        response.sendRedirect(request.getContextPath() + "/Activity?action=view&id=" + activityId);
    }

    private void updateQuestion(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int questionId = Integer.parseInt(request.getParameter("questionId"));
        int activityId = Integer.parseInt(request.getParameter("activityId"));
        Question question = buildQuestionFromRequest(request, questionId, activityId);

        questionDAO.updateQuestion(question);
        FlashMessage.set(request, "success", "Questão atualizada com sucesso!");
        response.sendRedirect(request.getContextPath() + "/Activity?action=view&id=" + activityId);
    }

    private void deleteQuestion(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int questionId = Integer.parseInt(request.getParameter("questionId"));
        int activityId = Integer.parseInt(request.getParameter("activityId"));

        questionDAO.deleteQuestion(questionId);
        FlashMessage.set(request, "success", "Questão excluída com sucesso!");
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

    private Question buildQuestionFromRequest(HttpServletRequest request, int questionId, int activityId) {
        String text = request.getParameter("questionText");
        float weight = parseFloat(request.getParameter("questionWeight"));

        return new Question(questionId, weight, text, activityId);
    }

    private void saveQuestionsFromRequest(HttpServletRequest request, int activityId) {
        String[] questionTexts = request.getParameterValues("questionText");
        String[] questionWeights = request.getParameterValues("questionWeight");

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

            Question question = new Question(0, questionWeight, questionText, activityId);
            questionDAO.createQuestion(question);
        }
    }

    private float parseFloat(String value) {
        if (value == null || value.isBlank()) {
            return 0;
        }

        return Float.parseFloat(value);
    }
}
