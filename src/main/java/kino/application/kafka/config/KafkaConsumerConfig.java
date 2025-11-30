package kino.application.kafka.config;

import kino.application.kafka.events.ReservationCommand;
import kino.application.kafka.events.ReservationDeleteCommand;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Kafka Consumer Konfiguration mit Unterstützung für verschiedene Command-Typen.
 */
@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    /**
     * Consumer Factory für ReservationDeleteCommand
     */
    @Bean
    public ConsumerFactory<String, ReservationDeleteCommand> deleteCommandConsumerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "kino-reservation-delete-worker");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        config.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class.getName());
        config.put(JsonDeserializer.VALUE_DEFAULT_TYPE, ReservationDeleteCommand.class.getName());
        config.put(JsonDeserializer.TRUSTED_PACKAGES, "kino.application.kafka.events");
        config.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        return new DefaultKafkaConsumerFactory<>(config, 
                new StringDeserializer(),
                new ErrorHandlingDeserializer<>(new JsonDeserializer<>(ReservationDeleteCommand.class)));
    }

    /**
     * Container Factory für ReservationDeleteCommand
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ReservationDeleteCommand> deleteCommandKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ReservationDeleteCommand> factory = 
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(deleteCommandConsumerFactory());
        return factory;
    }
}
