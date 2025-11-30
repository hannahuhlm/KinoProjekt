package kino.application.kafka;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

/**
 * Basiskonfiguration für Kafka.
 * Nutzt Spring Boot Auto-Konfiguration und stellt eine generische KafkaTemplate bereit.
 */
@EnableKafka
@Configuration
public class KafkaConfig {

    /**
     * Stellt eine generische KafkaTemplate<String, Object> bereit.
     * Spring Boot liefert ProducerFactory<Object, Object> automatisch
     * basierend auf application.properties.
     */
    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate(ProducerFactory<String, Object> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }
}

