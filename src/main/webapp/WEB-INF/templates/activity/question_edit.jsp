<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.tobias.model.Question" %>

<%
    Question question = (Question) request.getAttribute("question");
%>

<div class="custom-container">
    <% if (question != null) { %>
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2 class="h4 mb-0">Editar Questão</h2>
            <a href="${pageContext.request.contextPath}/Activity?action=view&id=<%= question.getIdActivity() %>" class="btn btn-outline-secondary">Voltar</a>
        </div>

        <div class="card border-0 shadow-sm p-4">
            <form action="${pageContext.request.contextPath}/Activity" method="post">
                <input type="hidden" name="action" value="update-question">
                <input type="hidden" name="questionId" value="<%= question.getId() %>">
                <input type="hidden" name="activityId" value="<%= question.getIdActivity() %>">

                <div class="row g-3">
                    <div class="col-md-9">
                        <label class="form-label">Enunciado</label>
                        <textarea class="form-control" name="questionText" rows="4" required><%= question.getEnunciado() %></textarea>
                    </div>
                    <div class="col-md-3">
                        <label class="form-label">Peso</label>
                        <input type="number" class="form-control" name="questionWeight" min="0" step="0.1" value="<%= question.getPeso() %>">
                    </div>
                </div>

                <button type="submit" class="btn btn-primary mt-4">Salvar Questão</button>
            </form>
        </div>
    <% } else { %>
        <div class="card border-0 shadow-sm p-4">
            <p class="text-muted text-center mb-0">Questão não encontrada.</p>
        </div>
    <% } %>
</div>
