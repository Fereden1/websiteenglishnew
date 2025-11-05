<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="title" value="${requestScope.pageTitle != null ? requestScope.pageTitle : 'Home English School'}" />
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title><c:out value="${title}" /></title>
    <link rel="stylesheet" href="style.css">
    <script>
        // Плавная прокрутка
        document.addEventListener("DOMContentLoaded", () => {
            document.querySelectorAll('a[href^="#"]').forEach(link => {
                link.addEventListener("click", function(e) {
                    e.preventDefault();
                    const target = document.querySelector(this.getAttribute("href"));
                    if (target) target.scrollIntoView({ behavior: "smooth" });
                });
            });
        });
    </script>
</head>
<body>

<!-- ===== HEADER ===== -->
<header class="hdr">
    <div class="wrap">
        <div class="logo"><a href="index.jsp">Home English School</a></div>

        <nav class="menu">
            <a href="index.jsp#courses">Курсы</a>
            <a href="index.jsp#advantages">Преимущества</a>
            <a href="index.jsp#process">Как учим</a>
            <a href="chat.jsp">Отзывы</a>
        </nav>

        <div class="auth">
            <c:choose>
                <c:when test="${sessionScope.userName != null}">
                    <span>Привет, <a href="profile.jsp"><c:out value="${sessionScope.userName}" /></a></span>
                    <button class="btnn quit" onclick="window.location.href='logout'">Выйти</button>
                </c:when>
                <c:otherwise>
                    <button class="btnn" onclick="window.location.href='register.jsp'">Регистрация</button>
                    <button class="btnn" onclick="window.location.href='login.jsp'">Войти</button>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</header>

