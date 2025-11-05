<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:if test="${sessionScope.userEmail == null}">
    <c:redirect url="login.jsp" />
</c:if>

<c:set var="pageTitle" value="Редактировать профиль" scope="request" />
<jsp:include page="/WEB-INF/fragments/header.jsp" />

<style>
    body { font-family: Arial; background-color: #f8f8f8; }
    .container { width: 400px; margin: 60px auto; background: white; padding: 20px; border-radius: 10px; }
    input[type=text], input[type=password] {
        width: 100%; padding: 8px; margin-top: 5px; margin-bottom: 10px;
    }
    button { padding: 8px 16px; }
</style>

<div class="container">
    <h2>Редактирование профиля</h2>
    <form method="post" action="${pageContext.request.contextPath}/profile">
        <label>Email:</label><br>
        <input type="text" name="email" value="<c:out value='${requestScope.user.email}' />" readonly><br>

        <label>Имя:</label><br>
        <input type="text" name="name" value="<c:out value='${requestScope.user.name}' />" required><br>

        <label>Новый пароль (необязательно):</label><br>
        <input type="password" name="password" placeholder="Оставьте пустым, чтобы не менять"><br>

        <button type="submit">Сохранить</button>
        <a href="${pageContext.request.contextPath}/profile">Отмена</a>
    </form>
</div>

<jsp:include page="/WEB-INF/fragments/footer.jsp" />
