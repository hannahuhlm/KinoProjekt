package kino.application.customer;

import kino.application.kafka.events.CustomerEvent;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Consumer;

public class CustomerUIEventBus {
    private static final Set<Consumer<CustomerEvent>> listeners = new CopyOnWriteArraySet<>();

    public static Registration register(Consumer<CustomerEvent> listener) {
        listeners.add(listener);
        return () -> listeners.remove(listener);
    }

    public static void broadcast(CustomerEvent event) {
        for (Consumer<CustomerEvent> l : listeners) {
            try { l.accept(event); } catch (Exception ignored) {}
        }
    }

    @FunctionalInterface
    public interface Registration { void remove(); }
}
