<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<html>
<head>
    <title>Маршруты — Менеджер Маршрутов</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="brand-bar">
        <div class="mark">Route<span>//</span>Manager</div>
        <a class="logout" href="${pageContext.request.contextPath}/logout">Выйти</a>
    </div>

    <div class="page">
        <span class="kicker">Пользователь: ${currentUser}</span>
        <h1 class="giant-title">Управление<br>базой<span class="accent">.</span></h1>

        <div class="tabs-nav">
            <button class="tab-btn active" onclick="openTab('tab-table', this)">📋 Все маршруты</button>
            <button class="tab-btn" onclick="openTab('tab-form', this)">➕ Добавить новый</button>
        </div>

        <div id="tab-table" class="tab-content active">
            <div class="search-container">
                <input type="text" id="searchInput" class="search-input" onkeyup="filterTable()" placeholder="🔍 Поиск по названию или владельцу...">
            </div>

            <div class="route-table-wrap">
                <table class="routes" id="routesTable">
                    <thead>
                    <tr>
                        <th onclick="sortTable(0)">ID</th>
                        <th onclick="sortTable(1)">Название</th>
                        <th>Координаты</th>
                        <th>Откуда</th>
                        <th>Куда</th>
                        <th onclick="sortTable(5)">Дистанция</th>
                        <th onclick="sortTable(6)">Владелец</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="route" items="${routes}">
                        <tr>
                            <td><span class="route-id">#${route.id}</span></td>
                            <td><span class="route-name">${route.name}</span></td>
                            <td>
                                <span class="coord-tag">
                                    <span><span class="axis">X</span>${route.coordinates.x}</span>
                                    <span><span class="axis">Y</span>${route.coordinates.y}</span>
                                </span>
                            </td>
                            <td>
                                <span class="coord-tag">
                                    <span><span class="axis">X</span>${route.from.x}</span>
                                    <span><span class="axis">Y</span>${route.from.y}</span>
                                    <span><span class="axis">Z</span>${route.from.z}</span>
                                </span>
                            </td>
                            <td>
                                <span class="coord-tag">
                                    <span><span class="axis">X</span>${route.to.x}</span>
                                    <span><span class="axis">Y</span>${route.to.y}</span>
                                    <span><span class="axis">Z</span>${route.to.z}</span>
                                </span>
                            </td>
                            <td><span class="distance-val">${route.distance}</span></td>
                            <td>${route.owner}</td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty routes}">
                        <tr class="empty-row">
                            <td colspan="7">Маршрутов пока нет. Перейдите во вкладку добавления!</td>
                        </tr>
                    </c:if>
                    </tbody>
                </table>
            </div>
        </div>

        <div id="tab-form" class="tab-content">
            <div class="panel wide">
                <form action="${pageContext.request.contextPath}/routes" method="POST">
                    <div class="field">
                        <label for="name">Название маршрута</label>
                        <input type="text" id="name" name="name" required placeholder="Например: Москва-Петербург">
                    </div>

                    <div class="field-group">
                        <p class="group-title">Координаты объекта</p>
                        <div class="field-row">
                            <div class="field"><label for="coordX">X</label><input type="number" step="0.01" id="coordX" name="coordX" required></div>
                            <div class="field"><label for="coordY">Y <span class="constraint-badge">Y ≤ 71</span></label><input type="number" max="71" id="coordY" name="coordY" required></div>
                        </div>
                    </div>

                    <div class="field-group">
                        <p class="group-title">Точка отправления</p>
                        <div class="field-row">
                            <div class="field"><label for="fromX">X</label><input type="number" id="fromX" name="fromX" required></div>
                            <div class="field"><label for="fromY">Y</label><input type="number" id="fromY" name="fromY" required></div>
                            <div class="field"><label for="fromZ">Z</label><input type="number" step="0.01" id="fromZ" name="fromZ" required></div>
                        </div>
                    </div>

                    <div class="field-group">
                        <p class="group-title">Точка прибытия</p>
                        <div class="field-row">
                            <div class="field"><label for="toX">X</label><input type="number" id="toX" name="toX" required></div>
                            <div class="field"><label for="toY">Y</label><input type="number" id="toY" name="toY" required></div>
                            <div class="field"><label for="toZ">Z</label><input type="number" step="0.01" id="toZ" name="toZ" required></div>
                        </div>
                    </div>

                    <div class="field" style="max-width: 220px;">
                        <label for="distance">Дистанция <span class="constraint-badge">> 1</span></label>
                        <input type="number" step="0.01" min="1.01" id="distance" name="distance" required>
                    </div>

                    <button type="submit" class="btn" style="margin-top: 16px;">💾 Сохранить маршрут</button>
                </form>
            </div>
        </div>
    </div>

    <script>
        function openTab(tabId, btn) {
            document.querySelectorAll('.tab-content').forEach(el => el.classList.remove('active'));
            document.querySelectorAll('.tab-btn').forEach(el => el.classList.remove('active'));
            document.getElementById(tabId).classList.add('active');
            btn.classList.add('active');
        }

        function filterTable() {
            const input = document.getElementById("searchInput").value.toUpperCase();
            const rows = document.querySelectorAll("#routesTable tbody tr:not(.empty-row)");

            rows.forEach(row => {
                const name = row.cells[1].innerText.toUpperCase();
                const owner = row.cells[6].innerText.toUpperCase();
                if (name.indexOf(input) > -1 || owner.indexOf(input) > -1) {
                    row.style.display = "";
                } else {
                    row.style.display = "none";
                }
            });
        }

        let sortDirections = {};
        function sortTable(columnIndex) {
            const table = document.getElementById("routesTable");
            const tbody = table.tBodies[0];
            const rows = Array.from(tbody.querySelectorAll("tr:not(.empty-row)"));

            if (rows.length === 0) return;

            let dir = sortDirections[columnIndex] === 'asc' ? 'desc' : 'asc';
            sortDirections[columnIndex] = dir;

            rows.sort((a, b) => {
                let valA = a.cells[columnIndex].innerText.trim().replace('#', '');
                let valB = b.cells[columnIndex].innerText.trim().replace('#', '');

                const numA = parseFloat(valA);
                const numB = parseFloat(valB);

                if (!isNaN(numA) && !isNaN(numB)) {
                    return dir === 'asc' ? numA - numB : numB - numA;
                }
                return dir === 'asc' ? valA.localeCompare(valB) : valB.localeCompare(valA);
            });

            rows.forEach(row => tbody.appendChild(row));
        }
    </script>
</body>
</html>