<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Route Manager — Главная</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .hero { text-align: center; margin-top: 64px; }
        .hero p {
            max-width: 600px;
            margin: 24px auto;
            font-size: 18px;
            line-height: 1.6;
            color: var(--ink);
            opacity: 0.8;
        }
        .features {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
            gap: 24px;
            margin-top: 80px;
        }
        .feature-card {
            border: 2px solid var(--ink);
            padding: 32px 24px;
            text-align: left;
            background: #fff;
            box-shadow: 4px 4px 0 var(--signal);
            transition: transform 0.2s;
        }
        .feature-card:hover { transform: translateY(-4px); box-shadow: 6px 6px 0 var(--signal); }
        .feature-title {
            font-family: 'JetBrains Mono', monospace;
            font-weight: 700;
            font-size: 16px;
            color: var(--signal-dark);
            margin-bottom: 12px;
            text-transform: uppercase;
        }
        .hero-btns {
            display: flex;
            justify-content: center;
            gap: 16px;
            margin-top: 40px;
        }
        a.btn { text-decoration: none; }
    </style>
</head>
<body>
    <div class="brand-bar">
        <div class="mark">Route<span>//</span>Manager</div>
    </div>

    <div class="page hero">
        <h1 class="giant-title" style="font-size: clamp(48px, 8vw, 88px);">
            Route Manager
        </h1>

        <p>Единая база маршрутов с интеграцией OpenStreetMap API, автоматическим расчетом дистанций и другими плюшками.</p>

        <div class="hero-btns">
            <a href="${pageContext.request.contextPath}/login" class="btn">Начать работу</a>
            <a href="${pageContext.request.contextPath}/register" class="btn secondary">Создать аккаунт</a>
        </div>

        <div class="features">
            <div class="feature-card">
                <div class="feature-title">🌍 Интеграция API</div>
                <div>Поиск городов по всему миру через Nominatim OSM.</div>
            </div>
            <div class="feature-card">
                <div class="feature-title">🧮 Smart-логика</div>
                <div>Автоматический расчет дистанции между точками маршрута.</div>
            </div>
            <div class="feature-card">
                <div class="feature-title">📊 Аналитика</div>
                <div>Личная и глобальная статистика.</div>
            </div>
        </div>
    </div>
</body>
</html>