<#if !userEmail??>
    <script>window.location.href = 'login';</script>
<#else>
    <#assign pageTitle = "Редактировать профиль" />
    <#assign extraStyles = ["editProfile.css"] />


    <div class="container">
        <h2>Редактирование профиля</h2>
        <form method="post" action="profile">
            <label>Email:</label><br>
            <input type="text" name="email" value="${(user.email)!}" readonly><br>

            <label>Имя:</label><br>
            <input type="text" name="name" value="${(user.name)!}" required><br>

            <label>Новый пароль (необязательно):</label><br>
            <input type="password" name="password" placeholder="Оставьте пустым, чтобы не менять"><br>

            <button type="submit">Сохранить</button>
            <a href="profile">Отмена</a>
        </form>
    </div>


</#if>
