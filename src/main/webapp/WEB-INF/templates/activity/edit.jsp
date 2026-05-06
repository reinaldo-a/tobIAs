<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.tobias.model.Activity" %>

<%
    Activity activity = (Activity) request.getAttribute("activity");
%>

<div class="custom-container activity-page">
    <% if (activity != null) { %>
        <div class="activity-header">
            <div>
                <h2 class="activity-title">Editar Atividade</h2>
                <p class="activity-subtitle">Atualize titulo, peso e datas dessa atividade.</p>
            </div>
            <a href="${pageContext.request.contextPath}/Activity?action=view&id=<%= activity.getId() %>" class="btn btn-action btn-action-back">
                <svg class="ui-icon" viewBox="0 0 24 24" aria-hidden="true" focusable="false">
                    <path d="M10.8 5.4 4.2 12l6.6 6.6 1.4-1.4L8 13h12v-2H8l4.2-4.2-1.4-1.4Z"/>
                </svg>
                Voltar
            </a>
        </div>

        <div class="activity-panel">
            <form action="${pageContext.request.contextPath}/Activity" method="post">
                <input type="hidden" name="action" value="update">
                <input type="hidden" name="activityId" value="<%= activity.getId() %>">
                <input type="hidden" name="disciplineId" value="<%= activity.getIdDiscipline() %>">

                <div class="activity-panel-header">
                    <h3 class="activity-panel-title">Informações da atividade</h3>
                </div>

                <div class="row activity-form-grid">
                    <div class="col-md-8">
                        <label class="form-label">Titulo</label>
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

                <div class="activity-footer-actions">
                    <button type="submit" class="btn btn-action btn-action-save">
                        <svg class="ui-icon" viewBox="0 0 24 24" aria-hidden="true" focusable="false">
                            <path d="m9.2 16.6-4.1-4.1 1.4-1.4 2.7 2.7 8.3-8.3 1.4 1.4-9.7 9.7Z"/>
                        </svg>
                        Salvar Alterações
                    </button>
                </div>
            </form>
        </div>
    <% } else { %>
        <div class="empty-state">
            Atividade não encontrada.
        </div>
    <% } %>
</div>
