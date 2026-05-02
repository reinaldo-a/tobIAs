<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.tobias.model.Discipline" %>

<div class="custom-container">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="h4 mb-0">Listagem de Disciplinas</h2>
        <div class="d-flex gap-2">
            <a href="${pageContext.request.contextPath}/Disciplines?action=enter" class="btn btnadd text-white">
                <img src="${pageContext.request.contextPath}/assets/images/enter.png" alt="botão entrar na disciplina">
                Entrar
            </a>
            <a href="${pageContext.request.contextPath}/Disciplines?action=new" class="btn btnadd text-white">
                <img src="${pageContext.request.contextPath}/assets/images/btnadd.png" alt="botão adicionar disciplina">
                Criar
            </a>
        </div>
    </div>

    <div class="card border-0 shadow-sm">
        <div class="table-responsive">
            <table class="table table-hover align-middle mb-0">
                <thead class="bg-light">
                    <tr>
                        <th>Id</th>
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