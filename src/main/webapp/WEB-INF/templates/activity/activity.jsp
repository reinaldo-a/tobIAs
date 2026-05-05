<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="custom-container">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <div>
            <h2 class="h4 mb-0">Atividades</h2>
            <p class="text-muted mb-0">Gerencie as atividades da disciplina.</p>
        </div>
        <a href="${pageContext.request.contextPath}/Activity?action=new&disciplineId=${param.disciplineId}" class="btn btnadd text-white">
            <img src="${pageContext.request.contextPath}/assets/images/btnadd.png" alt="botão adicionar atividade">
            Criar Atividade
        </a>
    </div>

    <div class="card border-0 shadow-sm p-4">
        <p class="text-muted text-center py-4 mb-0">Nenhuma atividade cadastrada ainda.</p>
    </div>
</div>
