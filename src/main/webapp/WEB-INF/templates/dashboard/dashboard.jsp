<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.tobias.model.Discipline" %>
<%@ page import="com.tobias.model.User" %>

<%
  User loggedUser = (User) session.getAttribute("usuarioLogado");
  List<Discipline> disciplines = (List<Discipline>) request.getAttribute("dashboardDisciplines");
  int disciplineCount = request.getAttribute("dashboardDisciplineCount") != null
      ? (Integer) request.getAttribute("dashboardDisciplineCount")
      : 0;
  long teacherDisciplineCount = request.getAttribute("dashboardTeacherDisciplineCount") != null
      ? (Long) request.getAttribute("dashboardTeacherDisciplineCount")
      : 0;
  long studentDisciplineCount = request.getAttribute("dashboardStudentDisciplineCount") != null
      ? (Long) request.getAttribute("dashboardStudentDisciplineCount")
      : 0;
  String firstName = loggedUser != null && loggedUser.getName() != null && !loggedUser.getName().isBlank()
      ? loggedUser.getName().split(" ")[0]
      : "usuário";
%>

<div class="custom-container">
  <div class="dashboard-shell">
    <section class="dashboard-welcome">
      <div>
        <span class="dashboard-kicker">TobIAs</span>
        <h1>Olá, <%= firstName %>.</h1>
        <p>Continue suas disciplinas, publique materiais, acompanhe atividades e gere relatórios em um só lugar.</p>
      </div>
      <div class="dashboard-actions">
        <a href="${pageContext.request.contextPath}/Disciplines" class="btn btn-action btn-action-save">
          <i class="ti ti-book"></i>
          Ver disciplinas
        </a>
        <a href="${pageContext.request.contextPath}/user/update-form" class="btn btn-action btn-action-back">
          <i class="ti ti-user-edit"></i>
          Meu perfil
        </a>
      </div>
    </section>

    <section class="dashboard-stats">
      <article class="dashboard-stat-card">
        <span class="dashboard-stat-icon"><i class="ti ti-books"></i></span>
        <strong><%= disciplineCount %></strong>
        <span>Disciplinas vinculadas</span>
      </article>
      <article class="dashboard-stat-card">
        <span class="dashboard-stat-icon"><i class="ti ti-chalkboard"></i></span>
        <strong><%= teacherDisciplineCount %></strong>
        <span>Como professor</span>
      </article>
      <article class="dashboard-stat-card">
        <span class="dashboard-stat-icon"><i class="ti ti-school"></i></span>
        <strong><%= studentDisciplineCount %></strong>
        <span>Como aluno</span>
      </article>
    </section>

    <section class="dashboard-grid">
      <article class="dashboard-panel">
        <div class="dashboard-panel-header">
          <div>
            <span class="dashboard-kicker">Acesso rápido</span>
            <h2>Atalhos do sistema</h2>
          </div>
        </div>

        <div class="dashboard-shortcuts">
          <a href="${pageContext.request.contextPath}/Disciplines?action=new" class="dashboard-shortcut">
            <i class="ti ti-plus"></i>
            <span>Nova disciplina</span>
          </a>
          <a href="${pageContext.request.contextPath}/Disciplines?action=enter" class="dashboard-shortcut">
            <i class="ti ti-login-2"></i>
            <span>Entrar em disciplina</span>
          </a>
          <a href="${pageContext.request.contextPath}/Disciplines" class="dashboard-shortcut">
            <i class="ti ti-list-details"></i>
            <span>Minhas disciplinas</span>
          </a>
          <a href="${pageContext.request.contextPath}/user/update-form" class="dashboard-shortcut">
            <i class="ti ti-user-cog"></i>
            <span>Atualizar perfil</span>
          </a>
        </div>
      </article>

      <article class="dashboard-panel">
        <div class="dashboard-panel-header">
          <div>
            <span class="dashboard-kicker">Continuidade</span>
            <h2>Disciplinas recentes</h2>
          </div>
          <a href="${pageContext.request.contextPath}/Disciplines" class="dashboard-link">Ver todas</a>
        </div>

        <% if (disciplines != null && !disciplines.isEmpty()) { %>
          <div class="dashboard-discipline-list">
            <%
              int limit = Math.min(4, disciplines.size());
              for (int i = 0; i < limit; i++) {
                Discipline discipline = disciplines.get(i);
            %>
              <a href="${pageContext.request.contextPath}/Disciplines?action=view&id=<%= discipline.getId() %>" class="dashboard-discipline-item">
                <div>
                  <strong><%= discipline.getName() %></strong>
                  <span>
                    Código <%= discipline.getCode() %>
                    <% if (discipline.getProfessorName() != null) { %>
                      · Prof. <%= discipline.getProfessorName() %>
                    <% } %>
                  </span>
                </div>
                <small><%= discipline.isProfessor() ? "Professor" : "Aluno" %></small>
              </a>
            <% } %>
          </div>
        <% } else { %>
          <div class="dashboard-empty">
            Você ainda não participa de nenhuma disciplina.
          </div>
        <% } %>
      </article>
    </section>
  </div>
</div>
