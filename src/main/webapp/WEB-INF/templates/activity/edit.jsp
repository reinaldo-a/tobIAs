<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.tobias.model.Activity" %>

<%
    Activity activity = (Activity) request.getAttribute("activity");
%>

<div class="custom-container">
    <% if (activity != null) { %>
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2 class="h4 mb-0">Editar Atividade</h2>
            <a href="${pageContext.request.contextPath}/Activity?action=view&id=<%= activity.getId() %>" class="btn btn-outline-secondary">Voltar</a>
        </div>

        <div class="card border-0 shadow-sm p-4">
            <form action="${pageContext.request.contextPath}/Activity" method="post">
                <input type="hidden" name="action" value="update">
                <input type="hidden" name="activityId" value="<%= activity.getId() %>">
                <input type="hidden" name="disciplineId" value="<%= activity.getIdDiscipline() %>">

                <div class="row g-3">
                    <div class="col-md-8">
                        <label class="form-label">Qual é a atividade?</label>
                        <input type="text" class="form-control" name="title" value="<%= activity.getTitle() %>" required>
                    </div>
                    <div class="col-md-4">
                        <label class="form-label">Peso da atividade</label>
                        <input type="number" class="form-control" name="weight" min="0" step="0.1" value="<%= activity.getPeso() %>">
                    </div>
                    <div class="col-md-4">
                        <label class="form-label">Data de atribuição</label>
                        <input type="date" class="form-control" name="submitDate" value="<%= activity.getSubmitDate() %>" required>
                    </div>
                    <div class="col-md-4">
                        <label class="form-label">Data de entrega</label>
                        <input type="date" class="form-control" name="deliveryDate" value="<%= activity.getDeliveryDate() != null ? activity.getDeliveryDate() : "" %>">
                    </div>
                </div>

                <button type="submit" class="btn btn-primary mt-4">Salvar Alterações</button>
            </form>
        </div>
    <% } else { %>
        <div class="card border-0 shadow-sm p-4">
            <p class="text-muted text-center mb-0">Atividade não encontrada.</p>
        </div>
    <% } %>
</div>
