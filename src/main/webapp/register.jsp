<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Регистрация</title>
    <link rel="stylesheet" href="auth.css">
</head>
<body>

<div class="container">
    <h2>Регистрация</h2>

    <% String error = (String) request.getAttribute("error"); %>
    <% if (error != null) { %>
    <div id="errorMsg"><%= error %></div>
    <% } %>

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

</body>
</html>
