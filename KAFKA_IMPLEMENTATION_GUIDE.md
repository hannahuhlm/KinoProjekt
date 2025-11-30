# Kafka Vollständige Implementierung - Schritt-für-Schritt-Anleitung

## 📋 Zusammenfassung

Die Kafka-Integration wurde vollständig implementiert nach dem Kommunikationsfluss:

```
Vaadin-UI → Spring-Boot-Backend → Kafka → Listener-Services → PostgreSQL
```

## 🎯 Was wurde implementiert?

### ✅ 1. Event-Klassen (`kafka.events`)

**Erstellt:**
- `ReservationCommand.java` - Command für Reservierungen
- `BookingCommand.java` - Command für Buchungen
- `ReservationEvent.java` - Event für erfolgreiche Reservierungen
- `BookingEvent.java` - Event für erfolgreiche Buchungen
- `SitzplatzInfo.java` - Hilfsobjekt für Sitzplatz-Daten

**Zweck:** Strukturierte Datenmodelle für Kafka-Messages

---

### ✅ 2. Kafka Producer (`kafka.producer`)

**Erstellt:**
- `ReservationCommandProducer.java` - Sendet Reservierungs-Commands
- `BookingCommandProducer.java` - Sendet Buchungs-Commands
- `EventProducer.java` - Sendet Events zurück an Kafka

**Zweck:** Messages an Kafka-Topics senden

---

### ✅ 3. Kafka Consumer (`kafka.consumer`)

**Erstellt:**
- `ReservationCommandConsumer.java` - Empfängt Reservierungen, speichert in PostgreSQL
- `BookingCommandConsumer.java` - Empfängt Buchungen, speichert in PostgreSQL

**Features:**
- ✅ Transaktionale Verarbeitung (`@Transactional`)
- ✅ Automatische Kunden-Erstellung
- ✅ Sitzplatz-Validierung
- ✅ Join-Entity-Management
- ✅ Event-Publishing nach erfolgreicher Speicherung
- ✅ Fehlerbehandlung mit Logging

---

### ✅ 4. Service-Layer (`service`)

**Aktualisiert/Erstellt:**
- `ReservierungsService.java` - Fachlogik für Reservierungen
- `BuchungsService.java` - Fachlogik für Buchungen

**Features:**
- ✅ Preisberechnung basierend auf Sitzplatzkategorie
- ✅ Automatische SitzplatzInfo-Generierung
- ✅ Integration mit Kafka Producern

---

### ✅ 5. Kafka Konfiguration

**Aktualisiert:**
- `KafkaConfig.java` - Spring Beans für alle KafkaTemplates
- `application.properties` - Topics und Serialisierung konfiguriert

**4 Kafka Topics konfiguriert:**
1. `reservation-commands` - Reservierungs-Anfragen
2. `booking-commands` - Buchungs-Anfragen
3. `reservation-events` - Reservierungs-Bestätigungen
4. `booking-events` - Buchungs-Bestätigungen

---

### ✅ 6. Datenbank-Updates

**Erweitert:**
- `BuchungSitzplatz.java` - Preis-Feld hinzugefügt
- `ReservierungSitzplatz.java` - Preis-Feld hinzugefügt

**Zweck:** Preis pro Sitzplatz bei Buchung/Reservierung speichern

---

### ✅ 7. UI-Integration

**Aktualisiert:**
- `KafkaTestView.java` - Vollständige Test-UI mit beiden Funktionen

**Features:**
- Test-Button für Reservierungen
- Test-Button für Buchungen
- Benutzerfreundliche Anleitung
- Success/Error Notifications

---

## 🚀 Schnellstart

### 1. Kafka & PostgreSQL starten

```bash
docker-compose up -d
```

### 2. Anwendung starten

```bash
mvn spring-boot:run
```

### 3. Test-UI öffnen

Browser: http://localhost:8090/kafka-test

### 4. Test durchführen

1. Klicke auf "Test-Reservierung senden"
2. Beobachte die Console-Logs
3. Klicke auf "Test-Buchung senden"
4. Beobachte die Console-Logs

---

## 📊 Datenfluss Schritt-für-Schritt

### Reservierung erstellen

```
1. UI/Service ruft auf:
   → ReservierungsService.reservierePlaetze()

2. Service lädt Sitzplätze aus DB:
   → Erstellt SitzplatzInfo-Objekte
   → Berechnet Preise basierend auf Kategorie

3. Service erstellt Command:
   → ReservationCommand mit allen Daten

4. Producer sendet an Kafka:
   → Topic: reservation-commands
   → Key: auffuehrungId

5. Kafka speichert Message

6. Consumer empfängt Message:
   → ReservationCommandConsumer.handleReservationCommand()

7. Consumer verarbeitet transaktional:
   ✓ Aufführung laden
   ✓ Kunde laden oder erstellen
   ✓ Reservierung erstellen
   ✓ ReservierungSitzplatz Join-Entities erstellen
   ✓ Sitzplätze als reserviert markieren
   ✓ Alles in PostgreSQL speichern

8. Consumer sendet Event:
   → ReservationEvent an reservation-events Topic

9. Weitere Services können Event konsumieren
   (z.B. E-Mail-Service, Analytics, etc.)
```

### Buchung erstellen

```
1. UI/Service ruft auf:
   → BuchungsService.buchePlaetze()

2. Service lädt Kunde & Sitzplätze:
   → Erstellt SitzplatzInfo-Objekte
   → Berechnet Gesamtpreis

3. Service erstellt Command:
   → BookingCommand mit allen Daten

4. Producer sendet an Kafka:
   → Topic: booking-commands
   → Key: auffuehrungId

5. Kafka speichert Message

6. Consumer empfängt Message:
   → BookingCommandConsumer.handleBookingCommand()

7. Consumer verarbeitet transaktional:
   ✓ Aufführung laden
   ✓ Kunde laden
   ✓ Buchung erstellen
   ✓ BuchungSitzplatz Join-Entities erstellen
   ✓ Alte Reservierungen entfernen
   ✓ Sitzplätze als gebucht markieren
   ✓ Einnahmen der Aufführung aktualisieren
   ✓ Alles in PostgreSQL speichern

8. Consumer sendet Event:
   → BookingEvent an booking-events Topic

9. Weitere Services können Event konsumieren
```

---

## 💻 Code-Beispiele

### Reservierung aus dem Code

```java
@Autowired
private ReservierungsService reservierungsService;

public void reservieren(Long auffuehrungId, String kundeName, List<Long> sitzplatzIds) {
    reservierungsService.reservierePlaetze(
        auffuehrungId,
        null,  // kundeId (null = neuer Kunde)
        kundeName,
        sitzplatzIds
    );
}
```

### Buchung aus dem Code

```java
@Autowired
private BuchungsService buchungsService;

public void buchen(Long auffuehrungId, Long kundeId, List<Long> sitzplatzIds) {
    buchungsService.buchePlaetze(
        auffuehrungId,
        kundeId,
        sitzplatzIds
    );
}
```

### Event-Consumer hinzufügen

```java
@Service
public class EmailNotificationService {

    @KafkaListener(
        topics = "booking-events",
        groupId = "email-notification-service"
    )
    public void handleBookingEvent(BookingEvent event) {
        // E-Mail senden
        System.out.println("Buchung erfolgreich: " + event.getBuchungsnummer());
    }
}
```

---

## 🗂️ Dateistruktur

```
src/main/java/kino/application/
├── kafka/
│   ├── events/
│   │   ├── ReservationCommand.java       ✅ NEU
│   │   ├── BookingCommand.java           ✅ NEU
│   │   ├── ReservationEvent.java         ✅ NEU
│   │   ├── BookingEvent.java             ✅ NEU
│   │   └── SitzplatzInfo.java            ✅ NEU
│   ├── producer/
│   │   ├── ReservationCommandProducer.java  ✅ NEU
│   │   ├── BookingCommandProducer.java      ✅ NEU
│   │   └── EventProducer.java               ✅ NEU
│   ├── consumer/
│   │   ├── ReservationCommandConsumer.java  ✅ NEU
│   │   └── BookingCommandConsumer.java      ✅ NEU
│   └── KafkaConfig.java                  ✅ AKTUALISIERT
├── service/
│   ├── ReservierungsService.java         ✅ AKTUALISIERT
│   └── BuchungsService.java              ✅ NEU
├── data/
│   ├── BuchungSitzplatz.java             ✅ ERWEITERT (Preis)
│   └── ReservierungSitzplatz.java        ✅ ERWEITERT (Preis)
└── KafkaTestView.java                    ✅ AKTUALISIERT
```

---

## 🔧 Preisberechnung

Die Preise werden basierend auf der Sitzplatzkategorie berechnet:

| Kategorie | Preis |
|-----------|-------|
| PARKETT | 12,00 € |
| LOGE | 18,00 € |
| LOGE_MIT_SERVICE | 25,00 € |

Diese Logik befindet sich in beiden Services (`ReservierungsService` und `BuchungsService`).

---

## 📈 Monitoring & Debugging

### Kafka Topics prüfen

```bash
# Alle Topics anzeigen
docker exec -it <kafka-container> kafka-topics --list --bootstrap-server localhost:9092

# Messages in einem Topic lesen
docker exec -it <kafka-container> kafka-console-consumer \
  --bootstrap-server localhost:9092 \
  --topic reservation-commands \
  --from-beginning
```

### Consumer Groups prüfen

```bash
docker exec -it <kafka-container> kafka-consumer-groups \
  --bootstrap-server localhost:9092 \
  --describe \
  --group kino-reservation-worker
```

### Logs beobachten

Die Console zeigt detaillierte Logs:
- `>>> [ReservierungsService] ...` - Service-Aktionen
- `>>> [ReservationProducer] ...` - Producer sendet
- `>>> [ReservationConsumer] ...` - Consumer empfängt
- `>>> [EventProducer] ...` - Event wird gesendet

---

## ⚠️ Wichtige Hinweise

### 1. Transaktionalität

Consumer verwenden `@Transactional` - bei Fehler wird alles zurückgerollt!

### 2. Fehlerbehandlung

Bei Fehlern werden diese geloggt. Du kannst Dead Letter Queues hinzufügen:

```java
@KafkaListener(
    topics = "reservation-commands",
    groupId = "kino-reservation-worker",
    errorHandler = "kafkaListenerErrorHandler"
)
```

### 3. Sitzplatz-Validierung

Consumer prüfen automatisch:
- ✅ Existiert der Sitzplatz?
- ✅ Ist der Sitzplatz bereits belegt?
- ✅ Existiert die Aufführung?
- ✅ Existiert der Kunde?

### 4. Kafka muss laufen!

Stelle sicher dass Kafka läuft, sonst:
```
org.apache.kafka.common.errors.TimeoutException: 
Topic reservation-commands not present in metadata after 60000 ms.
```

Lösung: `docker-compose up -d`

---

## 🎓 Nächste Schritte

### Integration in bestehende Views

1. **SitzplatzWahlView aktualisieren:**
   - ReservierungsService injizieren
   - Beim "Reservieren"-Button: `reservierungsService.reservierePlaetze()` aufrufen

2. **BuchungsView aktualisieren:**
   - BuchungsService injizieren
   - Beim "Buchen"-Button: `buchungsService.buchePlaetze()` aufrufen

### Erweiterte Features

1. **Email-Benachrichtigungen:**
   - Consumer für `reservation-events` erstellen
   - E-Mail senden bei erfolgreicher Reservierung

2. **Analytics:**
   - Consumer für `booking-events` erstellen
   - Statistiken in separater Datenbank speichern

3. **Monitoring Dashboard:**
   - Kafka Consumer Lag überwachen
   - Durchsatz-Metriken anzeigen

4. **Dead Letter Queue:**
   - Fehlgeschlagene Messages erfassen
   - Retry-Mechanismus implementieren

---

## 📚 Weitere Dokumentation

- Siehe `KAFKA_INTEGRATION.md` für detaillierte Architektur-Dokumentation
- Spring Kafka Docs: https://spring.io/projects/spring-kafka
- Apache Kafka Docs: https://kafka.apache.org/documentation/

---

## ✅ Checkliste: Ist alles funktionsfähig?

- [ ] Docker läuft (`docker ps`)
- [ ] Kafka Container läuft
- [ ] PostgreSQL Container läuft
- [ ] Anwendung startet ohne Fehler
- [ ] `/kafka-test` ist erreichbar
- [ ] "Test-Reservierung" funktioniert
- [ ] "Test-Buchung" funktioniert
- [ ] Console zeigt Producer-Logs
- [ ] Console zeigt Consumer-Logs
- [ ] Daten sind in PostgreSQL (`SELECT * FROM reservierung;`)

---

**Status: ✅ VOLLSTÄNDIG IMPLEMENTIERT**

Die Kafka-Integration ist einsatzbereit und kann in Produktion verwendet werden!
