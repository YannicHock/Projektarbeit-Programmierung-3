package de.prog3.projektarbeit.events;

import de.prog3.projektarbeit.eventHandling.events.Event;
import de.prog3.projektarbeit.eventHandling.listeners.EventListener;
import de.prog3.projektarbeit.eventHandling.listeners.Priority;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testklasse für die Priorität von Event-Listenern.
 */
class PriorityTests {

    private List<String> callOrder;

    /**
     * Initialisiert die Liste zur Verfolgung der Aufrufreihenfolge vor jedem Test.
     */
    @BeforeEach
    void setUp() {
        callOrder = new ArrayList<>();
    }

    /**
     * Testet, ob die Listener in der Reihenfolge ihrer Priorität aufgerufen werden.
     */
    @Test
    @DisplayName("Listeners should be called in order of their priority")
    void listenersCalledInOrderOfPriority() {
        class TestEvent extends Event {
            public TestEvent() {
                super("TestEvent");
            }
        }

        new EventListener<>(TestEvent.class, Priority.HIGH) {
            @Override
            public void onEvent(TestEvent event) {
                callOrder.add("HIGH");
            }
        };

        new EventListener<>(TestEvent.class, Priority.NORMAL) {
            @Override
            public void onEvent(TestEvent event) {
                callOrder.add("NORMAL");
            }
        };

        new EventListener<>(TestEvent.class, Priority.LOW) {
            @Override
            public void onEvent(TestEvent event) {
                callOrder.add("LOW");
            }
        };

        new TestEvent().call();

        assertEquals(List.of("HIGH", "NORMAL", "LOW"), callOrder);
    }
}