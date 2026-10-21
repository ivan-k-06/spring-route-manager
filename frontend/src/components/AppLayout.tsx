import { NavLink, Outlet } from 'react-router'
import './AppLayout.css'

const navigation = [
  { to: '/dashboard', label: 'Главная', icon: '⌂' },
  { to: '/trips', label: 'Поездки', icon: '↗' },
  { to: '/routes', label: 'Маршруты', icon: '◇' },
]

function AppLayout() {
  return (
    <div className="app-shell">
      <aside className="sidebar">
        <NavLink className="sidebar__logo" to="/dashboard" aria-label="Route Manager">RM<span>.</span></NavLink>
        <nav className="sidebar__nav" aria-label="Основная навигация">
          {navigation.map((item) => (
            <NavLink
              className={({ isActive }) => `sidebar__link${isActive ? ' sidebar__link--active' : ''}`}
              key={item.to}
              to={item.to}
            >
              <span aria-hidden="true">{item.icon}</span>
              {item.label}
            </NavLink>
          ))}
        </nav>
        <div className="sidebar__profile">
          <span className="sidebar__avatar" aria-hidden="true">ИК</span>
          <span><strong>Иван</strong><small>Организатор</small></span>
        </div>
      </aside>

      <div className="app-shell__content">
        <header className="topbar">
          <div><strong>Route Manager</strong><span>Демонстрационный режим</span></div>
          <NavLink className="topbar__exit" to="/">Выйти</NavLink>
        </header>
        <Outlet />
      </div>
    </div>
  )
}

export default AppLayout
