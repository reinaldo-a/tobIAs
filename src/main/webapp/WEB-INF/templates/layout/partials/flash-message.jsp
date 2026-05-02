<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="${not empty flashMessage}">
    <div class="alert alert-${flashType}">
        ${flashMessage}
    </div>
    <%
        com.tobias.application.FlashMessage.clear(session);
    %>
</c:if>
