<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Вход</title>
    <link rel="stylesheet" href="auth.css">
</head>
<body>

<div class="container">
    <h2>Вход</h2>

    <% String error = (String) request.getAttribute("error"); %>
    <% if (error != null) { %>
    <div id="errorMsg"><%= error %></div>
    <% } %>

    <form method="post" action="login">
        <label>Email:</label>
        <input type="email" name="email" placeholder="Введите email" required>

        <label>Пароль:</label>
        <input type="password" name="password" placeholder="Введите пароль" required>

        <input type="submit" value="Войти">
    </form>

    <p>Нет аккаунта? <a href="register.jsp">Регистрация</a></p>
</div>

</body>
</html>
