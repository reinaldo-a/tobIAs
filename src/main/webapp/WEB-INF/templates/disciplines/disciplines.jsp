<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="custom-container">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="h4 mb-0">Listagem de Disciplinas</h2>
        <a href="${pageContext.request.contextPath}/Disciplines?action=new" class="btn btn-primary">
            <i class="ti ti-plus"></i> Nova Disciplina
        </a>
    </div>

    <div class="card border-0 shadow-sm">
        <div class="table-responsive">
            <table class="table table-hover align-middle mb-0">
                <thead class="bg-light">
                    <tr>
                        <th>Cód.</th>
                        <th>Nome</th>
                        <th>Carga Horária</th>
                        <th class="text-end">Ações</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>#01</td>
                        <td>Programação Orientada a Objetos</td>
                        <td>80h</td>
                        <td class="text-end">
                            <button class="btn btn-sm btn-outline-secondary"><i class="ti ti-edit"></i></button>
                            <button class="btn btn-sm btn-outline-danger"><i class="ti ti-trash"></i></button>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>

<button type="submit">
    <img src="../imagens/botaozinluxo.png" alt="imagem botao adicionar">
</button>