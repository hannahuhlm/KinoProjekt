# Kafka Integration - Architektur-Diagramm

## Gesamtarchitektur

```
┌─────────────────────────────────────────────────────────────────────────┐
│                          VAADIN UI LAYER                                 │
│                                                                           │
│  ┌──────────────────┐        ┌──────────────────┐                       │
│  │  KafkaTestView   │        │ SitzplatzWahlView│                       │
│  │                  │        │   (zukünftig)    │                       │
│  └────────┬─────────┘        └────────┬─────────┘                       │
└───────────┼──────────────────────────┼─────────────────────────────────┘
            │                          │
            │ HTTP/Vaadin              │
            ▼                          ▼
┌─────────────────────────────────────────────────────────────────────────┐
│                      SPRING BOOT BACKEND                                 │
│                                                                           │
│  ┌──────────────────────────────────────────────────────────────────┐   │
│  │                       SERVICE LAYER                              │   │
│  │                                                                  │   │
│  │  ┌──────────────────────┐      ┌──────────────────────┐        │   │
│  │  │ ReservierungsService │      │   BuchungsService    │        │   │
│  │  │                      │      │                      │        │   │
│  │  │ - reservierePlaetze()│      │ - buchePlaetze()    │        │   │
│  │  │ - Preis berechnen    │      │ - Preis berechnen   │        │   │
│  │  └──────────┬───────────┘      └──────────┬──────────┘        │   │
│  └─────────────┼──────────────────────────────┼───────────────────┘   │
│                │                              │                         │
│                │                              │                         │
│  ┌─────────────▼──────────────────────────────▼───────────────────┐   │
│  │                    KAFKA PRODUCER LAYER                         │   │
│  │                                                                  │   │
│  │  ┌─────────────────────┐  ┌─────────────────────┐             │   │
│  │  │  ReservationCommand │  │   BookingCommand    │             │   │
│  │  │     Producer        │  │      Producer       │             │   │
│  │  └──────────┬──────────┘  └──────────┬─────────┘              │   │
│  └─────────────┼────────────────────────┼────────────────────────┘   │
│                │                        │                             │
└────────────────┼────────────────────────┼─────────────────────────────┘
                 │                        │
                 │ send()                 │ send()
                 ▼                        ▼
┌─────────────────────────────────────────────────────────────────────────┐
│                          APACHE KAFKA                                    │
│                                                                           │
│  ┌───────────────────┐  ┌───────────────────┐  ┌──────────────────┐   │
│  │ reservation-      │  │ booking-          │  │ reservation-     │   │
│  │ commands          │  │ commands          │  │ events           │   │
│  │ (Topic)           │  │ (Topic)           │  │ (Topic)          │   │
│  └─────────┬─────────┘  └─────────┬─────────┘  └─────────▲────────┘   │
│            │                      │                       │             │
│            │                      │            ┌──────────┴────────┐   │
│            │                      │            │ booking-          │   │
│            │                      │            │ events            │   │
│            │                      │            │ (Topic)           │   │
│            │                      │            └──────────▲────────┘   │
└────────────┼──────────────────────┼────────────────────────┼───────────┘
             │                      │                        │
             │ consume              │ consume                │ produce
             ▼                      ▼                        │
┌─────────────────────────────────────────────────────────────────────────┐
│                      KAFKA CONSUMER LAYER                                │
│                                                                           │
│  ┌─────────────────────┐        ┌─────────────────────┐                │
│  │  ReservationCommand │        │   BookingCommand    │                │
│  │      Consumer       │        │      Consumer       │                │
│  │                     │        │                     │                │
│  │ @KafkaListener      │        │ @KafkaListener      │                │
│  │ @Transactional      │        │ @Transactional      │                │
│  └──────────┬──────────┘        └──────────┬─────────┘                 │
│             │                              │                            │
│             │                              │                            │
│             │                              │                            │
│  ┌──────────▼──────────────────────────────▼────────────────┐          │
│  │              EVENT PRODUCER                               │          │
│  │                                                            │          │
│  │  Sendet Events zurück an Kafka nach erfolgreicher         │          │
│  │  Speicherung (ReservationEvent, BookingEvent)            │          │
│  └──────────────────────────────┬─────────────────────────────┘         │
└─────────────────────────────────┼───────────────────────────────────────┘
                                  │
                                  │ save()
                                  ▼
┌─────────────────────────────────────────────────────────────────────────┐
│                          POSTGRESQL                                      │
│                                                                           │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐                  │
│  │ reservierung │  │   buchung    │  │    kunde     │                  │
│  └──────┬───────┘  └──────┬───────┘  └──────┬───────┘                  │
│         │                 │                  │                           │
│  ┌──────▼──────────┐  ┌───▼──────────────┐  │                          │
│  │ reservierung_   │  │ buchung_         │  │                          │
│  │ sitzplatz       │  │ sitzplatz        │  │                          │
│  └─────────────────┘  └──────────────────┘  │                          │
│                                              │                           │
│  ┌──────────────┐  ┌──────────────┐  ┌──────▼───────┐                  │
│  │  sitzplatz   │  │  sitzreihe   │  │ auffuehrung  │                  │
│  └──────────────┘  └──────────────┘  └──────────────┘                  │
│                                                                           │
└─────────────────────────────────────────────────────────────────────────┘
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
│  │  Spring Boot App │                     │
│  │   Port: 8090     │                     │
│  └──────────────────┘                     │
│                                            │
└────────────────────────────────────────────┘
```

## Administration: Filme, Säle, Aufführungen

Aktuell erfolgt die Administration direkt über Vaadin-Views im selben Spring Boot Backend. Die Datenpersistenz läuft über JPA/Hibernate nach PostgreSQL. Kafka wird in diesen Admin-Flows nicht verwendet (synchrone DB-Schreibvorgänge).

- Filme einpflegen: `AdminFilmAnlegenView`
  - Ansicht mit Grid der vorhandenen Filme und Formular zum Anlegen/Bearbeiten/Löschen.
  - Felder: Titel, Dauer, Filmstart (DatePicker), Poster-URL, Beschreibung.
  - Speichern/Löschen über `FilmRepository` (JPA), sofortige Aktualisierung des Grids.
  - Aufführungen planen pro Film: Dialog „Aufführungen planen“ mit Wochen-Übersicht (`TreeGrid`).
    - Lädt Aufführungen via `AuffuehrungRepository.findByFilmOrderByStartzeitpunktAsc(film)`.
    - Neue Aufführung: Datum (DatePicker), Uhrzeit (TextField HH:mm), Saal (ComboBox, nur freigegebene Säle).
    - Saalbelegungsprüfung: Zeitüberschneidung mit vorhandenen Aufführungen im Saal wird verhindert.
    - Speichern über `AuffuehrungRepository.save(...)` und anschließendes Reload des Dialogs.
    - Löschen: Entfernt Aufführung aus der Film-Liste; per `orphanRemoval` wird DB-Eintrag gelöscht.

- Saal anlegen/verwalten: `AdminSaalAnlegenView`
  - Grid mit Sälen (Name, Anzahl Reihen, Freigegeben, Aktionen).
  - „Neuen Saal anlegen“/„Saal bearbeiten“ öffnet Dialog mit Basisdaten und dynamischer Reihen-Verwaltung.
  - Reihensteuerung: Anzahl Reihen (IntegerField) passt die Liste `Sitzreihe` an; pro Reihe Kategorie (`SitzreihenKategorie`) und Anzahl Sitze.
  - Default-Kategorie: Falls nicht gewählt, wird beim Speichern PARKETT gesetzt.
  - Sitzplätze-Synchronisierung: Erzeugt/entfernt `Sitzplatz`-Entitäten passend zur Anzahl und nummeriert durch.
  - Speichern über `KinosaalRepository.save(...)` inkl. abhängiger Entitäten.

- Aufführungen planen
  - Aktuell über den Dialog in `AdminFilmAnlegenView` integriert (kein separater Route erforderlich).
  - Optional könnte ein dedizierter View `AdminAuffuehrungPlanenView` existieren, der Film, Saal und Startzeitpunkt auswählt und `AuffuehrungRepository` speichert. In der aktuellen Codebasis ist die Planung im Film-View gelöst.

### Admin-Datenfluss (synchron, ohne Kafka)

```
1. User Interaction (Admin)
   ├─> Vaadin View (Formulare/Dialogs)
   │
2. Validierung + UI-Logik
   ├─> Pflichtfelder prüfen
   ├─> Saalbelegungs-Konflikte vermeiden
   │
3. Persistenz
   ├─> JPA Repository (FilmRepository/KinosaalRepository/AuffuehrungRepository)
   ├─> save()/delete() – sofortige DB-Schreibvorgänge
   │
4. Feedback
   ├─> Grid/TreeGrid aktualisieren
   ├─> Notifications
```
