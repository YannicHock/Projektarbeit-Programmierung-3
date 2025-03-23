package de.prog3.projektarbeit.eventHandling;

import de.prog3.projektarbeit.eventHandling.events.Event;
import de.prog3.projektarbeit.eventHandling.listeners.EventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Comparator;


public class EventHandler {

    private static final Logger logger = LoggerFactory.getLogger(EventHandler.class);
    private static final Marker DEBUG_DISABLED_MARKER = MarkerFactory.getMarker("DEBUG_DISABLED");
    private static EventHandler instance;

    private final Map<Class<? extends Event>, ArrayList<EventListener<? extends Event>>> listeners;

    private EventHandler() {
        listeners = new HashMap<>();
        logger.info("EventHandler wurde initialisiert.");
    }

    public static EventHandler getInstance() {
        if (instance == null) {
            logger.debug("Keine Instanz von EventHandler gefunden. Erstelle neue Instanz.");
            instance = new EventHandler();
            logger.info("Neue Instanz von EventHandler erstellt.");
        }
        return instance;
    }

    public void unregisterListener(Class<? extends Event> event, EventListener<? extends Event> listener) {
        logger.debug("Unregister-Listener-Methode aufgerufen für Event: {}", event.getSimpleName());
        ArrayList<EventListener<? extends Event>> list = new ArrayList<>(listeners.get(event));
        list.remove(listener);
        listeners.put(event, list);
        logger.info("Listener für {} abgemeldet ({} verbleibend)", event.getSimpleName(), list.size());
    }

    public void registerListener(Class<? extends Event> event, EventListener<? extends Event> listener) {
        logger.debug("Register-Listener-Methode aufgerufen für Event: {}", event.getSimpleName());
        ArrayList<EventListener<? extends Event>> list;
        if (listeners.containsKey(event)) {
            list = listeners.get(event);
        } else {
            list = new ArrayList<>();
        }
        list.add(listener);
        list.sort(Comparator.comparingInt(EventListener::getPriority));
        listeners.put(event, list);
        logger.info("Listener für {} registriert ({} insgesamt)", event.getSimpleName(), list.size());
    }

    public Map<Class<? extends Event>, ArrayList<EventListener<? extends Event>>> getListeners() {
        logger.debug("getListeners() Methode aufgerufen.");
        return listeners;
    }

    @SuppressWarnings("unchecked")
    public void callEvent(Event event) {
        logger.debug("callEvent() wurde aufgerufen für Event: {}", event.getClass().getSimpleName());
        ArrayList<EventListener<? extends Event>> listenerList = listeners.get(event.getClass());
        if (listenerList != null && !listenerList.isEmpty()) {
            logger.info("Rufe Event {} für {} Listener auf", event.getClass().getSimpleName(), listenerList.size());
            try {
                listenerList.forEach(listener -> {
                    String listenerName = listener.getClass().getSimpleName().replaceAll("\\$[0-9]*", "");
                    if (listenerName.isEmpty()) {
                        String[] parts = listener.getClass().getName().split("\\.");
                        listenerName = parts[parts.length - 1].replaceAll("\\$[0-9]*", "");
                    }
                    logger.info(DEBUG_DISABLED_MARKER, "Verarbeite Listener {} für Event {}", listenerName, event.getClass().getSimpleName());
                    logger.debug("Verarbeite Listener {} für Event {}", listener.getClass().getName(), event.getClass().getName());
                    ((EventListener<Event>) listener).onEvent(event);
                });
                logger.debug("Alle Listener für {} wurden erfolgreich aufgerufen.", event.getClass().getSimpleName());
            } catch (ConcurrentModificationException ignore) {
                logger.warn("ConcurrentModificationException während der Eventverarbeitung für {} aufgetreten.", event.getClass().getSimpleName());
            }
        } else {
            logger.info("Keine Listener gefunden für Event {}", event.getClass().getSimpleName());
        }
    }
}