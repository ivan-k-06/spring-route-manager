<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Регистрация — Менеджер Маршрутов</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .auth-link { display: block; text-align: center; margin-top: 24px; font-size: 14px; font-weight: 600; color: var(--ink); opacity: 0.7; text-decoration: none; }
        .auth-link:hover { opacity: 1; color: var(--signal); }
    </style>
</head>
<body>
    <div class="brand-bar">
        <div class="mark">Route<span>//</span>Manager</div>
        <a class="logout" href="${pageContext.request.contextPath}/">На главную</a>
    </div>

    <div class="page" style="display: flex; justify-content: center; margin-top: 40px;">
        <div style="max-width: 440px; width: 100%;">
            <span class="kicker">Новый аккаунт</span>
            <h1 class="giant-title">Регистрация<span class="accent">.</span></h1>

            <div class="panel" style="margin-top: 32px;">
                <p class="msg error">${error}</p>

                <form action="${pageContext.request.contextPath}/register" method="POST">
                    <div class="field">
                        <label for="login">Придумайте логин</label>
                        <input type="text" id="login" name="login" required autocomplete="username" placeholder="Например: alex_travel">
                    </div>

                    <div class="field">
                        <label for="password">Придумайте пароль</label>
                        <input type="password" id="password" name="password" required autocomplete="new-password">
                    </div>

                    <button type="submit" class="btn secondary full" style="border-color: var(--signal); color: var(--signal);">Создать аккаунт</button>

                    <a href="${pageContext.request.contextPath}/login" class="auth-link">Уже есть аккаунт? Войти</a>
                </form>
            </div>
        </div>
    </div>
</body>
</html>