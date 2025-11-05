<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<c:set var="errorMessage" value="${sessionScope.errorMessage}" />
<c:set var="successMessage" value="${sessionScope.successMessage}" />

<c:if test="${errorMessage != null || successMessage != null}">
    <div id="messageContainer" style="position: fixed; top: 80px; left: 50%; transform: translateX(-50%); z-index: 10000; max-width: 500px; padding: 15px 20px; border-radius: 5px; margin: 10px; box-shadow: 0 4px 6px rgba(0,0,0,0.1); 
        <c:choose>
            <c:when test="${errorMessage != null}">background-color: #f8d7da; color: #721c24; border: 1px solid #f5c6cb;</c:when>
            <c:otherwise>background-color: #d4edda; color: #155724; border: 1px solid #c3e6cb;</c:otherwise>
        </c:choose>">
        <c:choose>
            <c:when test="${errorMessage != null}">
                <strong>Ошибка:</strong> <c:out value="${errorMessage}" />
            </c:when>
            <c:when test="${successMessage != null}">
                <strong>Успех:</strong> <c:out value="${successMessage}" />
            </c:when>
        </c:choose>
        <button onclick="closeMessage()" style="float: right; background: none; border: none; font-size: 20px; cursor: pointer; margin-left: 10px;">&times;</button>
    </div>
    <script>
        function closeMessage() {
            document.getElementById('messageContainer').style.display = 'none';
        }
        setTimeout(function() {
            var msg = document.getElementById('messageContainer');
            if (msg) msg.style.display = 'none';
        }, 5000);
    </script>
    <c:remove var="errorMessage" scope="session" />
    <c:remove var="successMessage" scope="session" />
</c:if>

