<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.tobias.model.Activity" %>
<%@ page import="com.tobias.dao.activity" %>

<%
    boolean showActivitiesTab = "atividades".equals(request.getParameter("tab"));
%>

<div class="custom-container mt-3">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <div>
            <h2 class="h3 mb-0 text-primary">Nome da Disciplina (Ex: POO)</h2>
            <p class="text-muted mb-0">Código: 121232</p>
        </div>
        <a href="${pageContext.request.contextPath}/Disciplines" class="btn btn-outline-secondary">
            Voltar para Lista
        </a>
    </div>

    <ul class="nav nav-tabs mb-4" id="disciplineTabs" role="tablist">
        <li class="nav-item" role="presentation">
            <button class="nav-link <%= showActivitiesTab ? "" : "active" %>" id="materiais-tab" data-bs-toggle="tab" data-bs-target="#materiais" type="button" role="tab">
                📚 Materiais
            </button>
        </li>
        <li class="nav-item" role="presentation">
            <button class="nav-link <%= showActivitiesTab ? "active" : "" %>" id="atividades-tab" data-bs-toggle="tab" data-bs-target="#atividades" type="button" role="tab">
                📝 Atividades
            </button>
        </li>
        <li class="nav-item" role="presentation">
            <button class="nav-link" id="participantes-tab" data-bs-toggle="tab" data-bs-target="#participantes" type="button" role="tab">
                👥 Participantes
            </button>
        </li>
    </ul>


    <div class="tab-content" id="disciplineTabsContent">
        

        <div class="tab-pane fade <%= showActivitiesTab ? "" : "show active" %>" id="materiais" role="tabpanel">
            <div class="card border-0 shadow-sm p-4">
                <div class="d-flex justify-content-between align-items-center mb-3">
                    <h5 class="mb-0">Materiais de Apoio</h5>
                    <button class="btn btn-sm botnadd text-white">+ Novo Material</button>
                </div>
                <hr>
                <p class="text-muted text-center py-4">Nenhum material disponibilizado pelo professor ainda.</p>
            </div>
        </div>

        <div class="tab-pane fade <%= showActivitiesTab ? "show active" : "" %>" id="atividades" role="tabpanel">
            <div class="card border-0 shadow-sm p-4">
                <div class="d-flex justify-content-between align-items-center mb-3">
                    <h5 class="mb-0">Atividades Avaliativas</h5>
                    <a href="${pageContext.request.contextPath}/Activity?action=new&disciplineId=${param.id}" class="btn btn-sm btnadd text-white">+ Nova Atividade</a>
                </div>
                <hr>
                <%
                    List<Activity> activities = (List<Activity>) request.getAttribute("activities");

                    if (activities == null && request.getParameter("id") != null) {
                        activity activityDao = new activity();
                        activities = activityDao.listActivitiesByDiscipline(Integer.parseInt(request.getParameter("id")));
                    }

                    if (activities != null && !activities.isEmpty()) {
                %>
                    <div class="list-group list-group-flush">
                        <% for (Activity activity : activities) { %>
                            <div class="list-group-item px-0">
                                <div class="d-flex justify-content-between align-items-start gap-3">
                                    <div>
                                        <a href="${pageContext.request.contextPath}/Activity?action=view&id=<%= activity.getId() %>" class="text-decoration-none">
                                            <h6 class="mb-1"><%= activity.getTitle() %></h6>
                                        </a>
                                        <small class="text-muted">
                                            Atribuída em <%= activity.getSubmitDate() %>
                                            <% if (activity.getDeliveryDate() != null) { %>
                                                · Entrega em <%= activity.getDeliveryDate() %>
                                            <% } %>
                                        </small>
                                    </div>
                                    <span class="badge bg-primary rounded-pill">Peso <%= activity.getPeso() %></span>
                                </div>
                            </div>
                        <% } %>
                    </div>
                <%
                    } else {
                %>
                    <p class="text-muted text-center py-4">Nenhuma atividade no momento. Pode descansar!</p>
                <%
                    }
                %>
            </div>
        </div>

        <div class="tab-pane fade" id="participantes" role="tabpanel">
            <div class="card border-0 shadow-sm p-4">
                <h5 class="text-primary mb-3">Professores</h5>
                <ul class="list-group list-group-flush mb-4">
                    <li class="list-group-item d-flex align-items-center gap-3">
                        <div class="bg-primary text-white rounded-circle d-flex justify-content-center align-items-center" style="width: 40px; height: 40px;">
                            <strong>P</strong>
                        </div>
                        <span>Professor Responsável</span>
                    </li>
                </ul>

                <h5 class="text-primary mb-3">Colegas de Turma</h5>
                <p class="text-muted px-3">A lista de alunos aparecerá aqui.</p>
            </div>
        </div>

    </div>
</div>
