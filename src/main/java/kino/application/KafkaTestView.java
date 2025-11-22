package kino.application;

import com.vaadin.flow.component.button.Button;

import com.vaadin.flow.component.notification.Notification;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import com.vaadin.flow.router.PageTitle;

import com.vaadin.flow.router.Route;

import jakarta.annotation.security.PermitAll;

import kino.application.service.ReservierungsService;

/**

 * Einfache Test-View, um Kafka zu verifizieren.

 */

@Route(value = "kafka-test", layout = MainView.class)

@PageTitle("Kafka Test")

@PermitAll

public class KafkaTestView extends VerticalLayout {

    public KafkaTestView(ReservierungsService reservierungsService) {

        setPadding(true);

        setSpacing(true);

        Button testButton = new Button("Test-Reservierung an Kafka schicken", click -> {

            reservierungsService.sendeTestReservierung();

            Notification.show("Test-Reservierung per Kafka verschickt");

        });

        add(testButton);

    }

}

