package kino.application.kafka;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;

import org.springframework.kafka.annotation.EnableKafka;

import org.springframework.kafka.core.KafkaTemplate;

import org.springframework.kafka.core.ProducerFactory;

/** Max S. Jonas C. 21.11.2025 01:29

 * Basiskonfiguration für Kafka.

 * Spring Boot erzeugt ProducerFactory/ConsumerFactory

 * anhand der application.properties automatisch.

 */

@EnableKafka

@Configuration

public class KafkaConfig {

    @Bean

    public KafkaTemplate<String, ReservationCommand> reservationKafkaTemplate(

            ProducerFactory<String, ReservationCommand> producerFactory) {

        return new KafkaTemplate<>(producerFactory);

    }

}

