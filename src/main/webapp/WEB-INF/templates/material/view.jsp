<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>Visualizar Material</title></head>
<body>
    <h2>${material.titulo}</h2>
    <p>${material.conteudo}</p>

    <a href="list.jsp?disciplinaId=${material.disciplina.id}">Voltar para lista</a>
</body>
</html>
