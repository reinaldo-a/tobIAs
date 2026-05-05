<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="custom-container">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <div>
            <h2 class="h4 mb-0">Nova Atividade</h2>
            <p class="text-muted mb-0">Preencha os dados da atividade e adicione as questões.</p>
        </div>
        <a href="${pageContext.request.contextPath}/Disciplines?action=view&id=${param.disciplineId}" class="btn btn-outline-secondary">
            Voltar
        </a>
    </div>

    <div class="card border-0 shadow-sm p-4">
        <form action="${pageContext.request.contextPath}/Activity" method="post">
            <input type="hidden" name="action" value="new">
            <input type="hidden" name="disciplineId" value="${param.disciplineId}">

            <div class="row g-3">
                <div class="col-md-8">
                    <label class="form-label">Qual é a atividade?</label>
                    <input type="text" class="form-control" name="title" placeholder="Ex: Lista de exercícios 1" required>
                </div>

                <div class="col-md-4">
                    <label class="form-label">Peso da atividade</label>
                    <input type="number" class="form-control" name="weight" min="0" step="0.1" placeholder="Ex: 2.0">
                </div>

                <div class="col-md-4">
                    <label class="form-label">Data de atribuição</label>
                    <input type="date" class="form-control" name="submitDate" required>
                </div>

                <div class="col-md-4">
                    <label class="form-label">Data de entrega</label>
                    <input type="date" class="form-control" name="deliveryDate">
                </div>
            </div>

            <hr class="my-4">

            <div class="d-flex justify-content-between align-items-center mb-3">
                <h5 class="mb-0">Questões</h5>
                <button type="button" class="btn btn-sm btnadd text-white" id="add-question">
                    <i class="ti ti-plus"></i>
                    Adicionar questão
                </button>
            </div>

            <div id="questions-list" class="d-grid gap-3">
                <div class="border rounded p-3 question-item">
                    <div class="d-flex justify-content-between align-items-center mb-3">
                        <strong>Questão 1</strong>
                        <button type="button" class="btn btn-outline-danger btn-sm remove-question">
                            <i class="ti ti-trash"></i>
                            Remover
                        </button>
                    </div>
                    <div class="row g-3">
                        <div class="col-md-9">
                            <label class="form-label">Enunciado</label>
                            <textarea class="form-control" name="questionText" rows="3" placeholder="Digite o enunciado da questão" required></textarea>
                        </div>
                        <div class="col-md-3">
                            <label class="form-label">Peso</label>
                            <input type="number" class="form-control" name="questionWeight" min="0" step="0.1" placeholder="Ex: 1.0">
                        </div>
                    </div>
                </div>
            </div>

            <div class="d-flex gap-2 mt-4">
                <button type="submit" class="btn btn-primary">Salvar Atividade</button>
                <a href="${pageContext.request.contextPath}/Disciplines?action=view&id=${param.disciplineId}" class="btn btn-outline-secondary">Cancelar</a>
            </div>
        </form>
    </div>
</div>
