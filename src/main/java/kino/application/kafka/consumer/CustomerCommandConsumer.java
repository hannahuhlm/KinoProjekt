package kino.application.kafka.consumer;

import kino.application.data.Kunde;
import kino.application.data.KundeRepository;
import kino.application.kafka.events.CustomerCommand;
import kino.application.kafka.events.CustomerEvent;
import kino.application.customer.CustomerUIEventBus;
import kino.application.kafka.producer.CustomerEventProducer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerCommandConsumer {

    private final KundeRepository kundeRepository;
    private final CustomerEventProducer eventProducer;

    public CustomerCommandConsumer(KundeRepository kundeRepository, CustomerEventProducer eventProducer) {
        this.kundeRepository = kundeRepository;
        this.eventProducer = eventProducer;
    }

    @KafkaListener(topics = "${kino.kafka.topic.customer}", groupId = "kino-customer-worker")
    @Transactional
    public void onMessage(CustomerCommand cmd) {
        if (cmd == null || cmd.getAction() == null) return;
        try {
            switch (cmd.getAction()) {
                case CREATE -> handleCreate(cmd);
                case QUERY -> handleQuery(cmd);
            }
        } catch (Exception e) {
            CustomerEvent ev = new CustomerEvent(CustomerEvent.Action.valueOf(cmd.getAction().name()), CustomerEvent.Status.FAILURE);
            ev.setEmail(cmd.getEmail());
            ev.setCorrelationId(cmd.getCorrelationId());
            ev.setMessage(e.getMessage());
            eventProducer.send(ev);
            CustomerUIEventBus.broadcast(ev);
            throw e;
        }
    }

    private void handleCreate(CustomerCommand cmd) {
        if (cmd.getEmail() == null || cmd.getEmail().isBlank()) return;
        Kunde existing = kundeRepository.findByEmail(cmd.getEmail());
        if (existing != null) {
            CustomerEvent ev = new CustomerEvent(CustomerEvent.Action.CREATE, CustomerEvent.Status.SUCCESS);
            ev.setKundeId(existing.getId());
            ev.setEmail(existing.getEmail());
            ev.setCorrelationId(cmd.getCorrelationId());
            ev.setMessage("exists");
            eventProducer.send(ev);
            CustomerUIEventBus.broadcast(ev);
            return;
        }
        Kunde k = new Kunde();
        k.setEmail(cmd.getEmail());
        k.setName(cmd.getName());
        k = kundeRepository.save(k);
        CustomerEvent ev = new CustomerEvent(CustomerEvent.Action.CREATE, CustomerEvent.Status.SUCCESS);
        ev.setKundeId(k.getId());
        ev.setEmail(k.getEmail());
        ev.setCorrelationId(cmd.getCorrelationId());
        eventProducer.send(ev);
        CustomerUIEventBus.broadcast(ev);
    }

    private void handleQuery(CustomerCommand cmd) {
        CustomerEvent ev = new CustomerEvent(CustomerEvent.Action.QUERY, CustomerEvent.Status.SUCCESS);
        ev.setCorrelationId(cmd.getCorrelationId());
        ev.setEmail(cmd.getEmail());
        if (cmd.getEmail() == null || cmd.getEmail().isBlank()) {
            ev.setStatus(CustomerEvent.Status.NOT_FOUND);
            eventProducer.send(ev);
            CustomerUIEventBus.broadcast(ev);
            return;
        }
        Kunde existing = kundeRepository.findByEmail(cmd.getEmail());
        if (existing == null) {
            ev.setStatus(CustomerEvent.Status.NOT_FOUND);
        } else {
            ev.setKundeId(existing.getId());
            ev.setStatus(CustomerEvent.Status.SUCCESS);
        }
        eventProducer.send(ev);
        CustomerUIEventBus.broadcast(ev);
    }
}
