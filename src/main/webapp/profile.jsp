<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:if test="${sessionScope.userEmail == null}">
    <c:redirect url="login.jsp" />
</c:if>

<c:set var="pageTitle" value="Личный кабинет" scope="request" />
<jsp:include page="/WEB-INF/fragments/header.jsp" />

<style>
    body { font-family: Arial; background-color: #f8f8f8; }
    .container { width: 400px; margin: 60px auto; background: white; padding: 20px; border-radius: 10px; }
    .btn { display: inline-block; padding: 8px 16px; text-decoration: none; color: white; border-radius: 6px; margin: 5px; transition: all 0.3s ease; font-weight: 500; }
    .btn:hover { transform: translateY(-2px); box-shadow: 0 4px 8px rgba(0,0,0,0.2); }
    .btn-edit { background: #007bff; }
    .btn-edit:hover { background: #0056b3; }
    .btn-delete { background: #dc3545; }
    .btn-delete:hover { background: #c82333; }
    .btn-logout { background: #6c757d; }
    .btn-logout:hover { background: #5a6268; }
    .btn-home { background: #28a745; }
    .btn-home:hover { background: #218838; }
    .btn-container { margin-top: 20px; display: flex; flex-wrap: wrap; gap: 10px; }
    .message { padding: 10px; margin: 10px 0; border-radius: 5px; }
    .message-error { background-color: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }
    .message-success { background-color: #d4edda; color: #155724; border: 1px solid #c3e6cb; }
</style>

<div class="container">
    <h2>Личный кабинет</h2>
    
    <c:set var="errorMessage" value="${sessionScope.errorMessage}" />
    <c:set var="successMessage" value="${sessionScope.successMessage}" />
    
    <c:if test="${errorMessage != null}">
        <div class="message message-error">
            <strong>Ошибка:</strong> <c:out value="${errorMessage}" />
        </div>
        <c:remove var="errorMessage" scope="session" />
    </c:if>
    
    <c:if test="${successMessage != null}">
        <div class="message message-success">
            <strong>Успех:</strong> <c:out value="${successMessage}" />
        </div>
        <c:remove var="successMessage" scope="session" />
    </c:if>
    
    <p><b>Email:</b> <c:out value="${sessionScope.userEmail}" /></p>
    <p><b>Имя:</b> <c:out value="${sessionScope.userName}" /></p>
    <p><b>Роль:</b> <c:out value="${sessionScope.userRole}" /></p>

    <div class="btn-container">
        <a href="index.jsp" class="btn btn-home">← Вернуться на главную</a>
        <a href="profile/edit" class="btn btn-edit">Редактировать</a>
        <a href="profile/delete" class="btn btn-delete" onclick="return confirm('Удалить аккаунт?')">Удалить</a>
        <a href="logout" class="btn btn-logout">Выйти</a>
    </div>
</div>

<jsp:include page="/WEB-INF/fragments/footer.jsp" />
