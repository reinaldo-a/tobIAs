<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.tobias.model.Questoes" %>
<%@ page import="com.tobias.model.QuestoesAbertas" %>
<%@ page import="com.tobias.model.QuestoesFechadas" %>

<%
    Questoes question = (Questoes) request.getAttribute("question");
    boolean isClosedQuestion = question instanceof QuestoesFechadas;
    QuestoesAbertas openQuestion = question instanceof QuestoesAbertas ? (QuestoesAbertas) question : null;
    QuestoesFechadas closedQuestion = isClosedQuestion ? (QuestoesFechadas) question : null;
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
                    <div class="col-md-3">
                        <label class="form-label">Tipo</label>
                        <select class="form-control question-type" name="questionType">
                            <option value="ABERTA" <%= !isClosedQuestion ? "selected" : "" %>>Aberta</option>
                            <option value="FECHADA" <%= isClosedQuestion ? "selected" : "" %>>Fechada</option>
                        </select>
                    </div>
                    <div class="col-md-9">
                        <label class="form-label">Enunciado</label>
                        <textarea class="form-control" name="questionText" rows="4" required><%= question.getEnunciado() %></textarea>
                    </div>
                    <div class="col-md-3">
                        <label class="form-label">Peso</label>
                        <input type="number" class="form-control" name="questionWeight" min="0" step="0.1" value="<%= question.getPeso() %>">
                    </div>
                    <div class="col-md-9 open-question-fields <%= isClosedQuestion ? "d-none" : "" %>">
                        <label class="form-label">Resposta esperada</label>
                        <textarea class="form-control" name="expectedAnswer" rows="2"><%= openQuestion == null || openQuestion.getRespostaEsperada() == null ? "" : openQuestion.getRespostaEsperada() %></textarea>
                    </div>
                    <div class="col-12 closed-question-fields <%= isClosedQuestion ? "" : "d-none" %>">
                        <div class="row g-3">
                            <div class="col-md-3">
                                <label class="form-label">Alternativa A</label>
                                <input type="text" class="form-control" name="optionA" value="<%= closedQuestion == null || closedQuestion.getOpcaoA() == null ? "" : closedQuestion.getOpcaoA() %>">
                            </div>
                            <div class="col-md-3">
                                <label class="form-label">Alternativa B</label>
                                <input type="text" class="form-control" name="optionB" value="<%= closedQuestion == null || closedQuestion.getOpcaoB() == null ? "" : closedQuestion.getOpcaoB() %>">
                            </div>
                            <div class="col-md-3">
                                <label class="form-label">Alternativa C</label>
                                <input type="text" class="form-control" name="optionC" value="<%= closedQuestion == null || closedQuestion.getOpcaoC() == null ? "" : closedQuestion.getOpcaoC() %>">
                            </div>
                            <div class="col-md-3">
                                <label class="form-label">Alternativa D</label>
                                <input type="text" class="form-control" name="optionD" value="<%= closedQuestion == null || closedQuestion.getOpcaoD() == null ? "" : closedQuestion.getOpcaoD() %>">
                            </div>
                            <div class="col-md-3">
                                <label class="form-label">Letra correta</label>
                                <select class="form-control" name="correctOption">
                                    <option value="A" <%= closedQuestion != null && "A".equals(closedQuestion.getLetraCorreta()) ? "selected" : "" %>>A</option>
                                    <option value="B" <%= closedQuestion != null && "B".equals(closedQuestion.getLetraCorreta()) ? "selected" : "" %>>B</option>
                                    <option value="C" <%= closedQuestion != null && "C".equals(closedQuestion.getLetraCorreta()) ? "selected" : "" %>>C</option>
                                    <option value="D" <%= closedQuestion != null && "D".equals(closedQuestion.getLetraCorreta()) ? "selected" : "" %>>D</option>
                                </select>
                            </div>
                        </div>
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
