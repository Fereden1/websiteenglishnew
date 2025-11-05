<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Регистрация" scope="request" />
<jsp:include page="/WEB-INF/fragments/authHeader.jsp" />

<div class="container">
    <h2>Регистрация</h2>

    <c:if test="${requestScope.error != null}">
        <div id="errorMsg"><c:out value="${requestScope.error}" /></div>
    </c:if>

    <form action="register" method="post">
        <label>Имя:</label>
        <input type="text" name="name" required>

        <label>Email:</label>
        <input type="email" name="email" required>

        <label>Пароль:</label>
        <input type="password" name="password" required>

        <input type="submit" value="Зарегистрироваться">
    </form>

    <p>Уже есть аккаунт? <a href="login.jsp">Войти</a></p>
</div>

<jsp:include page="/WEB-INF/fragments/authFooter.jsp" />
