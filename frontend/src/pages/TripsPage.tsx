import { type FormEvent, useMemo, useState } from 'react'
import { savedRoutes, trips as initialTrips, type Trip } from '../data/demoData'
import './Page.css'
import './TablePage.css'

function getStatusClass(status: string) {
  if (status === 'Набор группы') return 'status--recruiting'
  if (status === 'Завершена') return 'status--completed'
  return 'status--planned'
}

function TripsPage() {
  const [trips, setTrips] = useState<Trip[]>(initialTrips)
  const [query, setQuery] = useState('')
  const [isFormOpen, setIsFormOpen] = useState(false)

  const filteredTrips = useMemo(() => {
    const normalizedQuery = query.trim().toLowerCase()
    if (!normalizedQuery) return trips

    return trips.filter((trip) =>
      `${trip.route} ${trip.group} ${trip.status}`.toLowerCase().includes(normalizedQuery),
    )
  }, [query, trips])

  function handleSubmit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault()
    const form = event.currentTarget
    const data = new FormData(form)

    const newTrip: Trip = {
      id: Date.now(),
      route: String(data.get('route')),
      group: String(data.get('group')),
      date: String(data.get('date')),
      people: Number(data.get('people')),
      status: 'Запланирована',
    }

    setTrips((currentTrips) => [newTrip, ...currentTrips])
    setIsFormOpen(false)
    form.reset()
  }

  return (
    <main className="page">
      <header className="page__header">
        <div><p className="page__eyebrow">Расписание</p><h1 className="page__title">Поездки</h1><p className="page__subtitle">Группы, даты отправления и выбранные маршруты.</p></div>
        <button className="page__action" type="button" onClick={() => setIsFormOpen((value) => !value)}>
          {isFormOpen ? 'Закрыть форму' : '+ Создать поездку'}
        </button>
      </header>

      {isFormOpen && (
        <form className="creation-form panel" onSubmit={handleSubmit}>
          <div><label htmlFor="trip-route">Маршрут</label><select id="trip-route" name="route" required>{savedRoutes.map((route) => <option key={route.id}>{route.name}</option>)}</select></div>
          <div><label htmlFor="trip-group">Номер группы</label><input id="trip-group" name="group" placeholder="Например, SPB-25" required /></div>
          <div><label htmlFor="trip-date">Дата и время</label><input id="trip-date" name="date" placeholder="25 октября, 08:00" required /></div>
          <div><label htmlFor="trip-people">Участники</label><input id="trip-people" name="people" type="number" min="1" defaultValue="10" required /></div>
          <button type="submit">Добавить поездку</button>
        </form>
      )}

      <section className="panel table-panel" aria-label="Список поездок">
        <div className="table-toolbar">
          <label><span className="visually-hidden">Поиск поездки</span><input type="search" placeholder="Найти поездку или группу" value={query} onChange={(event) => setQuery(event.target.value)} /></label>
          <span>{filteredTrips.length} из {trips.length}</span>
        </div>
        <div className="table-scroll">
          <table>
            <thead><tr><th>Маршрут</th><th>Группа</th><th>Дата</th><th>Участники</th><th>Статус</th></tr></thead>
            <tbody>
              {filteredTrips.map((trip) => (
                <tr key={trip.id}><td><strong>{trip.route}</strong></td><td>{trip.group}</td><td>{trip.date}</td><td>{trip.people}</td><td><span className={`status ${getStatusClass(trip.status)}`}>{trip.status}</span></td></tr>
              ))}
            </tbody>
          </table>
          {filteredTrips.length === 0 && <p className="empty-state">Поездки по вашему запросу не найдены.</p>}
        </div>
      </section>
    </main>
  )
}

export default TripsPage
