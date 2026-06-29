<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Вход — Менеджер Маршрутов</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="brand-bar">
        <div class="mark">Route<span>//</span>Manager</div>
    </div>

    <div class="page" style="display: flex; justify-content: center;">
        <div style="max-width: 440px; width: 100%;">
            <div class="page-header">
                <span class="kicker">Доступ к системе</span>
                <h1 class="giant-title">Вход в систему</h1>
            </div>

            <div class="panel">
                <p class="msg error">${error}</p>
                <p class="msg success">${message}</p>

                <form action="${pageContext.request.contextPath}/auth" method="POST">
                    <div class="field">
                        <label for="login">Логин</label>
                        <input type="text" id="login" name="login" required autocomplete="username">
                    </div>

                    <div class="field">
                        <label for="password">Пароль</label>
                        <input type="password" id="password" name="password" required autocomplete="current-password">
                    </div>

                    <div class="btn-row">
                        <button type="submit" class="btn full" name="action" value="login">Войти</button>
                        <button type="submit" class="btn secondary full" name="action" value="register">Регистрация</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
