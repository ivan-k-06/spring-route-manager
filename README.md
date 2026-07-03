# Spring Route Manager 🗺️
![Java](https://img.shields.io/badge/Java-17+-orange.svg)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?logo=spring-boot&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?logo=Hibernate&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?logo=postgresql&logoColor=white)
![HTML/CSS/JS](https://img.shields.io/badge/Frontend-Vanilla_JS_%7C_JSP-E34F26)
![Docker](https://img.shields.io/badge/-Docker-2496ED?logo=Docker&logoColor=white)

**Spring Route Manager** — это веб-приложение для управления коллекцией маршрутов. Пользователи могут регистрироваться в системе, просматривать общую базу маршрутов и добавлять свои собственные

> Изначально проект был написан на чистом Jakarta Servlet API + JDBC. 
В рамках развития архитектуры проект был успешно мигрирован на **Spring Boot 3**, **Spring MVC** и **Spring Data JPA (Hibernate)**. 
Это позволило избавиться от boilerplate-кода, делегировать управление транзакциями и маппингом таблиц фреймворку, а также использовать встроенный сервер Tomcat

## Технологический стек
* **Язык:** Java 17
* **Фреймворк:** Spring Boot 3
* **Бэкенд:** Spring Web MVC, Spring Data JPA (Hibernate)
* **Фронтенд:** HTML/CSS, JavaScript, JSP, JSTL
* **База данных:** PostgreSQL 15
* **Сборка:** Maven
* **Инфраструктура:** Docker, Docker Compose

## Основной функционал
* Регистрация и авторизация пользователей
* Сессионная аутентификация
* SPA-подобный интерфейс
* Интерактивная таблица маршрутов:
   * **Live Search** по названию и владельцу
   * **Сортировка** по клику на заголовки таблицы
* Автоматическая генерация структуры базы данных

## Как запустить проект (Docker)

**Требования:** Установленный Docker и Docker Compose

1. Склонируйте репозиторий:
   ```bash
   git clone https://github.com/ivan-k-06/spring-route-manager.git
   cd spring-route-manager
   ```

2. Запустите приложение с помощью Docker Compose:
   ```bash
   docker-compose up --build
   ```

3. Откройте в браузере:
   👉 **http://localhost:8080/auth** 

*Для остановки нажмите `Ctrl+C`. Чтобы удалить базу данных и начать с нуля, выполните `docker-compose down -v`*

## Структура проекта
Проект реализован по классическому паттерну **MVC (Model-View-Controller)**:

* `src/main/java/.../model/` — **(M)odel**: JPA-сущности бизнес-логики
* `src/main/java/.../repository/` — **(M)odel**: Слой доступа к данным
* `src/main/webapp/WEB-INF/views/` — **(V)iew**: JSP-шаблоны страниц
* `src/main/java/.../web/controllers/` — **(C)ontroller**: Spring REST/MVC Контроллеры, маршрутизирующие запросы браузера