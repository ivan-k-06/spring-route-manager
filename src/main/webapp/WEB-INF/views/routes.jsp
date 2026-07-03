<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<html>
<head>
    <title>Маршруты — Менеджер Маршрутов</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .autocomplete-wrapper { position: relative; width: 100%; }
        .autocomplete-list {
            position: absolute; top: 100%; left: 0; right: 0; z-index: 99;
            background: #fff; border: 2px solid var(--ink); border-top: none;
            max-height: 250px; overflow-y: auto; box-shadow: 4px 4px 0 var(--ink);
            display: none;
        }
        .autocomplete-item {
            padding: 12px; cursor: pointer; border-bottom: 1px solid var(--line-light);
            font-size: 13px; line-height: 1.2;
        }
        .autocomplete-item:hover { background-color: var(--signal); color: var(--paper); }
        .autocomplete-item strong { display: block; margin-bottom: 4px; font-size: 14px;}
        input[readonly] { background-color: #e9e8e2; cursor: not-allowed; }
    </style>
</head>
<body>
    <div class="brand-bar">
        <div class="mark">Route<span>//</span>Manager</div>
        <a class="logout" href="${pageContext.request.contextPath}/logout">Выйти</a>
    </div>

    <c:if test="${not empty toastSuccess}"><div class="toast success">✅ ${toastSuccess}</div></c:if>
    <c:if test="${not empty toastError}"><div class="toast error">❌ ${toastError}</div></c:if>

    <div class="page">
        <span class="kicker">Пользователь: ${currentUser}</span>
        <h1 id="pageTitle" class="giant-title">Управление<br>базой<span class="accent">.</span></h1>

        <div class="tabs-nav">
            <button class="tab-btn active" onclick="openTab('tab-table', this, 'Управление<br>базой<span class=\'accent\'>.</span>')">📋 Все маршруты</button>
            <button class="tab-btn" onclick="openTab('tab-form', this, 'Новый<br>маршрут<span class=\'accent\'>.</span>')">➕ Добавить новый</button>
            <button class="tab-btn" onclick="openTab('tab-stats', this, 'Статистика<br>маршрутов<span class=\'accent\'>.</span>')">📊 Статистика</button>
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
                        <th>Средние координаты</th>
                        <th>Откуда</th>
                        <th>Куда</th>
                        <th onclick="sortTable(5)">Дистанция</th>
                        <th onclick="sortTable(6)">Владелец</th>
                        <th>🗑️</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="route" items="${routes}">
                        <tr>
                            <td><span class="route-id">#${route.id}</span></td>
                            <td><span class="route-name">${route.name}</span></td>
                            <td>
                                <span style="font-size: 11px;">Lat: ${route.coordinates.latitude}</span><br>
                                <span style="font-size: 11px;">Lon: ${route.coordinates.longitude}</span>
                            </td>
                            <td>
                                <strong>${route.from.name}</strong><br>
                                <span style="font-size: 11px; opacity: 0.6;">${route.from.latitude}, ${route.from.longitude}</span>
                            </td>
                            <td>
                                <strong>${route.to.name}</strong><br>
                                <span style="font-size: 11px; opacity: 0.6;">${route.to.latitude}, ${route.to.longitude}</span>
                            </td>
                            <td><span class="distance-val">${route.distance}</span></td>
                            <td>${route.owner}</td>
                            <td>
                                <c:if test="${route.owner eq currentUser}">
                                    <form action="${pageContext.request.contextPath}/routes/delete/${route.id}" method="POST" style="margin: 0;" onsubmit="return confirm('Точно удалить?');">
                                        <button type="submit" class="btn-icon">🗑️</button>
                                    </form>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty routes}">
                        <tr class="empty-row"><td colspan="8">Маршрутов пока нет. Перейдите во вкладку добавления!</td></tr>
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

                    <input type="hidden" id="coordLat" name="coordLat" value="0.0">
                    <input type="hidden" id="coordLon" name="coordLon" value="0.0">

                    <div class="field-group">
                        <p class="group-title">Откуда</p>
                        <div class="field-row" style="grid-template-columns: 2fr 1fr 1fr;">
                            <div class="field">
                                <label>Город</label>
                                <div class="autocomplete-wrapper">
                                    <input type="text" id="fromName" name="fromName" autocomplete="off" required placeholder="Введите город...">
                                    <div id="fromSuggestions" class="autocomplete-list"></div>
                                </div>
                            </div>
                            <div class="field"><label>Широта</label><input type="number" step="any" id="fromLat" name="fromLat" required readonly></div>
                            <div class="field"><label>Долгота</label><input type="number" step="any" id="fromLon" name="fromLon" required readonly></div>
                        </div>
                    </div>

                    <div class="field-group">
                        <p class="group-title">Куда</p>
                        <div class="field-row" style="grid-template-columns: 2fr 1fr 1fr;">
                            <div class="field">
                                <label>Город</label>
                                <div class="autocomplete-wrapper">
                                    <input type="text" id="toName" name="toName" autocomplete="off" required placeholder="Введите город...">
                                    <div id="toSuggestions" class="autocomplete-list"></div>
                                </div>
                            </div>
                            <div class="field"><label>Широта</label><input type="number" step="any" id="toLat" name="toLat" required readonly></div>
                            <div class="field"><label>Долгота</label><input type="number" step="any" id="toLon" name="toLon" required readonly></div>
                        </div>
                    </div>

                    <div class="field" style="max-width: 220px;">
                        <label for="distance">Дистанция</label>
                        <input type="number" step="0.01" min="1.01" id="distance" name="distance" required readonly>
                    </div>
                    <button type="submit" class="btn" style="margin-top: 16px;">💾 Сохранить маршрут</button>
                </form>
            </div>
        </div>

        <div id="tab-stats" class="tab-content">
            <div class="section-label">Ваша личная статистика</div>
            <div class="stats-grid">
                <div class="stat-card"><div class="stat-title">Создано маршрутов</div><div class="stat-value">${totalRoutes}</div></div>
                <div class="stat-card"><div class="stat-title">Суммарная дистанция</div><div class="stat-value">${totalDistance} <span style="font-size:14px;opacity:0.5">KM</span></div></div>
                <div class="stat-card"><div class="stat-title">Средняя дистанция</div><div class="stat-value">${avgDistance} <span style="font-size:14px;opacity:0.5">KM</span></div></div>
                <div class="stat-card"><div class="stat-title">Самый длинный</div><div class="stat-value">${maxDistance} <span style="font-size:14px;opacity:0.5">KM</span></div></div>
                <div class="stat-card"><div class="stat-title">Самый короткий</div><div class="stat-value">${minDistance} <span style="font-size:14px;opacity:0.5">KM</span></div></div>
            </div>
            <div class="section-label">Глобальная статистика</div>
            <div class="stats-grid" style="grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));">
                <div class="stat-card" style="border-color: var(--signal); box-shadow: 4px 4px 0 var(--ink);"><div class="stat-title">Всего маршрутов</div><div class="stat-value">${globalTotalRoutes} <span style="font-size:14px;opacity:0.5">ШТ</span></div></div>
            </div>
        </div>
    </div>

    <script>
        function openTab(tabId, btn, newTitleHtml) {
            document.querySelectorAll('.tab-content').forEach(el => el.classList.remove('active'));
            document.querySelectorAll('.tab-btn').forEach(el => el.classList.remove('active'));
            document.getElementById(tabId).classList.add('active');
            btn.classList.add('active');
            document.getElementById('pageTitle').innerHTML = newTitleHtml;
        }

        function calculateDistance(lat1, lon1, lat2, lon2) {
            const R = 6371;
            const dLat = (lat2 - lat1) * Math.PI / 180;
            const dLon = (lon2 - lon1) * Math.PI / 180;
            const a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                      Math.cos(lat1 * Math.PI / 180) * Math.cos(lat2 * Math.PI / 180) *
                      Math.sin(dLon/2) * Math.sin(dLon/2);
            const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
            return (R * c).toFixed(2);
        }

        function updateRouteMath() {
            const lat1 = parseFloat(document.getElementById('fromLat').value);
            const lon1 = parseFloat(document.getElementById('fromLon').value);
            const lat2 = parseFloat(document.getElementById('toLat').value);
            const lon2 = parseFloat(document.getElementById('toLon').value);

            if (!isNaN(lat1) && !isNaN(lon1) && !isNaN(lat2) && !isNaN(lon2)) {
                const dist = calculateDistance(lat1, lon1, lat2, lon2);
                document.getElementById('distance').value = dist > 1 ? dist : 1.01;

                document.getElementById('coordLat').value = ((lat1 + lat2) / 2).toFixed(5);
                document.getElementById('coordLon').value = ((lon1 + lon2) / 2).toFixed(5);
            }
        }

        function setupAutocomplete(inputId, suggestionsId, latId, lonId) {
            const input = document.getElementById(inputId);
            const suggestionsBox = document.getElementById(suggestionsId);
            const latInput = document.getElementById(latId);
            const lonInput = document.getElementById(lonId);
            let timeoutId;

            input.addEventListener('input', function() {
                const query = this.value;
                if (query.length < 2) {
                    suggestionsBox.style.display = 'none';
                    return;
                }
                clearTimeout(timeoutId);
                timeoutId = setTimeout(() => {
                    fetch('/api/cities/search?q=' + encodeURIComponent(query))
                        .then(response => response.json())
                        .then(cities => {
                            suggestionsBox.innerHTML = '';
                            if (cities.length === 0) {
                                suggestionsBox.innerHTML = '<div class="autocomplete-item">Ничего не найдено</div>';
                            } else {
                                cities.forEach(city => {
                                    const div = document.createElement('div');
                                    div.className = 'autocomplete-item';

                                    const parts = city.name.split(',');
                                    const mainName = parts[0];
                                    const desc = parts.slice(1, 3).join(', ');

                                    div.innerHTML = '<strong>' + mainName + '</strong><span style="font-size:11px; opacity:0.7;">' + desc + '</span>';

                                    div.addEventListener('click', function() {
                                        input.value = mainName;
                                        latInput.value = city.lat;
                                        lonInput.value = city.lon;
                                        suggestionsBox.style.display = 'none';
                                        updateRouteMath();
                                    });
                                    suggestionsBox.appendChild(div);
                                });
                            }
                            suggestionsBox.style.display = 'block';
                        })
                        .catch(err => console.error("Ошибка поиска:", err));
                }, 400);
            });

            document.addEventListener('click', function(e) {
                if (e.target !== input && e.target !== suggestionsBox) {
                    suggestionsBox.style.display = 'none';
                }
            });
        }

        setupAutocomplete('fromName', 'fromSuggestions', 'fromLat', 'fromLon');
        setupAutocomplete('toName', 'toSuggestions', 'toLat', 'toLon');

        function filterTable() {
                    const input = document.getElementById("searchInput").value.toUpperCase();
                    const rows = document.querySelectorAll("#routesTable tbody tr:not(.empty-row)");
                    rows.forEach(row => {
                        const name = row.cells[1].innerText.toUpperCase();
                        const owner = row.cells[6].innerText.toUpperCase();
                        if (name.indexOf(input) > -1 || owner.indexOf(input) > -1) { row.style.display = ""; }
                        else { row.style.display = "none"; }
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
                        if (!isNaN(numA) && !isNaN(numB)) { return dir === 'asc' ? numA - numB : numB - numA; }
                        return dir === 'asc' ? valA.localeCompare(valB) : valB.localeCompare(valA);
                    });
                    rows.forEach(row => tbody.appendChild(row));
                }
    </script>
</body>
</html>