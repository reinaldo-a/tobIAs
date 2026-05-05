<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><title>Materiais da Disciplina</title></head>
<body>
    <h2>Materiais da Disciplina: ${disciplina.nome}</h2>

    <a href="form.jsp?disciplinaId=${disciplina.id}&action=criar">Novo Material</a>
    <ul>
        <c:forEach var="material" items="${materiais}">
            <li>
                <a href="view.jsp?id=${material.id}">${material.titulo}</a>
                | <a href="form.jsp?id=${material.id}&action=editar">Editar</a>
                | <form action="MaterialController" method="post" style="display:inline;">
                    <input type="hidden" name="action" value="remover"/>
                    <input type="hidden" name="id" value="${material.id}"/>
                    <button type="submit">Remover</button>
                  </form>
            </li>
        </c:forEach>
    </ul>
</body>
</html>
