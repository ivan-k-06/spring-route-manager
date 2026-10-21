import './LandingPage.css'
import { Link } from 'react-router'

function LandingPage() {
  return (
    <main className="landing">
      <div className="landing__decoration landing__decoration--top" />
      <div className="landing__content">
        <span className="landing__eyebrow">Планирование без лишней рутины</span>
        <h1 className="landing__title">
          ROUTE <span>MANAGER</span>
        </h1>
        <p className="landing__description">
          Создавайте маршруты один раз, используйте их в новых поездках и держите
          расписание групп в одном месте.
        </p>
        <div className="landing__actions">
          <Link className="button button--primary" to="/dashboard">Открыть демо</Link>
          <a className="button button--secondary" href="#features">Возможности</a>
        </div>
      </div>

      <section className="landing__features" id="features" aria-label="Возможности сервиса">
        <article><span>01</span><h2>Маршруты</h2><p>Сохраняйте точки и расстояние, чтобы не вводить их заново.</p></article>
        <article><span>02</span><h2>Поездки</h2><p>Назначайте маршрут группе на конкретную дату отправления.</p></article>
        <article><span>03</span><h2>Сводка</h2><p>Следите за ближайшими поездками и основными показателями.</p></article>
      </section>
      <div className="landing__decoration landing__decoration--bottom" />
    </main>
  )
}

export default LandingPage
