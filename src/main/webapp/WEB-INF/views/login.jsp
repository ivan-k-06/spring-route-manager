<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Вход — Менеджер Маршрутов</title>
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
            <span class="kicker">С возвращением</span>
            <h1 class="giant-title">Вход<br>в систему<span class="accent">.</span></h1>

            <div class="panel" style="margin-top: 32px;">
                <p class="msg error">${error}</p>
                <p class="msg success">${message}</p>

                <form action="${pageContext.request.contextPath}/login" method="POST">
                    <div class="field">
                        <label for="login">Логин</label>
                        <input type="text" id="login" name="login" required autocomplete="username">
                    </div>

                    <div class="field">
                        <label for="password">Пароль</label>
                        <input type="password" id="password" name="password" required autocomplete="current-password">
                    </div>

                    <button type="submit" class="btn full">Войти</button>

                    <a href="${pageContext.request.contextPath}/register" class="auth-link">Ещё нет аккаунта? Зарегистрируйтесь</a>
                </form>
            </div>
        </div>
    </div>
</body>
</html>