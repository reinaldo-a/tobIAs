<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.tobias.model.Discipline" %>

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
                        <th>Id</th>
                        <th>Nome</th>
                        <th>Código</th>
                    </tr>
                </thead>
                <tbody>
                    <% 
                        List<Discipline> lista = (List<Discipline>) request.getAttribute("listaDisciplines");
                        
                        if (lista != null && !lista.isEmpty()) {
                            // Faz o loop usando Java puro
                            for (Discipline disciplina : lista) { 
                    %>
                                <tr>
                                    <td>#<%= disciplina.getId() %></td>
                                    <td><%= disciplina.getName() %></td>
                                    <td><%= disciplina.getCode() %></td>
                                </tr>
                    <% 
                            } 
                        } else {
                    %>
                            <tr>
                                <td colspan="4" class="text-center text-muted py-3">Nenhuma disciplina cadastrada ainda.</td>
                            </tr>
                    <% 
                        } 
                    %>
                </tbody>
            </table>
        </div>
    </div>
</div>