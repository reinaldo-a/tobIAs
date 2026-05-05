<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.tobias.model.Activity" %>
<%@ page import="com.tobias.model.Question" %>

<%
    Activity activity = (Activity) request.getAttribute("activity");
    List<Question> questions = (List<Question>) request.getAttribute("questions");
%>

<div class="custom-container">
    <% if (activity != null) { %>
        <div class="d-flex justify-content-between align-items-center mb-4">
            <div>
                <h2 class="h4 mb-0"><%= activity.getTitle() %></h2>
                <p class="text-muted mb-0">
                    Atribuída em <%= activity.getSubmitDate() %>
                    <% if (activity.getDeliveryDate() != null) { %>
                        · Entrega em <%= activity.getDeliveryDate() %>
                    <% } %>
                    · Peso <%= activity.getPeso() %>
                </p>
            </div>
            <a href="${pageContext.request.contextPath}/Disciplines?action=view&id=<%= activity.getIdDiscipline() %>&tab=atividades" class="btn btn-outline-secondary">
                Voltar
            </a>
        </div>

        <div class="card border-0 shadow-sm p-4 mb-4">
            <div class="d-flex gap-2">
                <a href="${pageContext.request.contextPath}/Activity?action=edit&id=<%= activity.getId() %>" class="btn btn-primary">
                    Editar Atividade
                </a>
                <form action="${pageContext.request.contextPath}/Activity" method="post">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="activityId" value="<%= activity.getId() %>">
                    <input type="hidden" name="disciplineId" value="<%= activity.getIdDiscipline() %>">
                    <button type="submit" class="btn btn-outline-danger">Excluir Atividade</button>
                </form>
            </div>
        </div>

        <div class="card border-0 shadow-sm p-4 mb-4">
            <h5 class="mb-3">Nova Questão</h5>
            <form action="${pageContext.request.contextPath}/Activity" method="post">
                <input type="hidden" name="action" value="new-question">
                <input type="hidden" name="activityId" value="<%= activity.getId() %>">

                <div class="row g-3">
                    <div class="col-md-9">
                        <label class="form-label">Enunciado</label>
                        <textarea class="form-control" name="questionText" rows="3" required></textarea>
                    </div>
                    <div class="col-md-3">
                        <label class="form-label">Peso</label>
                        <input type="number" class="form-control" name="questionWeight" min="0" step="0.1">
                    </div>
                </div>

                <button type="submit" class="btn btnadd text-white mt-3">Adicionar Questão</button>
            </form>
        </div>

        <div class="card border-0 shadow-sm p-4">
            <h5 class="mb-3">Questões</h5>

            <% if (questions != null && !questions.isEmpty()) { %>
                <div class="list-group list-group-flush">
                    <% for (Question question : questions) { %>
                        <div class="list-group-item px-0">
                            <div class="d-flex justify-content-between align-items-start gap-3">
                                <div>
                                    <p class="mb-1"><%= question.getEnunciado() %></p>
                                    <small class="text-muted">Peso <%= question.getPeso() %></small>
                                </div>
                                <div class="d-flex gap-2">
                                    <a href="${pageContext.request.contextPath}/Activity?action=edit-question&id=<%= question.getId() %>" class="btn btn-sm btn-outline-primary">
                                        Editar
                                    </a>
                                    <form action="${pageContext.request.contextPath}/Activity" method="post">
                                        <input type="hidden" name="action" value="delete-question">
                                        <input type="hidden" name="questionId" value="<%= question.getId() %>">
                                        <input type="hidden" name="activityId" value="<%= activity.getId() %>">
                                        <button type="submit" class="btn btn-sm btn-outline-danger">Excluir</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    <% } %>
                </div>
            <% } else { %>
                <p class="text-muted text-center py-4 mb-0">Nenhuma questão cadastrada ainda.</p>
            <% } %>
        </div>
    <% } else { %>
        <div class="card border-0 shadow-sm p-4">
            <p class="text-muted text-center mb-0">Atividade não encontrada.</p>
        </div>
    <% } %>
</div>
