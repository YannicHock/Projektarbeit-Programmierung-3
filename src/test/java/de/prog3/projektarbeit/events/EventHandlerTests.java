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

class EventHandlerTests {

    private EventHandler eventHandler;
    private EventListener<Event> mockListener;
    private Event mockEvent;

    @BeforeEach
    void setUp() {
        eventHandler = EventHandler.getInstance();
        mockListener = mock(EventListener.class);
        mockEvent = mock(Event.class);
    }

    @Test
    @DisplayName("registerListener should add listener to event")
    void registerListener_addsListenerToEvent() {
        eventHandler.registerListener(mockEvent.getClass(), mockListener);
        Map<Class<? extends Event>, ArrayList<EventListener<? extends Event>>> listeners = eventHandler.getListeners();
        assertTrue(listeners.containsKey(mockEvent.getClass()));
        assertTrue(listeners.get(mockEvent.getClass()).contains(mockListener));
    }

    @Test
    @DisplayName("unregisterListener should remove listener from event")
    void unregisterListener_removesListenerFromEvent() {
        eventHandler.registerListener(mockEvent.getClass(), mockListener);
        eventHandler.unregisterListener(mockEvent.getClass(), mockListener);
        Map<Class<? extends Event>, ArrayList<EventListener<? extends Event>>> listeners = eventHandler.getListeners();
        assertTrue(listeners.containsKey(mockEvent.getClass()));
        assertFalse(listeners.get(mockEvent.getClass()).contains(mockListener));
    }

    @Test
    @DisplayName("callEvent should invoke listener on event")
    void callEvent_invokesListenerOnEvent() {
        eventHandler.registerListener(mockEvent.getClass(), mockListener);
        eventHandler.callEvent(mockEvent);
        verify(mockListener, times(1)).onEvent(mockEvent);
    }

    @Test
    @DisplayName("callEvent should not invoke listener if none registered")
    void callEvent_doesNotInvokeListenerIfNoneRegistered() {
        eventHandler.callEvent(mockEvent);
        verify(mockListener, never()).onEvent(mockEvent);
    }

    @Test
    @DisplayName("getInstance should return the same instance")
    void getInstance_returnsSameInstance() {
        EventHandler anotherInstance = EventHandler.getInstance();
        assertSame(eventHandler, anotherInstance);
    }
}