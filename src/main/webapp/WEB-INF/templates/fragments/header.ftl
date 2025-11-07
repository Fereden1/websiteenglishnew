<#assign pageTitle = (pageTitle!)?default("Home English School") />
<#assign _extraStyles = [] />
<#if extraStyles??>
    <#if extraStyles?is_sequence>
        <#assign _extraStyles = extraStyles />
    <#else>
        <#assign _extraStyles = [extraStyles] />
    </#if>
</#if>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>${pageTitle}</title>
    <link rel="stylesheet" href="style.css">
    <#if includeAuthStyles?? && includeAuthStyles>
        <link rel="stylesheet" href="auth.css">
    </#if>
    <#list _extraStyles as stylePath>
        <link rel="stylesheet" href="${stylePath}">
    </#list>
</head>
<body<#if includeAuthStyles?? && includeAuthStyles> class="auth-layout"</#if>>

<header class="hdr">
    <div class="wrap">
        <div class="logo"><a href="index">Home English School</a></div>

        <nav class="menu">
            <a href="index#courses">Курсы</a>
            <a href="index#advantages">Преимущества</a>
            <a href="index#process">Как учим</a>
            <a href="chat">Отзывы</a>
        </nav>

        <div class="auth">
            <#if userName??>
                <span>Привет, <a href="profile">${userName}</a></span>
                <a class="btnn quit" href="logout">Выйти</a>
            <#else>
                <a class="btnn" href="register">Регистрация</a>
                <a class="btnn" href="login">Войти</a>
            </#if>
        </div>
    </div>
</header>

<#if errorMessage?? || successMessage??>
    <div id="messageContainer" class="page-message <#if errorMessage??>page-message-error<#else>page-message-success</#if>">
        <div>
            <#if errorMessage??>
                <strong>Ошибка:</strong> ${errorMessage}
            <#elseif successMessage??>
                <strong>Успех:</strong> ${successMessage}
            </#if>
        </div>
        <button type="button" class="message-close" aria-label="Закрыть уведомление">&times;</button>
    </div>
</#if>

