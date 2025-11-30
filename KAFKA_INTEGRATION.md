# Kafka Integration - Vollständige Implementierung

## Übersicht

Dieses Projekt implementiert eine vollständige Event-Driven-Architektur mit Kafka nach folgendem Kommunikationsfluss:

```
Vaadin-UI → Spring-Boot-Backend → Kafka → Listener-Services → PostgreSQL
```

## Architektur

### 1. Event-Klassen (`kino.application.kafka.events`)

- **ReservationCommand**: Command zum Erstellen einer Reservierung
- **BookingCommand**: Command zum Erstellen einer Buchung
- **ReservationEvent**: Event über erfolgreiche Reservierung
- **BookingEvent**: Event über erfolgreiche Buchung
- **SitzplatzInfo**: Hilfsklasse für Sitzplatz-Informationen

### 2. Producer (`kino.application.kafka.producer`)

- **ReservationCommandProducer**: Sendet Reservierungs-Commands an Kafka
- **BookingCommandProducer**: Sendet Buchungs-Commands an Kafka
- **EventProducer**: Sendet Events (Benachrichtigungen) an Kafka

### 3. Consumer (`kino.application.kafka.consumer`)

- **ReservationCommandConsumer**: Empfängt Reservierungs-Commands und speichert in PostgreSQL
- **BookingCommandConsumer**: Empfängt Buchungs-Commands und speichert in PostgreSQL

### 4. Services (`kino.application.service`)

- **ReservierungsService**: Fachlogik für Reservierungen
- **BuchungsService**: Fachlogik für Buchungen

### 5. Konfiguration (`kino.application.kafka`)

- **KafkaConfig**: Spring-Konfiguration für KafkaTemplates

## Kafka Topics

Die folgenden Topics werden verwendet:

- `reservation-commands`: Commands für neue Reservierungen
- `booking-commands`: Commands für neue Buchungen
- `reservation-events`: Events über erfolgreiche Reservierungen
- `booking-events`: Events über erfolgreiche Buchungen

## Installation & Setup

### 1. Kafka starten

Mit Docker Compose (im Projektverzeichnis):

```bash
docker-compose up -d
```

Dies startet:
- Kafka auf Port 9092
- PostgreSQL auf Port 5432

### 2. Topics erstellen (optional)

Die Topics werden automatisch erstellt. Bei Bedarf manuell:

```bash
docker exec -it <kafka-container-name> kafka-topics --create --topic reservation-commands --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1
docker exec -it <kafka-container-name> kafka-topics --create --topic booking-commands --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1
docker exec -it <kafka-container-name> kafka-topics --create --topic reservation-events --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1
docker exec -it <kafka-container-name> kafka-topics --create --topic booking-events --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1
```

### 3. Anwendung starten

```bash
mvn clean install
mvn spring-boot:run
```

### 4. UI aufrufen

Öffne im Browser:
- Hauptanwendung: http://localhost:8090
- Kafka Test-View: http://localhost:8090/kafka-test

## Verwendung

### Test über die UI

1. Navigiere zu `/kafka-test`
2. Klicke auf "Test-Reservierung senden" oder "Test-Buchung senden"
3. Beobachte die Console-Logs:
   - Producer sendet Command an Kafka
   - Consumer empfängt Command
   - Daten werden in PostgreSQL gespeichert
   - Event wird zurück an Kafka gesendet

### Programmatische Verwendung

#### Reservierung erstellen

```java
@Autowired
private ReservierungsService reservierungsService;

public void reservieren() {
    List<Long> sitzplatzIds = List.of(1L, 2L, 3L);
    reservierungsService.reservierePlaetze(
        auffuehrungId,  // ID der Aufführung
        kundeId,        // ID des Kunden (oder null für neue Kunden)
        "Max Mustermann",
        sitzplatzIds
    );
}
```

#### Buchung erstellen

```java
@Autowired
private BuchungsService buchungsService;

public void buchen() {
    List<Long> sitzplatzIds = List.of(1L, 2L, 3L);
    buchungsService.buchePlaetze(
        auffuehrungId,  // ID der Aufführung
        kundeId,        // ID des Kunden
        sitzplatzIds
    );
}
```

## Datenfluss im Detail

### Reservierung

1. **UI/Service-Call**: `ReservierungsService.reservierePlaetze()`
2. **Producer**: Erstellt `ReservationCommand` und sendet an Kafka-Topic `reservation-commands`
3. **Kafka**: Speichert Command im Topic
4. **Consumer**: `ReservationCommandConsumer` empfängt Command
5. **Datenbank**: 
   - Reservierung wird in PostgreSQL gespeichert
   - Kunde wird angelegt (falls neu)
   - ReservierungSitzplatz Join-Entities werden erstellt
   - Sitzplätze werden als reserviert markiert
6. **Event**: `ReservationEvent` wird an Topic `reservation-events` gesendet
7. **Weitere Services**: Können das Event konsumieren (z.B. für E-Mail-Benachrichtigungen)

### Buchung

1. **UI/Service-Call**: `BuchungsService.buchePlaetze()`
2. **Producer**: Erstellt `BookingCommand` und sendet an Kafka-Topic `booking-commands`
3. **Kafka**: Speichert Command im Topic
4. **Consumer**: `BookingCommandConsumer` empfängt Command
5. **Datenbank**:
   - Buchung wird in PostgreSQL gespeichert
   - BuchungSitzplatz Join-Entities werden erstellt
   - Sitzplätze werden als gebucht markiert
   - Reservierung wird entfernt (falls vorhanden)
   - Einnahmen der Aufführung werden aktualisiert
6. **Event**: `BookingEvent` wird an Topic `booking-events` gesendet
7. **Weitere Services**: Können das Event konsumieren

## Konfiguration (application.properties)

```properties
# Kafka Konfiguration
spring.kafka.bootstrap-servers=localhost:9092

# Producer (JSON Serialisierung)
spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer
spring.kafka.producer.value-serializer=org.springframework.kafka.support.serializer.JsonSerializer
spring.kafka.producer.properties.spring.json.add.type.headers=false

# Consumer (JSON Deserialisierung)
spring.kafka.consumer.key-deserializer=org.apache.kafka.common.serialization.StringDeserializer
spring.kafka.consumer.value-deserializer=org.springframework.kafka.support.serializer.JsonDeserializer
spring.kafka.consumer.properties.spring.json.trusted.packages=kino.application.kafka.events
spring.kafka.consumer.auto-offset-reset=earliest
spring.kafka.consumer.enable-auto-commit=true

# Kafka Topics
kino.kafka.topic.reservations=reservation-commands
kino.kafka.topic.bookings=booking-commands
kino.kafka.topic.reservation-events=reservation-events
kino.kafka.topic.booking-events=booking-events
```

## Vorteile dieser Architektur

1. **Entkopplung**: UI, Backend und Datenbank sind lose gekoppelt
2. **Skalierbarkeit**: Consumer können unabhängig skaliert werden
3. **Fehlertoleranz**: Kafka garantiert Message-Zustellung
4. **Erweiterbarkeit**: Neue Consumer können einfach hinzugefügt werden
5. **Audit-Trail**: Alle Events werden in Kafka gespeichert
6. **Event-Sourcing**: Vollständige Historie aller Vorgänge

## Monitoring

### Kafka Topics anzeigen

```bash
docker exec -it <kafka-container-name> kafka-topics --list --bootstrap-server localhost:9092
```

### Messages in einem Topic anzeigen

```bash
docker exec -it <kafka-container-name> kafka-console-consumer --bootstrap-server localhost:9092 --topic reservation-commands --from-beginning
```

### Consumer Groups anzeigen

```bash
docker exec -it <kafka-container-name> kafka-consumer-groups --bootstrap-server localhost:9092 --list
```

## Troubleshooting

### Kafka läuft nicht

Prüfe ob Docker Container laufen:
```bash
docker ps
```

Starte Docker Compose neu:
```bash
docker-compose down
docker-compose up -d
```

### Consumer empfängt keine Messages

1. Prüfe ob Consumer-Group registriert ist
2. Prüfe Logs auf Deserialisierungs-Fehler
3. Stelle sicher dass `trusted.packages` korrekt konfiguriert ist

### PostgreSQL Fehler

Prüfe Datenbankverbindung in `application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/mydatabase
spring.datasource.username=myuser
spring.datasource.password=mysecret
```

## Weiterentwicklung

Mögliche Erweiterungen:

1. **Dead Letter Queue**: Für fehlgeschlagene Messages
2. **Retry-Mechanismus**: Automatisches Wiederholen bei Fehlern
3. **Transaktionen**: Transaktionale Message-Verarbeitung
4. **Monitoring**: Integration mit Prometheus/Grafana
5. **Schema Registry**: Für Type-Safe Serialisierung
6. **Event-Versioning**: Für Schema-Evolution
7. **CQRS**: Command Query Responsibility Segregation
8. **Event Sourcing**: Vollständige Event-basierte Datenhaltung

## Autor

Erstellt für das Kino-Projekt mit Spring Boot, Vaadin und Apache Kafka.
