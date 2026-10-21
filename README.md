# Spring Route Manager 🗺️
![Java](https://img.shields.io/badge/Java-17+-orange.svg)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?logo=spring-boot&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?logo=Hibernate&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?logo=postgresql&logoColor=white)
![React](https://img.shields.io/badge/React-19-61DAFB?logo=react&logoColor=black)
![TypeScript](https://img.shields.io/badge/TypeScript-6-3178C6?logo=typescript&logoColor=white)
![Docker](https://img.shields.io/badge/-Docker-2496ED?logo=Docker&logoColor=white)

**Spring Route Manager** — full-stack веб-приложение для организаторов туристических поездок. В нём можно сохранять маршруты для повторного использования, планировать поездки для групп и следить за ближайшими отправлениями.

Проект развивается поэтапно. Рабочая серверная часть использует Spring MVC и JSP. В каталоге `frontend` находится новый интерфейс на React и TypeScript: лендинг, сводная панель, страницы поездок и маршрутов. Сейчас интерфейс работает с демонстрационными данными; следующий этап — REST API и подключение к Spring Boot.

> Изначально проект был написан на чистом Jakarta Servlet API + JDBC. 
В рамках развития архитектуры проект был успешно мигрирован на **Spring Boot 3**, **Spring MVC** и **Spring Data JPA (Hibernate)**. 
Это позволило избавиться от boilerplate-кода, делегировать управление транзакциями и маппингом таблиц фреймворку, а также использовать встроенный сервер Tomcat

## Технологический стек
* **Язык:** Java 17
* **Фреймворк:** Spring Boot 3
* **Бэкенд:** Spring Web MVC, Spring Data JPA (Hibernate)
* **Фронтенд:** React 19, TypeScript, React Router, Vite, CSS
* **Текущий серверный UI:** JSP, JSTL, JavaScript
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
* Новый адаптивный React-интерфейс с отдельными страницами маршрутов и поездок

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
   👉 **http://localhost:8080/** 

*Для остановки нажмите `Ctrl+C`. Чтобы удалить базу данных и начать с нуля, выполните `docker-compose down -v`*

## Как запустить новый frontend

**Требования:** Node.js 24 и npm.

```bash
cd frontend
npm ci
npm run dev
```

Vite покажет локальный адрес в терминале. Проверки перед коммитом:

```bash
npm run lint
npm run build
```

## Структура проекта
Проект реализован по классическому паттерну **MVC (Model-View-Controller)**:

* `src/main/java/.../model/` — **(M)odel**: JPA-сущности бизнес-логики
* `src/main/java/.../repository/` — **(M)odel**: Слой доступа к данным
* `src/main/webapp/WEB-INF/views/` — **(V)iew**: JSP-шаблоны страниц
* `src/main/java/.../web/controllers/` — **(C)ontroller**: Spring REST/MVC Контроллеры, маршрутизирующие запросы браузера
* `frontend/src/pages/` — страницы нового React-интерфейса
* `frontend/src/components/` — общие компоненты и каркас приложения
* `frontend/src/data/` — временные демонстрационные данные

## Ближайшие задачи

* Выделить JSON API для маршрутов и авторизации
* Подключить React-интерфейс к Spring Boot вместо демонстрационных данных
* Добавить сущность поездки: маршрут, группа, дата и статус
* Добавить карту и выбор точек маршрута
