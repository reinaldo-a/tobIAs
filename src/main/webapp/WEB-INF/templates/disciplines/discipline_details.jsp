<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

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
            <button class="nav-link active" id="materiais-tab" data-bs-toggle="tab" data-bs-target="#materiais" type="button" role="tab">
                📚 Materiais
            </button>
        </li>
        <li class="nav-item" role="presentation">
            <button class="nav-link" id="atividades-tab" data-bs-toggle="tab" data-bs-target="#atividades" type="button" role="tab">
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
        

        <div class="tab-pane fade show active" id="materiais" role="tabpanel">
            <div class="card border-0 shadow-sm p-4">
                <div class="d-flex justify-content-between align-items-center mb-3">
                    <h5 class="mb-0">Materiais de Apoio</h5>
                    <button class="btn btn-sm botnadd text-white">+ Novo Material</button>
                </div>
                <hr>
                <p class="text-muted text-center py-4">Nenhum material disponibilizado pelo professor ainda.</p>
            </div>
        </div>

        <div class="tab-pane fade" id="atividades" role="tabpanel">
            <div class="card border-0 shadow-sm p-4">
                <div class="d-flex justify-content-between align-items-center mb-3">
                    <h5 class="mb-0">Atividades Avaliativas</h5>
                    <button class="btn btn-sm botnadd text-white">+ Nova Atividade</button>
                </div>
                <hr>
                <p class="text-muted text-center py-4">Nenhuma atividade no momento. Pode descansar!</p>
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