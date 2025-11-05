<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Вход" scope="request" />
<jsp:include page="/WEB-INF/fragments/authHeader.jsp" />

<div class="container">
    <h2>Вход</h2>

    <c:if test="${requestScope.error != null}">
        <div id="errorMsg"><c:out value="${requestScope.error}" /></div>
    </c:if>

    <form method="post" action="login">
        <label>Email:</label>
        <input type="email" name="email" placeholder="Введите email" required>

        <label>Пароль:</label>
        <input type="password" name="password" placeholder="Введите пароль" required>

        <input type="submit" value="Войти">
    </form>

    <p>Нет аккаунта? <a href="register.jsp">Регистрация</a></p>
</div>

<jsp:include page="/WEB-INF/fragments/authFooter.jsp" />
