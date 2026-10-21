export type TripStatus = 'Запланирована' | 'Набор группы' | 'Завершена'
export type Trip = { id: number; route: string; group: string; date: string; people: number; status: TripStatus }
export type SavedRoute = { id: number; name: string; from: string; to: string; distance: number; owner: string; used: number }

export const trips: Trip[] = [
  { id: 1, route: 'Петербург — Карелия', group: 'SPB-24', date: '24 октября, 08:00', people: 18, status: 'Запланирована' },
  { id: 2, route: 'Москва — Коломна', group: 'MSK-17', date: '27 октября, 09:30', people: 12, status: 'Набор группы' },
  { id: 3, route: 'Псковские выходные', group: 'PSK-08', date: '2 ноября, 07:00', people: 21, status: 'Запланирована' },
  { id: 4, route: 'Золотое кольцо', group: 'ZK-31', date: '12 октября, 06:30', people: 26, status: 'Завершена' },
]

export const savedRoutes: SavedRoute[] = [
  { id: 1, name: 'Петербург — Карелия', from: 'Санкт-Петербург', to: 'Петрозаводск', distance: 428, owner: 'ivan-k-06', used: 7 },
  { id: 2, name: 'Москва — Коломна', from: 'Москва', to: 'Коломна', distance: 114, owner: 'ivan-k-06', used: 4 },
  { id: 3, name: 'Псковские выходные', from: 'Санкт-Петербург', to: 'Псков', distance: 294, owner: 'ivan-k-06', used: 3 },
  { id: 4, name: 'Золотое кольцо', from: 'Москва', to: 'Ярославль', distance: 271, owner: 'ivan-k-06', used: 9 },
]
