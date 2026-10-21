import { type FormEvent, useMemo, useState } from 'react'
import { savedRoutes as initialRoutes, type SavedRoute } from '../data/demoData'
import './Page.css'
import './TablePage.css'

function RoutesPage() {
  const [routes, setRoutes] = useState<SavedRoute[]>(initialRoutes)
  const [query, setQuery] = useState('')
  const [isFormOpen, setIsFormOpen] = useState(false)

  const filteredRoutes = useMemo(() => {
    const normalizedQuery = query.trim().toLowerCase()
    if (!normalizedQuery) return routes

    return routes.filter((route) =>
      `${route.name} ${route.from} ${route.to} ${route.owner}`.toLowerCase().includes(normalizedQuery),
    )
  }, [query, routes])

  function handleSubmit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault()
    const form = event.currentTarget
    const data = new FormData(form)

    const newRoute: SavedRoute = {
      id: Math.max(0, ...routes.map((route) => route.id)) + 1,
      name: String(data.get('name')),
      from: String(data.get('from')),
      to: String(data.get('to')),
      distance: Number(data.get('distance')),
      owner: 'ivan-k-06',
      used: 0,
    }

    setRoutes((currentRoutes) => [newRoute, ...currentRoutes])
    setIsFormOpen(false)
    form.reset()
  }

  return (
    <main className="page">
      <header className="page__header">
        <div><p className="page__eyebrow">Библиотека</p><h1 className="page__title">Маршруты</h1><p className="page__subtitle">Готовые направления для повторного использования в поездках.</p></div>
        <button className="page__action" type="button" onClick={() => setIsFormOpen((value) => !value)}>
          {isFormOpen ? 'Закрыть форму' : '+ Новый маршрут'}
        </button>
      </header>

      {isFormOpen && (
        <form className="creation-form panel" onSubmit={handleSubmit}>
          <div><label htmlFor="route-name">Название</label><input id="route-name" name="name" placeholder="Выходные в Выборге" required /></div>
          <div><label htmlFor="route-from">Откуда</label><input id="route-from" name="from" placeholder="Санкт-Петербург" required /></div>
          <div><label htmlFor="route-to">Куда</label><input id="route-to" name="to" placeholder="Выборг" required /></div>
          <div><label htmlFor="route-distance">Дистанция, км</label><input id="route-distance" name="distance" type="number" min="1" required /></div>
          <button type="submit">Сохранить маршрут</button>
        </form>
      )}

      <section className="panel table-panel" aria-label="Список маршрутов">
        <div className="table-toolbar">
          <label><span className="visually-hidden">Поиск маршрута</span><input type="search" placeholder="Найти маршрут или город" value={query} onChange={(event) => setQuery(event.target.value)} /></label>
          <span>{filteredRoutes.length} из {routes.length}</span>
        </div>
        <div className="table-scroll">
          <table>
            <thead><tr><th>№</th><th>Название</th><th>Откуда</th><th>Куда</th><th>Дистанция</th><th>Использован</th><th>Владелец</th></tr></thead>
            <tbody>
              {filteredRoutes.map((route) => (
                <tr key={route.id}><td className="table-id">#{route.id}</td><td><strong>{route.name}</strong></td><td>{route.from}</td><td>{route.to}</td><td>{route.distance} км</td><td>{route.used} раз</td><td>{route.owner}</td></tr>
              ))}
            </tbody>
          </table>
          {filteredRoutes.length === 0 && <p className="empty-state">Маршруты по вашему запросу не найдены.</p>}
        </div>
      </section>
    </main>
  )
}

export default RoutesPage
