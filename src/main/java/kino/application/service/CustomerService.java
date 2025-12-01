package kino.application.service;

import kino.application.data.Kunde;
import kino.application.data.KundeRepository;
import kino.application.kafka.events.CustomerCommand;
import kino.application.kafka.producer.CustomerCommandProducer;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerCommandProducer producer;
    private final KundeRepository kundeRepository;

    public CustomerService(CustomerCommandProducer producer, KundeRepository kundeRepository) {
        this.producer = producer;
        this.kundeRepository = kundeRepository;
    }

    /**
     * Ensure a customer with the given email exists. If not, emit Kafka command and
     * wait briefly until it is persisted, then return the entity from the repository.
     */
    public Kunde ensureCustomer(String name, String email) {
        return ensureCustomer(name, email, null);
    }

    /**
     * Ensure a customer with the given email exists. If not, emit Kafka command with correlationId.
     * If correlationId is provided, does NOT wait/poll - caller should listen for CustomerEvent.
     * If correlationId is null, falls back to polling behavior.
     */
    public Kunde ensureCustomer(String name, String email, String correlationId) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("email required");
        }
        Kunde existing = kundeRepository.findByEmail(email);
        if (existing != null) return existing;

        CustomerCommand cmd = new CustomerCommand(CustomerCommand.Action.CREATE, name, email);
        if (correlationId != null) {
            cmd.setCorrelationId(correlationId);
        }
        producer.send(cmd);

        // If correlationId provided, return null immediately - caller waits for event
        if (correlationId != null) {
            return null;
        }

        // Simple polling for eventual consistency (reads are allowed in UI)
        int attempts = 0;
        while (attempts < 20) { // up to ~2s with 100ms sleep
            try { Thread.sleep(100); } catch (InterruptedException ignored) {}
            Kunde k = kundeRepository.findByEmail(email);
            if (k != null) return k;
            attempts++;
        }
        // last attempt
        return kundeRepository.findByEmail(email);
    }
}
