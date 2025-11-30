package kino.application.admin;

import kino.application.kafka.events.AdminEvent;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Consumer;

/**
 * Simple broadcaster for AdminEvents to all registered UI listeners.
 */
public class AdminUIEventBus {
    private static final Set<Consumer<AdminEvent>> listeners = new CopyOnWriteArraySet<>();

    public static Registration register(Consumer<AdminEvent> listener) {
        listeners.add(listener);
        return () -> listeners.remove(listener);
    }

    public static void broadcast(AdminEvent event) {
        for (Consumer<AdminEvent> l : listeners) {
            try {
                l.accept(event);
            } catch (Exception ignored) {}
        }
    }

    @FunctionalInterface
    public interface Registration {
        void remove();
    }
}
