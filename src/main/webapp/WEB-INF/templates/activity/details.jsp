<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.tobias.model.Activity" %>
<%@ page import="com.tobias.model.Question" %>

<%
    Activity activity = (Activity) request.getAttribute("activity");
    List<Question> questions = (List<Question>) request.getAttribute("questions");
%>

<div class="custom-container activity-page">
    <% if (activity != null) { %>
        <div class="activity-header">
            <div>
                <h2 class="activity-title"><%= activity.getTitle() %></h2>
                <p class="activity-subtitle">
                    Atribuida em <%= activity.getSubmitDate() %>
                    <% if (activity.getDeliveryDate() != null) { %>
                        · Entrega em <%= activity.getDeliveryDate() %>
                    <% } %>
                    · Peso <%= activity.getPeso() %>
                </p>
            </div>
            <a href="${pageContext.request.contextPath}/Disciplines?action=view&id=<%= activity.getIdDiscipline() %>&tab=atividades" class="btn btn-action btn-action-back">
                <svg class="ui-icon" viewBox="0 0 24 24" aria-hidden="true" focusable="false">
                    <path d="M10.8 5.4 4.2 12l6.6 6.6 1.4-1.4L8 13h12v-2H8l4.2-4.2-1.4-1.4Z"/>
                </svg>
                Voltar
            </a>
        </div>

        <div class="activity-panel">
            <div class="activity-panel-header">
                <h3 class="activity-panel-title">Ações da atividade</h3>
            </div>
            <div class="activity-actions">
                <a href="${pageContext.request.contextPath}/Activity?action=edit&id=<%= activity.getId() %>" class="btn btn-action btn-action-edit">
                    <svg class="ui-icon" viewBox="0 0 24 24" aria-hidden="true" focusable="false">
                        <path d="M17.7 3.3a1 1 0 0 1 1.4 0l1.6 1.6a1 1 0 0 1 0 1.4L8.9 18.1 4 19.5l1.4-4.9L17.7 3.3Zm-10.5 12-.5 1.9 1.9-.5L16.6 8.7l-1.4-1.4-8 8ZM17.9 7.3 18.6 6 18 5.4l-1.3.7 1.2 1.2Z"/>
                    </svg>
                    Editar Atividade
                </a>
                <form action="${pageContext.request.contextPath}/Activity" method="post">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="activityId" value="<%= activity.getId() %>">
                    <input type="hidden" name="disciplineId" value="<%= activity.getIdDiscipline() %>">
                    <button type="submit" class="btn btn-action btn-action-delete">
                        <svg class="ui-icon" viewBox="0 0 24 24" aria-hidden="true" focusable="false">
                            <path d="M9 3h6l1 2h4v2H4V5h4l1-2Zm-2 6h10l-.7 12H7.7L7 9Zm2.1 2 .5 8h1.8l-.4-8H9.1Zm3.9 0v8h2v-8h-2Z"/>
                        </svg>
                        Excluir Atividade
                    </button>
                </form>
            </div>
        </div>

        <div class="activity-panel">
            <div class="activity-panel-header">
                <h3 class="activity-panel-title">Nova Questão</h3>
            </div>
            <form action="${pageContext.request.contextPath}/Activity" method="post">
                <input type="hidden" name="action" value="new-question">
                <input type="hidden" name="activityId" value="<%= activity.getId() %>">

                <div class="row activity-form-grid">
                    <div class="col-md-9">
                        <label class="form-label">Enunciado</label>
                        <textarea class="form-control" name="questionText" rows="3" required></textarea>
                    </div>
                    <div class="col-md-3">
                        <label class="form-label">Peso</label>
                        <input type="number" class="form-control" name="questionWeight" min="0" step="0.1">
                    </div>
                </div>

                <button type="submit" class="btn btn-action btn-action-add mt-3">
                    <svg class="ui-icon" viewBox="0 0 24 24" aria-hidden="true" focusable="false">
                        <path d="M11 5h2v6h6v2h-6v6h-2v-6H5v-2h6V5Z"/>
                    </svg>
                    Adicionar Questão
                </button>
            </form>
        </div>

        <div class="activity-panel">
            <div class="activity-panel-header">
                <h3 class="activity-panel-title">Questões cadastradas</h3>
            </div>

            <% if (questions != null && !questions.isEmpty()) { %>
                <div class="question-list">
                    <% for (Question question : questions) { %>
                        <div class="question-list-item">
                            <div>
                                <p class="question-text"><%= question.getEnunciado() %></p>
                                <span class="activity-meta-item">Peso <strong><%= question.getPeso() %></strong></span>
                            </div>
                            <div class="activity-actions">
                                <a href="${pageContext.request.contextPath}/Activity?action=edit-question&id=<%= question.getId() %>" class="btn btn-sm btn-action btn-action-edit">
                                    <svg class="ui-icon" viewBox="0 0 24 24" aria-hidden="true" focusable="false">
                                        <path d="M17.7 3.3a1 1 0 0 1 1.4 0l1.6 1.6a1 1 0 0 1 0 1.4L8.9 18.1 4 19.5l1.4-4.9L17.7 3.3Zm-10.5 12-.5 1.9 1.9-.5L16.6 8.7l-1.4-1.4-8 8ZM17.9 7.3 18.6 6 18 5.4l-1.3.7 1.2 1.2Z"/>
                                    </svg>
                                    Editar
                                </a>
                                <form action="${pageContext.request.contextPath}/Activity" method="post">
                                    <input type="hidden" name="action" value="delete-question">
                                    <input type="hidden" name="questionId" value="<%= question.getId() %>">
                                    <input type="hidden" name="activityId" value="<%= activity.getId() %>">
                                    <button type="submit" class="btn btn-sm btn-action btn-action-delete">
                                        <svg class="ui-icon" viewBox="0 0 24 24" aria-hidden="true" focusable="false">
                                            <path d="M9 3h6l1 2h4v2H4V5h4l1-2Zm-2 6h10l-.7 12H7.7L7 9Zm2.1 2 .5 8h1.8l-.4-8H9.1Zm3.9 0v8h2v-8h-2Z"/>
                                        </svg>
                                        Excluir
                                    </button>
                                </form>
                            </div>
                        </div>
                    <% } %>
                </div>
            <% } else { %>
                <div class="empty-state">Nenhuma questão cadastrada ainda.</div>
            <% } %>
        </div>
    <% } else { %>
        <div class="empty-state">
            Atividade não encontrada.
        </div>
    <% } %>
</div>
