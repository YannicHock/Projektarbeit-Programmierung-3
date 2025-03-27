package de.prog3.projektarbeit.events;

import de.prog3.projektarbeit.eventHandling.EventHandler;
import de.prog3.projektarbeit.eventHandling.events.Event;
import de.prog3.projektarbeit.eventHandling.listeners.EventListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Testklasse für die EventHandler-Klasse.
 */
class EventHandlerTests {

    private EventHandler eventHandler;
    private EventListener<Event> mockListener;
    private Event mockEvent;

    /**
     * Initialisiert die Testobjekte vor jedem Test.
     */
    @BeforeEach
    void setUp() {
        eventHandler = EventHandler.getInstance();
        mockListener = mock(EventListener.class);
        mockEvent = mock(Event.class);
    }

    /**
     * Testet, ob registerListener den Listener zum Event hinzufügt.
     */
    @Test
    @DisplayName("registerListener should add listener to event")
    void registerListener_addsListenerToEvent() {
        eventHandler.registerListener(mockEvent.getClass(), mockListener);
        Map<Class<? extends Event>, ArrayList<EventListener<? extends Event>>> listeners = eventHandler.getListeners();
        assertTrue(listeners.containsKey(mockEvent.getClass()));
        assertTrue(listeners.get(mockEvent.getClass()).contains(mockListener));
    }

    /**
     * Testet, ob unregisterListener den Listener vom Event entfernt.
     */
    @Test
    @DisplayName("unregisterListener should remove listener from event")
    void unregisterListener_removesListenerFromEvent() {
        eventHandler.registerListener(mockEvent.getClass(), mockListener);
        eventHandler.unregisterListener(mockEvent.getClass(), mockListener);
        Map<Class<? extends Event>, ArrayList<EventListener<? extends Event>>> listeners = eventHandler.getListeners();
        assertTrue(listeners.containsKey(mockEvent.getClass()));
        assertFalse(listeners.get(mockEvent.getClass()).contains(mockListener));
    }

    /**
     * Testet, ob callEvent den Listener auf dem Event aufruft.
     */
    @Test
    @DisplayName("callEvent should invoke listener on event")
    void callEvent_invokesListenerOnEvent() {
        eventHandler.registerListener(mockEvent.getClass(), mockListener);
        eventHandler.callEvent(mockEvent);
        verify(mockListener, times(1)).onEvent(mockEvent);
    }

    /**
     * Testet, ob callEvent den Listener nicht aufruft, wenn keiner registriert ist.
     */
    @Test
    @DisplayName("callEvent should not invoke listener if none registered")
    void callEvent_doesNotInvokeListenerIfNoneRegistered() {
        eventHandler.callEvent(mockEvent);
        verify(mockListener, never()).onEvent(mockEvent);
    }

    /**
     * Testet, ob getInstance dieselbe Instanz zurückgibt.
     */
    @Test
    @DisplayName("getInstance should return the same instance")
    void getInstance_returnsSameInstance() {
        EventHandler anotherInstance = EventHandler.getInstance();
        assertSame(eventHandler, anotherInstance);
    }
}