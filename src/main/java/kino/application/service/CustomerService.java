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
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("email required");
        }
        Kunde existing = kundeRepository.findByEmail(email);
        if (existing != null) return existing;

        CustomerCommand cmd = new CustomerCommand(CustomerCommand.Action.CREATE, name, email);
        producer.send(cmd);

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
