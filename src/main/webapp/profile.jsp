<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String email = (String) session.getAttribute("userEmail");
    String name = (String) session.getAttribute("userName");
    String role = (String) session.getAttribute("userRole");

    if (email == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<html>
<head>
    <title>Личный кабинет</title>
    <style>
        body { font-family: Arial; background-color: #f8f8f8; }
        .container { width: 400px; margin: 60px auto; background: white; padding: 20px; border-radius: 10px; }
        .btn { display: inline-block; padding: 6px 12px; text-decoration: none; color: white; border-radius: 4px; }
        .btn-edit { background: #007bff; }
        .btn-delete { background: #dc3545; }
        .btn-logout { background: #6c757d; }
    </style>
</head>
<body>
<div class="container">
    <h2>Личный кабинет</h2>
    <p><b>Email:</b> <%= email %></p>
    <p><b>Имя:</b> <%= name %></p>
    <p><b>Роль:</b> <%= role %></p>


    <a href="profile/edit" class="btn btn-edit">Редактировать</a>
    <a href="profile/delete" class="btn btn-delete" onclick="return confirm('Удалить аккаунт?')">Удалить</a>
    <a href="logout" class="btn btn-logout">Выйти</a>
</div>
</body>
</html>
