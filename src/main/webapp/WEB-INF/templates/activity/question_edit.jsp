<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.tobias.model.Question" %>

<%
    Question question = (Question) request.getAttribute("question");
%>

<div class="custom-container activity-page">
    <% if (question != null) { %>
        <div class="activity-header">
            <div>
                <h2 class="activity-title">Editar Questão</h2>
                <p class="activity-subtitle">Ajuste o enunciado e o peso da questão.</p>
            </div>
            <a href="${pageContext.request.contextPath}/Activity?action=view&id=<%= question.getIdActivity() %>" class="btn btn-action btn-action-back">
                <svg class="ui-icon" viewBox="0 0 24 24" aria-hidden="true" focusable="false">
                    <path d="M10.8 5.4 4.2 12l6.6 6.6 1.4-1.4L8 13h12v-2H8l4.2-4.2-1.4-1.4Z"/>
                </svg>
                Voltar
            </a>
        </div>

        <div class="activity-panel">
            <form action="${pageContext.request.contextPath}/Activity" method="post">
                <input type="hidden" name="action" value="update-question">
                <input type="hidden" name="questionId" value="<%= question.getId() %>">
                <input type="hidden" name="activityId" value="<%= question.getIdActivity() %>">

                <div class="activity-panel-header">
                    <h3 class="activity-panel-title">Conteúdo da questão</h3>
                </div>

                <div class="row activity-form-grid">
                    <div class="col-md-9">
                        <label class="form-label">Enunciado</label>
                        <textarea class="form-control" name="questionText" rows="4" required><%= question.getEnunciado() %></textarea>
                    </div>
                    <div class="col-md-3">
                        <label class="form-label">Peso</label>
                        <input type="number" class="form-control" name="questionWeight" min="0" step="0.1" value="<%= question.getPeso() %>">
                    </div>
                </div>

                <div class="activity-footer-actions">
                    <button type="submit" class="btn btn-action btn-action-save">
                        <svg class="ui-icon" viewBox="0 0 24 24" aria-hidden="true" focusable="false">
                            <path d="m9.2 16.6-4.1-4.1 1.4-1.4 2.7 2.7 8.3-8.3 1.4 1.4-9.7 9.7Z"/>
                        </svg>
                        Salvar Questão
                    </button>
                </div>
            </form>
        </div>
    <% } else { %>
        <div class="empty-state">
            Questão não encontrada.
        </div>
    <% } %>
</div>
