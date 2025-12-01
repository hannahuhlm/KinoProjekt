package kino.application.aggregation;

import kino.application.kafka.events.AggregationResultEvent;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Consumer;

public class AggregationUIEventBus {
    private static final Set<Consumer<AggregationResultEvent>> listeners = new CopyOnWriteArraySet<>();

    public static Registration register(Consumer<AggregationResultEvent> listener) {
        listeners.add(listener);
        return () -> listeners.remove(listener);
    }

    public static void broadcast(AggregationResultEvent event) {
        for (Consumer<AggregationResultEvent> l : listeners) {
            try {
                l.accept(event);
            } catch (Exception ignored) {}
        }
    }

    @FunctionalInterface
    public interface Registration { void remove(); }
}
