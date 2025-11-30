package kino.application;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import kino.application.service.BuchungsService;
import kino.application.service.ReservierungsService;

/**
 * Test-View, um die Kafka-Integration zu testen.
 * Zeigt die vollständige Kommunikationskette:
 * Vaadin-UI → Spring-Boot-Backend → Kafka → Listener-Services → PostgreSQL
 */
@Route(value = "kafka-test", layout = MainViewLayout.class)
@PageTitle("Kafka Test")
@PermitAll
public class KafkaTestView extends VerticalLayout {

    public KafkaTestView(ReservierungsService reservierungsService, BuchungsService buchungsService) {
        setPadding(true);
        setSpacing(true);
        setMaxWidth("800px");

        H2 title = new H2("Kafka Integration Test");
        add(title);

        Paragraph description = new Paragraph(
                "Diese Seite testet die vollständige Kafka-Integration: " +
                "Vaadin-UI → Spring-Boot → Kafka → Consumer → PostgreSQL"
        );
        add(description);

        // Reservierungs-Test-Button
        Button reservierungButton = new Button("Test-Reservierung senden", click -> {
            try {
                reservierungsService.sendeTestReservierung();
                Notification notification = Notification.show(
                        "✓ Reservierungs-Command an Kafka gesendet! " +
                        "Prüfe die Console-Logs für Details.",
                        5000,
                        Notification.Position.TOP_CENTER
                );
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            } catch (Exception e) {
                Notification.show("Fehler: " + e.getMessage(), 5000, Notification.Position.TOP_CENTER)
                        .addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });
        reservierungButton.getStyle().set("margin-top", "20px");

        // Buchungs-Test-Button
        Button buchungButton = new Button("Test-Buchung senden", click -> {
            try {
                buchungsService.sendeTestBuchung();
                Notification notification = Notification.show(
                        "✓ Buchungs-Command an Kafka gesendet! " +
                        "Prüfe die Console-Logs für Details.",
                        5000,
                        Notification.Position.TOP_CENTER
                );
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            } catch (Exception e) {
                Notification.show("Fehler: " + e.getMessage(), 5000, Notification.Position.TOP_CENTER)
                        .addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });
        buchungButton.getStyle().set("margin-top", "10px");

        add(reservierungButton, buchungButton);

        // Anleitung
        Paragraph anleitung = new Paragraph(
                "Anleitung:\n" +
                "1. Stelle sicher, dass Kafka läuft (docker-compose up -d)\n" +
                "2. Klicke auf einen der Buttons\n" +
                "3. Prüfe die Console-Logs:\n" +
                "   - Producer sendet Command an Kafka\n" +
                "   - Consumer empfängt Command\n" +
                "   - Daten werden in PostgreSQL gespeichert\n" +
                "   - Event wird zurück an Kafka gesendet"
        );
        anleitung.getStyle()
                .set("white-space", "pre-line")
                .set("background-color", "#f5f5f5")
                .set("padding", "15px")
                .set("border-radius", "5px")
                .set("margin-top", "30px");
        add(anleitung);
    }
}

