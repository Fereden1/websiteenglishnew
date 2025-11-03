<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <title>Регистрация</title>
</head>
<body>
<h2>Регистрация</h2>

<form action="register" method="post" id="registerForm">
    <label>Имя:</label><br>
    <input type="text" name="name" required><br><br>

    <label>Email:</label><br>
    <input type="email" name="email" id="emailInput" required><br>
    <span id="emailError" style="color:red;"></span><br><br>

    <label>Пароль:</label><br>
    <input type="password" name="password" required><br><br>

    <input type="submit" value="Зарегистрироваться">
</form>

<p>Уже есть аккаунт? <a href="login.jsp">Войти</a></p>

<script>
    const emailInput = document.getElementById('emailInput');
    const emailError = document.getElementById('emailError');
    const form = document.getElementById('registerForm');

    emailInput.addEventListener('blur', function() {
        const email = this.value;
        if (!email) return;

        const contextPath = '<%= request.getContextPath() %>';
        fetch(contextPath + '/check/email?email=' + encodeURIComponent(email))
                .then(res => res.text())
            .then(msg => {
                emailError.textContent = msg;
            });
    });

    // Предотвратим отправку формы, если email занят
    form.addEventListener('submit', function(event) {
        if (emailError.textContent) {
            event.preventDefault();
            alert("Исправьте ошибки перед отправкой формы.");
        }
    });
</script>
</body>
</html>
