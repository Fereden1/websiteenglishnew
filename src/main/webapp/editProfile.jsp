<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Редактировать профиль</title>
    <style>
        body { font-family: Arial; background-color: #f8f8f8; }
        .container { width: 400px; margin: 60px auto; background: white; padding: 20px; border-radius: 10px; }
        input[type=text], input[type=password] {
            width: 100%; padding: 8px; margin-top: 5px; margin-bottom: 10px;
        }
        button { padding: 8px 16px; }
    </style>
</head>
<body>
<div class="container">
    <h2>Редактирование профиля</h2>
    <form method="post" action="${pageContext.request.contextPath}/profile">
        <label>Email:</label><br>
        <input type="text" name="email" value="${user.email}" readonly><br>

        <label>Имя:</label><br>
        <input type="text" name="name" value="${user.name}" required><br>

        <label>Новый пароль (необязательно):</label><br>
        <input type="password" name="password" placeholder="Оставьте пустым, чтобы не менять"><br>

        <button type="submit">Сохранить</button>
        <a href="${pageContext.request.contextPath}/profile">Отмена</a>
    </form>
</div>
</body>
</html>
