import { Link } from 'react-router'
import { savedRoutes, trips } from '../data/demoData'
import './Page.css'
import './DashboardPage.css'

function getStatusClass(status: string) {
  if (status === 'Набор группы') return 'status--recruiting'
  if (status === 'Завершена') return 'status--completed'
  return 'status--planned'
}

function DashboardPage() {
  const upcomingTrips = trips.filter((trip) => trip.status !== 'Завершена').slice(0, 3)
  const plannedPeople = upcomingTrips.reduce((total, trip) => total + trip.people, 0)

  return (
    <main className="page">
      <header className="page__header">
        <div>
          <p className="page__eyebrow">22 октября 2026</p>
          <h1 className="page__title">Добрый день, Иван</h1>
          <p className="page__subtitle">Краткая сводка по маршрутам и ближайшим поездкам.</p>
        </div>
        <Link className="page__action" to="/trips">+ Новая поездка</Link>
      </header>

      <section className="stats" aria-label="Статистика">
        <article className="stat-card stat-card--accent"><span>Ближайшая поездка</span><strong>24 окт.</strong><small>Петербург — Карелия</small></article>
        <article className="stat-card"><span>Активные поездки</span><strong>{upcomingTrips.length}</strong><small>в ближайшие две недели</small></article>
        <article className="stat-card"><span>Участники</span><strong>{plannedPeople}</strong><small>в запланированных группах</small></article>
        <article className="stat-card"><span>Сохранённые маршруты</span><strong>{savedRoutes.length}</strong><small>доступны для повторения</small></article>
      </section>

      <section className="dashboard-grid">
        <div className="panel">
          <div className="panel__header"><h2>Ближайшие поездки</h2><Link to="/trips">Все поездки</Link></div>
          <ul className="data-list">
            {upcomingTrips.map((trip) => (
              <li className="data-list__item dashboard-trip" key={trip.id}>
                <time>{trip.date.split(',')[0]}</time>
                <div className="data-list__primary"><strong>{trip.route}</strong><span>{trip.group} · {trip.people} человек</span></div>
                <span className={`status ${getStatusClass(trip.status)}`}>{trip.status}</span>
              </li>
            ))}
          </ul>
        </div>

        <aside className="panel dashboard-note">
          <span>Совет</span>
          <h2>Переиспользуйте маршруты</h2>
          <p>Создавая поездку, выберите готовый маршрут — точки и расстояние подставятся автоматически.</p>
          <Link to="/routes">Открыть маршруты →</Link>
        </aside>
      </section>
    </main>
  )
}

export default DashboardPage
