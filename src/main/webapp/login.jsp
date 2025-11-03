<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Вход</title>
</head>
<body>
<h2>Вход</h2>

<form method="post" action="login">
    <label>Email:</label><br>
    <input type="email" name="email" placeholder="Введите email" required><br><br>

    <label>Пароль:</label><br>
    <input type="password" name="password" placeholder="Введите пароль" required><br><br>

    <input type="submit" value="Войти">
</form>

<p>Нет аккаунта? <a href="register.jsp">Регистрация</a></p>

</body>
</html>
