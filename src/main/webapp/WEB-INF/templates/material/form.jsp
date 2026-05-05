<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>Formulário de Material</title></head>
<body>
    <h2>Criar/Editar Material</h2>
    <form action="MaterialController" method="post">
        <input type="hidden" name="action" value="${param.action != null ? param.action : 'criar'}"/>
        <input type="hidden" name="disciplinaId" value="${disciplina.id}"/>

        <label>Título:</label>
        <input type="text" name="titulo" value="${material.titulo}" required/><br/>

        <label>Conteúdo:</label>
        <textarea name="conteudo" required>${material.conteudo}</textarea><br/>

        <button type="submit">Salvar</button>
    </form>
</body>
</html>
