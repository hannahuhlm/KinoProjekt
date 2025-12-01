# KinoProjekt – Umgebung, Installation & Entwicklung

Dieses Dokument beschreibt, wie du die lokale Umgebung einrichtest, den Stack startest und das Projekt weiterentwickelst.

## Voraussetzungen

- Java JDK 21: Für Spring Boot 3.5.x und Java 21 Build/Run
	- Download (Temurin 21 LTS): https://adoptium.net/temurin/releases/?version=21
- Apache Maven 3.9+: Zum Bauen und Starten (`pom.xml` nutzt Vaadin Plugin)
	- Download: https://maven.apache.org/download.cgi
- Docker Desktop (inkl. Docker Compose v2): Startet Kafka, Zookeeper, PostgreSQL, MongoDB, UIs
	- Download (Windows): https://www.docker.com/products/docker-desktop/
- Node.js 18+ (empfohlen): Vaadin 24 verwendet Vite/Node für das Frontend; das Vaadin Maven Plugin kann Node autom. beziehen, lokal installiert ist jedoch schneller/stabiler
	- Download (LTS empfohlen): https://nodejs.org/en/download/
- Git: Repository klonen und Branches verwalten
	- Download (Windows): https://git-scm.com/download/win
  
Optional/IDE:
- Visual Studio Code: https://code.visualstudio.com/
- IntelliJ IDEA: https://www.jetbrains.com/idea/download/

Optional/Tools:
- Kafka UI (im Compose enthalten): http://localhost:8081
- Mongo Express (im Compose enthalten): http://localhost:8082
- pgAdmin (im Compose enthalten): http://localhost:8083 (Login: admin@local / admin123)


## Dienste & Ports

- Spring Boot App: http://localhost:8090
- Kafka Broker: `localhost:9092` (Zookeeper: 2181)
- PostgreSQL: `localhost:5432` (DB: `mydatabase`, User: `myuser`, PW: `mysecret`)
- MongoDB: `localhost:27017` (Root: `root`/`rootpassword`, DB: `kino_analytics`)
- Kafka UI: http://localhost:8081
- Mongo Express: http://localhost:8082
- pgAdmin: http://localhost:8083

Siehe `docker-compose.yml` und `src/main/resources/application.properties` für exakte Einstellungen.

## Projekt klonen

```powershell
# PowerShell (Windows)
cd C:\Users\admin\VSCode-Projects
git clone https://github.com/hannahuhlm/KinoProjekt KinoProjekt
cd .\KinoProjekt\KinoProjekt
```

## Infrastruktur starten (Docker Compose)
```powershell
# Startet Zookeeper, Kafka, Kafka-UI, Postgres, pgAdmin, Mongo, Mongo-Express
docker compose up -d

# Status prüfen
docker compose ps
```

Warte, bis alle Container „healthy“/„running“ sind. Prüfe UIs:
- Kafka UI: http://localhost:8081
- Mongo Express: http://localhost:8082
- pgAdmin: http://localhost:8083 (Login: admin@local / admin123)

## Backend bauen & starten

```powershell
# Abhängigkeiten laden & kompilieren
& "C:\\Tools\\apache-maven-3.9.11\\bin\\mvn.cmd" -q clean package
# Entwicklung: mit Live-Reload (DevTools) starten
& "C:\\Tools\\apache-maven-3.9.11\\bin\\mvn.cmd" spring-boot:run
```

Hinweise:
- Erststart kann länger dauern, da Vaadin das Frontend (Vite) vorbereitet.
- Alternativ: `java -jar target/kino.application-0.0.1-SNAPSHOT.jar` nach `mvn package`.

## Anwendung aufrufen

- UI Einstieg: http://localhost:8090
- Einnahmen/Analytics: Route `einnahmen` (MongoDB Aggregation, „Jetzt aggregieren“)
- Sitzplatzwahl: Route `sitzplatzwahl/:auffuehrungId`

## Entwickeln (Empfohlener Workflow)
- IDE: VS Code oder IntelliJ, JDK 21 konfigurieren
- Branch: `kafka`
- Live-Reload: `spring-boot-devtools` ist aktiv; Backend‑Änderungen werden automatisch neu geladen
- Frontend (Vaadin 24 + Vite): Wird durch das Vaadin Plugin während `spring-boot:run` gebaut; Node 18+ beschleunigt Builds

Typische Kommandos:
```powershell
# Schnell bauen
& "C:\\Tools\\apache-maven-3.9.11\\bin\\mvn.cmd" -q compile
# Tests ausführen
& "C:\\Tools\\apache-maven-3.9.11\\bin\\mvn.cmd" -q test

# Anwendung mit Hot-Reload
& "C:\\Tools\\apache-maven-3.9.11\\bin\\mvn.cmd" spring-boot:run
```

## Kafka & Aggregation (MongoDB)
- Aggregation triggert das UI per Button „📊 Jetzt aggregieren“ (Route `einnahmen`).
- Technisch sendet `AggregationCommandProducer` ein `AggregationCommand(day, correlationId)` auf Topic `kino-aggregation` (Konfig: `kino.kafka.topic.aggregation`).
- `AggregationCommandConsumer` ruft `AggregationService.aggregateDay()` auf:
	- löscht Tagesaggregate in Mongo („Replace“)
	- lädt Buchungen (Postgres), gruppiert nach Aufführung
	- speichert `RevenueAggregate` in Mongo (`daily_revenue`)
	- publiziert `AggregationResultEvent` auf `aggregation-events`
- `AggregationResultConsumer` broadcastet das Event an den UI‑EventBus; das UI aktualisiert die Ansicht.

Weitere Details siehe die AsyncAPI-Spezifikation `KINO_API.yml` (alle Kafka-Themen, Nachrichten und Schemas).

## Konfiguration (Ausschnitt)
Datei: `src/main/resources/application.properties`
- Server Port: `server.port=8090`
- PostgreSQL: `spring.datasource.url=jdbc:postgresql://localhost:5432/mydatabase`
- MongoDB: `spring.data.mongodb.uri=mongodb://root:rootpassword@localhost:27017/` und `spring.data.mongodb.database=kino_analytics`
- Kafka Bootstrap: `spring.kafka.bootstrap-servers=localhost:9092`
- Topics: `kino.kafka.topic.*` Einträge (reservations, booking, admin, customer, aggregation, aggregation-events)

### pgAdmin Verbindung
- URL: http://localhost:8083 (Default Login: `admin@local` / `admin123`)
- Vorkonfigurierter Server: Beim ersten Login ist `Kino-Postgres` bereits vorhanden (Host `postgres`, Port `5432`, DB `mydatabase`, User `myuser`).
- Falls nicht sichtbar (alte Containerdaten):
```powershell
docker compose down
docker compose up -d pgadmin
```
	Danach neu anmelden und prüfen.

## Troubleshooting
- Ports belegt:
	- Prüfe, ob andere Dienste bereits 5432/27017/8081/8082/9092/8090 nutzen.
	- Stoppe alte Container: `docker compose down` (ggf. mit `-v` wenn Volumes gelöscht werden sollen).

- Kafka‑Verbindung schlägt fehl:
	- Warte bis `zookeeper` und `kafka` laufen; prüfe Kafka UI.
	- `spring.kafka.bootstrap-servers` muss `localhost:9092` sein (siehe Compose).

-- Postgres/Mongo Auth:
	- Nutze die in Compose hinterlegten Credentials (Postgres: `myuser`/`mysecret`; Mongo Root: `root`/`rootpassword`).

- Vaadin/Node Build hängt:
	- Installiere Node 18+ lokal.
	- Leere Vaadin Build‑Caches: `mvn -Pproduction clean` (optional) und erneut starten.

## Nützliche URLs
- App: http://localhost:8090
- Kafka UI: http://localhost:8081
- Mongo Express: http://localhost:8082
- pgAdmin: http://localhost:8083



