# Kafka Integration – Architektur (Aktuelle Gesamtsicht)

## Gesamtarchitektur (vereinfacht)

```
┌────────────────────────────────────────────────────────────────────────────────────────────────┐
│                                SPRING BOOT ENTRYPOINT                                          │
│  Application.java ( @SpringBootApplication )                                                   │
│        │ startet Kontext, registriert Vaadin Routes                                            │
└────────┴───────────────────────────────────────────────────────────────────────────────────────┘
                                      │
                                      ▼
┌──────────────────────────────────────────────────────────────────────────────────────────────────┐
│                                       VAADIN UI LAYER                                            │
│                                                                                                  │
│  ┌───────────────────────────┬───────────────────────────┬──────────────────────────┬───────────┐│
│  │ FilmListeView             │ SitzplatzWahlView         │ ReservierungenView       │           ││
│  │ (FILM QUERY LIST_ALL)     │ (Aufführung/Kunde über    │ (Kunde & Reservierungen  │           ││
│  │                           │ Admin/Customer QUERY)     │ via QUERY)               │           ││
│  ├───────────────────────────┼───────────────────────────┼──────────────────────────┤ Buchungs  ││
│  │ AdminFilmAnlegenView      │ AdminSaalAnlegenView      │ Aufführungs-Dialog       │ View      ││
│  │ (Film CRUD via Commands)  │ (Saal CRUD via Commands)  │ (LIST_BY_FILM)           │ (Auff.    ││
│  │                           │                           │                          │ GET_BY_ID)││
│  └───────────────────────────┴───────────────────────────┴──────────────────────────┴───────────┘│
│           ▲                    ▲                     ▲                        ▲                  │
│           │ AdminEvents        │ Admin/Customer/      │ ReservationEvents      │ AdminEvents     │
│           │                    │ ReservationEvents    │                        │                 │
└───────────┼────────────────────┼──────────────────────┼────────────────────────┼─────────────────┘
            │                    │                      │                        │
            ▼                    ▼                      ▼                        ▼
┌──────────────────────────────────────────────────────────────────────────────────────────────────┐
│                                        UI EVENT BUSSES                                           │
│  AdminUIEventBus  | CustomerUIEventBus | ReservationUIEventBus                                   │
│  (filter by correlationId, remove temporary listeners)                                           │
└───────────┬──────────────────────────────────────────────────────────────────────────────────────┘
            │
            ▼ send()/publish (Commands)
┌──────────────────────────────────────────────────────────────────────────────────────────────────┐
│                                 PRODUCER / SERVICE FACADE                                        │
│  AdminService  → AdminCommandProducer (Film/Saal/Aufführung CRUD & Queries)                      │
│  ReservierungsService → ReservationCommandProducer (CREATE/DELETE/QUERY)                         │
│  BuchungsService → BookingCommandProducer (CREATE Booking)                                       │
│  (CustomerCommandProducer direkt in Views für Email→Kunde)                                       │
└───────────┬──────────────────────────────────────────────────────────────────────────────────────┘
            │ Kafka send() (JSON DTO Payloads)
            ▼
┌─────────────────────────────────────────────────────────────────────────────────────────────────┐
│                                           APACHE KAFKA                                          │
│  Topics:                                                                                        │
│    admin-commands        → AdminCommandConsumer                                                 │
│    admin-events          ← AdminEventProducer                                                   │
│    customer-commands     → CustomerCommandConsumer                                              │
│    customer-events       ← CustomerEventProducer                                                │
│    reservation-commands  → ReservationCommandConsumer                                           │
│    reservation-events    ← ReservationEventProducer                                             │
│    booking-commands      → BookingCommandConsumer                                               │
│    booking-events        ← BookingEventProducer                                                 │
│    aggregation           → AggregationCommandConsumer                                           │
│    aggregation-events    ← EventProducer.sendAggregationEvent                                   │
└───────────┬─────────────────────────────────────────────────────────────────────────────────────┘
            │ consume (@KafkaListener) + map Entities → DTOs / execute writes
            ▼
┌────────────────────────────────────────────────────────────────────────────────────────────────┐
│                                      CONSUMER LAYER                                            │
│  AdminCommandConsumer        (FILM / SAAL / AUFFÜHRUNG: CREATE, DELETE, QUERY)                 │
│  CustomerCommandConsumer     (Kunde CREATE / QUERY by Email)                                   │
│  ReservationCommandConsumer  (Reservierung CREATE / DELETE / QUERY by kundeId)                 │
│  BookingCommandConsumer      (Buchung CREATE)                                                  │
│  AggregationCommandConsumer  (Aggregation für Mongo, pro Tag/Aufführung)                       │
│  → Build AdminEvent / CustomerEvent / ReservationEvent / BookingEvent / AggregationResultEvent │
│  → Send event to Kafka & broadcast to matching UI EventBus                                     │
└───────────┬────────────────────────────────────────────────────────────────────────────────────┘
            │ JPA/Hibernate transactional writes & reads; Mongo Aggregation writes (Spring Data)
            ▼
┌────────────────────────────────────────────────────────────────────────────────────────────────┐
│                                         POSTGRESQL                                             │
│  Entities: Film, Kinosaal, Sitzreihe, Sitzplatz, Auffuehrung, Reservierung, Buchung, Kunde     │
│  Join Tables: reservierung_sitzplatz, buchung_sitzplatz                                        │
└────────────────────────────────────────────────────────────────────────────────────────────────┘
            ▼
┌────────────────────────────────────────────────────────────────────────────────────────────────┐
│                                           MONGODB                                              │
│  Collection: daily_revenue (RevenueAggregate je Aufführung und Tag)                            │
└────────────────────────────────────────────────────────────────────────────────────────────────┘
```

## Datenfluss: Reservierung erstellen

```
1. User Interaction
   ├─> Vaadin UI: Button Click
   │
2. Service Layer
   ├─> ReservierungsService.reservierePlaetze()
   ├─> Lade Sitzplätze aus DB
   ├─> Berechne Preise (PARKETT: 12€, LOGE: 18€, LOGE_MIT_SERVICE: 25€)
   ├─> Erstelle ReservationCommand
   │
3. Producer
   ├─> ReservationCommandProducer.sendReservation()
   ├─> Serialisiere Command zu JSON
   ├─> Sende an Topic: "reservation-commands"
   │
4. Kafka
   ├─> Speichere Message im Topic
   ├─> Partition nach auffuehrungId
   │
5. Consumer
   ├─> ReservationCommandConsumer.handleReservationCommand()
   ├─> Deserialisiere JSON zu ReservationCommand
   ├─> @Transactional Start
   │   ├─> Lade Aufführung
   │   ├─> Lade/Erstelle Kunde
   │   ├─> Erstelle Reservierung
   │   ├─> Erstelle ReservierungSitzplatz (Join-Entities)
   │   ├─> Markiere Sitzplätze als reserviert
   │   ├─> Commit Transaction
   │
6. Event Publishing
   ├─> EventProducer.sendReservationEvent()
   ├─> Sende ReservationEvent an "reservation-events"
   │
7. Weitere Services (optional)
   └─> Konsumiere reservation-events
       ├─> Email-Service
       ├─> Analytics-Service
       └─> Notification-Service
```

## Datenfluss: Buchung erstellen

```
1. User Interaction
   ├─> Vaadin UI: Button Click
   │
2. Service Layer
   ├─> BuchungsService.buchePlaetze()
   ├─> Lade Kunde aus DB
   ├─> Lade Sitzplätze aus DB
   ├─> Berechne Gesamtpreis
   ├─> Erstelle BookingCommand
   │
3. Producer
   ├─> BookingCommandProducer.sendBooking()
   ├─> Serialisiere Command zu JSON
   ├─> Sende an Topic: "booking-commands"
   │
4. Kafka
   ├─> Speichere Message im Topic
   ├─> Partition nach auffuehrungId
   │
5. Consumer
   ├─> BookingCommandConsumer.handleBookingCommand()
   ├─> Deserialisiere JSON zu BookingCommand
   ├─> @Transactional Start
   │   ├─> Lade Aufführung
   │   ├─> Lade Kunde
   │   ├─> Erstelle Buchung
   │   ├─> Erstelle BuchungSitzplatz (Join-Entities)
   │   ├─> Entferne alte Reservierungen
   │   ├─> Markiere Sitzplätze als gebucht
   │   ├─> Aktualisiere Einnahmen der Aufführung
   │   ├─> Commit Transaction
   │
6. Event Publishing
   ├─> EventProducer.sendBookingEvent()
   ├─> Sende BookingEvent an "booking-events"
   │
7. Weitere Services (optional)
   └─> Konsumiere booking-events
       ├─> Payment-Service
       ├─> Invoice-Service
       ├─> Analytics-Service
       └─> Notification-Service
```

## Event-Typen im System

```
Commands (Input Events):
┌─────────────────────────┐
│  ReservationCommand     │
├─────────────────────────┤
│ - auffuehrungId         │
│ - kundeId (optional)    │
│ - kundeName             │
│ - sitzplaetze[]         │
│   └─> SitzplatzInfo     │
│       ├─ sitzplatzId    │
│       ├─ reiheNummer    │
│       ├─ platzNummer    │
│       └─ preis          │
└─────────────────────────┘

┌─────────────────────────┐
│   BookingCommand        │
├─────────────────────────┤
│ - auffuehrungId         │
│ - kundeId               │
│ - kundeName             │
│ - sitzplaetze[]         │
│ - gesamtpreis           │
└─────────────────────────┘

Events (Output Events):
┌─────────────────────────┐
│  ReservationEvent       │
├─────────────────────────┤
│ - reservierungId        │
│ - reservierungsnummer   │
│ - auffuehrungId         │
│ - kundeId               │
│ - timestamp             │
│ - status (CREATED)      │
└─────────────────────────┘

┌─────────────────────────┐
│ AggregationResultEvent  │
├─────────────────────────┤
│ - day                   │
│ - correlationId         │
│ - operation (INSERT/DELETE)
│ - status (SUCCESS/FAILURE)
│ - count                 │
│ - message               │
│ - timestamp             │
└─────────────────────────┘

┌─────────────────────────┐
│    BookingEvent         │
├─────────────────────────┤
│ - buchungId             │
│ - buchungsnummer        │
│ - auffuehrungId         │
│ - kundeId               │
│ - gesamtpreis           │
│ - timestamp             │
│ - status (COMPLETED)    │
└─────────────────────────┘
```

## Consumer Groups

```
reservation-commands Topic
    └─> kino-reservation-worker (Group)
        └─> ReservationCommandConsumer
            ├─> Partition 0
            ├─> Partition 1
            └─> Partition 2

booking-commands Topic
    └─> kino-booking-worker (Group)
        └─> BookingCommandConsumer
            ├─> Partition 0
            ├─> Partition 1
            └─> Partition 2

reservation-events Topic
    └─> (Für zukünftige Services)
        ├─> email-service
        ├─> analytics-service
        └─> notification-service

booking-events Topic
    └─> (Für zukünftige Services)
        ├─> payment-service
        ├─> invoice-service
        └─> analytics-service
```

## Fehlerbehandlung & Retry

```
┌─────────────────────────────────────────────────────┐
│              Consumer Error Handling                 │
├─────────────────────────────────────────────────────┤
│                                                      │
│  1. Message empfangen                               │
│      ↓                                              │
│  2. Deserialisierung                                │
│      ├─ Success → weiter                           │
│      └─ Error → Log + Skip                         │
│                                                      │
│  3. Business Logic (@Transactional)                 │
│      ├─ Success → Commit                           │
│      └─ Error → Rollback                           │
│          ├─> Log Error                             │
│          ├─> Kafka retries automatisch             │
│          └─> Optional: Dead Letter Queue           │
│                                                      │
│  4. Event Publishing                                │
│      └─> Nur bei erfolgreicher Verarbeitung       │
│                                                      │
└─────────────────────────────────────────────────────┘
```

## Vorteile der Architektur

```
✅ Loose Coupling
   └─> UI kennt nur Services, nicht DB

✅ Scalability
   └─> Consumer können unabhängig skaliert werden

✅ Reliability
   └─> Kafka garantiert Message Delivery
   └─> At-least-once Semantics

✅ Auditability
   └─> Alle Events in Kafka gespeichert
   └─> Vollständige Historie

✅ Extensibility
   └─> Neue Consumer einfach hinzufügen
   └─> Ohne bestehende Services zu ändern

✅ Fault Tolerance
   └─> Transaktionale Verarbeitung
   └─> Rollback bei Fehlern

✅ Performance
   └─> Asynchrone Verarbeitung
   └─> Non-blocking UI
```

## Monitoring Points

```
1. Producer Metrics
   ├─> Messages sent rate
   ├─> Send errors
   └─> Latency

2. Kafka Metrics
   ├─> Topic size
   ├─> Partition lag
   ├─> Message throughput
   └─> Replication status

3. Consumer Metrics
   ├─> Processing rate
   ├─> Consumer lag
   ├─> Error rate
   └─> Processing time

4. Database Metrics
   ├─> Transaction duration
   ├─> Connection pool
   └─> Query performance
```

## Deployment

```
┌────────────────────────────────────────────┐
│           Docker Compose Setup             │
├────────────────────────────────────────────┤
│                                            │
│  ┌──────────────────┐                     │
│  │   Zookeeper      │                     │
│  │   Port: 2181     │                     │
│  └────────┬─────────┘                     │
│           │                                │
│  ┌────────▼─────────┐                     │
│  │   Kafka          │                     │
│  │   Port: 9092     │                     │
│  └──────────────────┘                     │
│                                            │
│  ┌──────────────────┐                     │
│  │   PostgreSQL     │                     │
│  │   Port: 5432     │                     │
│  └──────────────────┘                     │
│                                            │
│  ┌──────────────────┐                     │
│  │    MongoDB       │                     │
│  │   Port: 27017    │                     │
│  └──────────────────┘                     │
│                                            │
│  ┌──────────────────┐                     │
│  │  Spring Boot App │                     │
│  │   Port: 8090     │                     │
│  └──────────────────┘                     │
│                                            │
└────────────────────────────────────────────┘

## Aggregation (MongoDB) – Architektur & Datenfluss

Die tagesbezogene Einnahmen‑Aggregation wird in MongoDB gespeichert und über Kafka orchestriert. UI und Services sind vollständig entkoppelt.

```
UI (EinnahmenView)
   ├─ Klick "Jetzt aggregieren" → AggregationCommand(day, correlationId)
   ├─ send via AggregationCommandProducer → Topic: aggregation
   ├─ registriert Listener auf AggregationUIEventBus (filter corrId & day)
   └─ konsumiert AggregationResultEvent (INSERT) → Grid refresh

AggregationCommandConsumer (@KafkaListener topic=aggregation)
   └─ ruft AggregationService.aggregateDay(day, correlationId)

AggregationService
   ├─ löscht vorhandene Aggregate für day (Mongo) → send AggregationResultEvent(DELETE)
   ├─ lädt Buchungen (Postgres) im Tagesfenster
   ├─ gruppiert nach Aufführung
   ├─ berechnet pro Aufführung:
   │     • revenue (Summe gesamtpreis)
   │     • bookingsCount (Anzahl Buchungen)
   │     • occupiedSeatsCount (Summe BuchungSitzplätze)
   │     • totalSeatsCount (Summe aller Sitzplätze im Saal)
   │     • occupancyPercent = occupied/total*100
   ├─ speichert RevenueAggregate in Mongo (collection "daily_revenue")
   └─ send AggregationResultEvent(INSERT, count, correlationId)

AggregationResultConsumer (@KafkaListener topic=aggregation-events)
   └─ broadcastet AggregationResultEvent → AggregationUIEventBus

MongoDB (collection: daily_revenue)
   └─ RevenueAggregate(day, aggregatedAt, filmId, auffuehrungId, revenue,
                       bookingsCount, occupiedSeatsCount, totalSeatsCount, occupancyPercent)
```

Wichtige Schnittstellen:
- Command: `AggregationCommand { day: LocalDate, correlationId?: String }`
- Event: `AggregationResultEvent { day, correlationId, operation, status, count, message, timestamp }`

UI‑Integration:
- `EinnahmenView` triggert Aggregation via `AggregationCommandProducer` und hört auf `AggregationUIEventBus` (Ergebnisse aus `aggregation-events`).
- Anzeige im UI verwendet die letzten Mongo‑Aggregate (Umsatz, Belegung) pro Aufführung; Anzahl Buchungen kann ergänzend live aus Postgres gelesen werden.

Konsistenz:
- Aggregation arbeitet pro Aufführung; Belegungen werden nicht über mehrere Aufführungen eines Saals summiert.
- Vor Einfügen neuer Tagesdaten werden bestehende Aggregate desselben Tages gelöscht (Replace‑Semantik).
- `correlationId` wird durchgereicht (Command → ResultEvent), um UI‑Listener präzise zu matchen.

## Architektur‑Konsistenzprüfung (Stand Implementierung)

- Topics & Consumer/Producer stimmen mit Code überein:
  - `aggregation` (AggregationCommandProducer → AggregationCommandConsumer)
  - `aggregation-events` (EventProducer.sendAggregationEvent → AggregationResultConsumer)
  - Admin/Customer/Reservation/Booking Topics unverändert vorhanden.
- UI‑EventBus‑Bridges vorhanden: `AggregationUIEventBus`, `AdminUIEventBus`, `CustomerUIEventBus`, `ReservationUIEventBus`.
- `CustomerEvent` Korrektur: `correlationId` wird im CREATE‑Pfad jetzt gesetzt (fix in `CustomerCommandConsumer.handleCreate`). UI‑Listener in `SitzplatzWahlView` filtern korrekt über `correlationId`.
- Sitzplatz‑Belegung in `SitzplatzWahlView` prüft nur Reservierungen/Buchungen der aktuellen Aufführung (kein globales `isFrei()` mehr) → keine fälschliche Akkumulation über Aufführungen hinweg.
- Einnahmen‑UI (`EinnahmenView`) zeigt:
  - Gesamteinnahmen aus Mongo je Film
  - Pro Aufführung: letzte Aggregation (Belegung, Umsatz) + Anzahl Buchungen (Postgres) und „Details anzeigen“.
- Event‑Schemas: `AggregationResultEvent` mit Operation/Status/Count/Timestamp ist implementiert und dokumentiert.

Offene/optionale Punkte:
- Scheduler für tägliche Aggregation (z. B. 02:00 Uhr) per `@Scheduled` in einem separaten Service nutzen, der `AggregationCommand` publiziert.
- Optionales Topic für Sitzplatz‑Queries, falls UI‑Reads konsolidiert werden sollen (derzeit lokale Repository‑Reads ok).
```

## Administration: Filme, Säle, Aufführungen (nach Migration über Kafka)

Die komplette Admin-Verwaltung (Filme, Säle, Aufführungen) arbeitet jetzt event‑getrieben über Kafka Commands & Events:

| Bereich | Command | Consumer Aktion | Event (AdminEvent) | UI-Reaktion |
|---------|---------|-----------------|--------------------|-------------|
| Film CREATE/UPDATE | `AdminCommand(FILM, CREATE)` | Persistiert Film (JPA) | Status `SUCCESS`, `filmId` | Grid reload via QUERY Command |
| Film DELETE | `AdminCommand(FILM, DELETE)` | Löscht Film | Status `SUCCESS`, `filmId` | Entfernt selektierten Film, lädt Liste neu |
| Film LIST_ALL | `AdminCommand(FILM, QUERY)` `QueryPayload.type=LIST_ALL` | Lädt alle Filme, mappt zu DTO | Status `OK`, `films[]` | Grid befüllt |
| Film GET_BY_ID | `QUERY` `GET_BY_ID` | Einzelner Film→DTO | Status `OK/NOT_FOUND` | Detail / Auswahl |
| Saal CREATE | `AdminCommand(SAAL, CREATE)` | Persistiert Saal + Reihen + Sitzplätze | Status `SUCCESS`, `saalId` | Optionales Refresh |
| Saal LIST_ALL | `QUERY` `LIST_ALL` | Alle Säle→DTO | Status `OK` | Dropdown/Planung |
| Aufführung CREATE | `AdminCommand(AUFFUEHRUNG, CREATE)` | Persistiert Aufführung | Status `SUCCESS`, `auffuehrungId` | Dialog schließt, Requery |
| Aufführung DELETE | `AdminCommand(AUFFUEHRUNG, DELETE)` | Löscht Aufführung | Status `SUCCESS` | Kalender aktualisiert |
| Aufführung LIST_BY_FILM | `QUERY` `LIST_BY_FILM` | Alle Aufführungen eines Films → DTOs | Status `OK` | Wochen-/Kalenderansicht |
| Aufführung GET_BY_ID | `QUERY` `GET_BY_ID` | Einzelne Aufführung → DTO | Status `OK/NOT_FOUND` | Buchungs-/Reservierungs-Kontext |

### Technische Änderungen gegenüber vorher:

- Alle früheren direkten Repository-Aufrufe in den Admin-Views für Lesen wurden durch Kafka QUERY Commands ersetzt (Grid, Dialoge, Kalender).
- Write-Operationen (Anlegen/Löschen) gehen ebenfalls über `AdminService`, der `AdminCommandProducer` nutzt (keine direkte Repository-Verwendung mehr im UI Layer).
- Antwort‑Aggregation erfolgt über `AdminUIEventBus` mit `correlationId`. Temporäre Listener filtern passende Events und entfernen sich anschließend.
- DTO-Schicht (`FilmDTO`, `SaalDTO`, `AuffuehrungDTO`) verhindert zyklische Serialisierung und reduziert Payload.
- Status-Codes vereinheitlicht: `SUCCESS` (Write), `OK` (Query erfolgreich), `NOT_FOUND`, `FAILURE`, `ERROR`.
- Pagination (offset/limit) wieder entfernt: `LIST_ALL` liefert komplette Film-Liste; „Mehr laden“ Button entfällt.

### Ablauf Film-Liste (neu über Kafka)
```
FilmListeView
   ├─ Erzeugt correlationId
   ├─ send AdminCommand(FILM, QUERY, LIST_ALL)
   ├─ wartet auf AdminEvent(Status=OK, films[])
   ├─ rendert komplette Liste
   └─ kein Nachladen / Pagination mehr
```

### Ablauf Löschung Film
```
AdminFilmAnlegenView.deleteFilm()
   ├─ send AdminCommand(FILM, DELETE)
   ├─ Consumer löscht Film (JPA) -> AdminEvent SUCCESS
   ├─ UI Listener erkennt DELETE -> clearForm() + updateGrid()
   ├─ updateGrid() sendet LIST_ALL QUERY
   ├─ AdminEvent OK mit neuer films[] Liste
   └─ Grid aktualisiert ohne gelöschten Film
```

### Künftige / verbleibende direkte Zugriffe

Aktuell werden Sitzplätze (Detail-Ladung einzelner Plätze für Sitzplan / Buchung) noch direkt über Repositories geladen (kein eigenes Seat-Query-Topic). Dies kann optional durch Einführung eines weiteren `SeatCommand`/`SeatEvent` Musters ersetzt werden, falls:

- Skalierung des Sitzplan-Ladens nötig wird
- Sitzplatzdaten von anderen Services (Pricing, Availability Analytics) angereichert werden sollen

Bis dahin besteht kein Konsistenzproblem, da Sitzplatz-Lesezugriffe lokal und kurzlebig sind.

### Vorteile der vollständigen Migration (Admin Bereich)

- Einheitlicher Kommunikationskanal (Kafka) für Lesen & Schreiben
- UI komplett entkoppelt von JPA – leichter Test/Stubbing
- Erweiterbar für Event-Sourcing / Audit (AdminEvents persistierbar)
- Reduzierte Komplexität im UI (nur Producer + EventBus statt mehrere Repositories)

### Nächste mögliche Schritte

1. Sitzplatz-/Reihen-Query über Kafka vereinheitlichen (SeatQueryCommand).
2. Einführung eines `Version` Feldes in Events für evolutionäre Schema-Änderungen.
3. Optionales Caching vor Consumer für häufige LIST_ALL Abfragen (Filme, Säle).
4. Metriken für Query Latenz (`correlationId` Zeit messen) senden.
