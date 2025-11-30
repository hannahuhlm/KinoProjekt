package kino.application.reservation;

import kino.application.kafka.events.ReservationEvent;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Consumer;

public class ReservationUIEventBus {
    private static final Set<Consumer<ReservationEvent>> listeners = new CopyOnWriteArraySet<>();

    public static Registration register(Consumer<ReservationEvent> listener) {
        listeners.add(listener);
        return () -> listeners.remove(listener);
    }

    public static void broadcast(ReservationEvent event) {
        for (Consumer<ReservationEvent> l : listeners) {
            try { l.accept(event); } catch (Exception ignored) {}
        }
    }

    @FunctionalInterface
    public interface Registration { void remove(); }
}