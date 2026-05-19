<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.tobias.model.Activity" %>
<%@ page import="com.tobias.model.Discipline" %>
<%@ page import="com.tobias.model.Material" %>
<%@ page import="com.tobias.model.User" %>
<%@ page import="com.tobias.dao.ActivityDAO" %>

<%
    boolean showActivitiesTab = "atividades".equals(request.getParameter("tab"));
    Discipline discipline = (Discipline) request.getAttribute("discipline");
    User participant = (User) request.getAttribute("participant");
    List<User> students = (List<User>) request.getAttribute("students");
    List<Material> materials = (List<Material>) request.getAttribute("materials");
    boolean isProfessor = participant != null && participant.canManageDiscipline();
    boolean isStudent = participant != null && participant.canSubmitActivity();
%>

<div class="custom-container mt-3">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <div>
            <h2 class="h3 mb-0 text-primary"><%= discipline != null ? discipline.getName() : "Disciplina" %></h2>
            <p class="text-muted mb-0">
                Código: <%= discipline != null ? discipline.getCode() : "" %>
                <% if (discipline != null && discipline.getProfessorName() != null) { %>
                    · Professor: <%= discipline.getProfessorName() %>
                <% } %>
                <% if (isProfessor) { %>
                    · Visão do professor
                <% } else if (isStudent) { %>
                    · Visão do aluno
                <% } %>
            </p>
        </div>
        <a href="${pageContext.request.contextPath}/Disciplines" class="btn btn-action btn-action-back">
            <svg class="ui-icon" viewBox="0 0 24 24" aria-hidden="true" focusable="false">
                <path d="M10.8 5.4 4.2 12l6.6 6.6 1.4-1.4L8 13h12v-2H8l4.2-4.2-1.4-1.4Z"/>
            </svg>
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
                    <% if (isProfessor) { %>
                        <a href="${pageContext.request.contextPath}/Material?action=new&disciplineId=<%= discipline != null ? discipline.getId() : 0 %>" class="btn btn-sm btnadd text-white">+ Novo Material</a>
                    <% } %>
                </div>
                <hr>
                <% if (materials != null && !materials.isEmpty()) { %>
                    <div class="list-group list-group-flush">
                        <% for (Material material : materials) { %>
                            <div class="list-group-item px-0">
                                <div class="d-flex justify-content-between align-items-start gap-3">
                                    <div class="flex-grow-1">
                                        <h6 class="mb-1"><%= material.getTitle() != null ? material.getTitle() : "Material sem título" %></h6>
                                        <% if (material.getContent() != null && !material.getContent().isBlank()) { %>
                                            <p class="text-muted mb-2"><%= material.getContent() %></p>
                                        <% } %>
                                        <small class="text-muted">
                                            <% if (material.getUploadedAt() != null) { %>
                                                Publicado em <%= material.getUploadedAt().toLocalDate() %>
                                            <% } %>
                                            <% if (material.getOriginalFileName() != null && !material.getOriginalFileName().isBlank()) { %>
                                                · <%= material.getOriginalFileName() %>
                                            <% } %>
                                        </small>
                                    </div>
                                    <div class="activity-actions justify-content-end">
                                        <% if (material.hasFile()) { %>
                                            <a href="${pageContext.request.contextPath}/Material?action=download&id=<%= material.getId() %>" class="btn btn-sm btn-action material-action-icon btn-action-save" title="Baixar material" aria-label="Baixar material">
                                                <svg class="ui-icon" viewBox="0 0 24 24" aria-hidden="true" focusable="false">
                                                    <path d="M11 4h2v9.2l3.1-3.1 1.4 1.4L12 17l-5.5-5.5 1.4-1.4 3.1 3.1V4Zm-6 15h14v2H5v-2Z"/>
                                                </svg>
                                            </a>
                                        <% } %>
                                        <% if (isProfessor) { %>
                                            <a href="${pageContext.request.contextPath}/Material?action=edit&id=<%= material.getId() %>" class="btn btn-sm btn-action material-action-icon btn-action-edit" title="Editar material" aria-label="Editar material">
                                                <svg class="ui-icon" viewBox="0 0 24 24" aria-hidden="true" focusable="false">
                                                    <path d="M17.7 3.3a1 1 0 0 1 1.4 0l1.6 1.6a1 1 0 0 1 0 1.4L8.9 18.1 4 19.5l1.4-4.9L17.7 3.3Zm-10.5 12-.5 1.9 1.9-.5L16.6 8.7l-1.4-1.4-8 8ZM17.9 7.3 18.6 6 18 5.4l-1.3.7 1.2 1.2Z"/>
                                                </svg>
                                            </a>
                                            <form action="${pageContext.request.contextPath}/Material" method="post">
                                                <input type="hidden" name="action" value="delete">
                                                <input type="hidden" name="materialId" value="<%= material.getId() %>">
                                                <button type="submit" class="btn btn-sm btn-action material-action-icon btn-action-delete" title="Excluir material" aria-label="Excluir material">
                                                    <svg class="ui-icon" viewBox="0 0 24 24" aria-hidden="true" focusable="false">
                                                        <path d="M9 3h6l1 2h4v2H4V5h4l1-2Zm-2 6h10l-.7 12H7.7L7 9Zm2.1 2 .5 8h1.8l-.4-8H9.1Zm3.9 0v8h2v-8h-2Z"/>
                                                    </svg>
                                                </button>
                                            </form>
                                        <% } %>
                                    </div>
                                </div>
                            </div>
                        <% } %>
                    </div>
                <% } else { %>
                    <p class="text-muted text-center py-4">Nenhum material disponibilizado pelo professor ainda.</p>
                <% } %>
            </div>
        </div>

        <div class="tab-pane fade <%= showActivitiesTab ? "show active" : "" %>" id="atividades" role="tabpanel">
            <div class="card border-0 shadow-sm p-4">
                <div class="d-flex justify-content-between align-items-center mb-3">
                    <h5 class="mb-0">Atividades Avaliativas</h5>
                    <% if (isProfessor) { %>
                        <a href="${pageContext.request.contextPath}/Activity?action=new&disciplineId=${param.id}" class="btn btn-sm btnadd text-white">+ Nova Atividade</a>
                    <% } %>
                </div>
                <hr>
                <%
                    List<Activity> activities = (List<Activity>) request.getAttribute("activities");

                    if (activities == null && request.getParameter("id") != null) {
                        ActivityDAO activityDao = new ActivityDAO();
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
                        <span><%= discipline != null && discipline.getProfessorName() != null ? discipline.getProfessorName() : "Professor Responsável" %></span>
                    </li>
                </ul>

                <h5 class="text-primary mb-3">Colegas de Turma</h5>
                <% if (students != null && !students.isEmpty()) { %>
                    <ul class="list-group list-group-flush">
                        <% for (User student : students) { %>
                            <li class="list-group-item d-flex justify-content-between align-items-center gap-3">
                                <div class="d-flex align-items-center gap-3">
                                    <div class="bg-secondary text-white rounded-circle d-flex justify-content-center align-items-center" style="width: 40px; height: 40px;">
                                        <strong><%= student.getName() != null && !student.getName().isBlank() ? student.getName().substring(0, 1).toUpperCase() : "A" %></strong>
                                    </div>
                                    <div>
                                        <div><%= student.getName() %></div>
                                        <small class="text-muted"><%= student.getEmail() %></small>
                                    </div>
                                </div>
                                <span class="badge bg-secondary">Aluno</span>
                            </li>
                        <% } %>
                    </ul>
                <% } else { %>
                    <p class="text-muted px-3">Nenhum aluno entrou nessa disciplina ainda.</p>
                <% } %>
            </div>
        </div>

    </div>
</div>
