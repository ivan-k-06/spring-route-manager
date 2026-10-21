import { Link } from 'react-router'
import './NotFoundPage.css'

function NotFoundPage() {
  return (
    <main className="not-found">
      <span>404</span><h1>Такой страницы нет</h1><p>Возможно, адрес изменился или был введён с ошибкой.</p>
      <Link to="/">Вернуться на главную</Link>
    </main>
  )
}

export default NotFoundPage
