package de.prog3.projektarbeit.eventHandling;

import de.prog3.projektarbeit.eventHandling.events.Event;
import de.prog3.projektarbeit.eventHandling.events.data.player.PlayerUpdateFinishedEvent;
import de.prog3.projektarbeit.eventHandling.listeners.EventListener;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Comparator;

public class EventHandler {

    private static EventHandler instance;

    private final Map<Class<? extends Event>, ArrayList<EventListener<? extends Event>>> listeners;

    private EventHandler() {
        listeners = new HashMap<>();
    }

    public static EventHandler getInstance() {
        if(instance == null) {
            instance = new EventHandler();
        }
        return instance;
    }

    public void unregisterListener(Class<? extends Event> event, EventListener<? extends Event> listener) {
        ArrayList<EventListener<? extends Event>> list = new ArrayList<>(listeners.get(event));
        list.remove(listener);
        listeners.put(event, list);
        System.out.println("Unregistering listener for " + event.getSimpleName() + " (" + list.size() + ")");
    }

    public void registerListener(Class<? extends Event> event, EventListener<? extends Event> listener) {
        ArrayList<EventListener<? extends Event>> list;
        if(listeners.containsKey(event)) {
            list = listeners.get(event);
        } else {
            list = new ArrayList<>();
        }
        list.add(listener);
        list.sort(Comparator.comparingInt(EventListener::getPriority));
        listeners.put(event, list);
        System.out.println("Registering listener for " + event.getSimpleName() + " (" + list.size() + ")");
    }

    public void callEvent(Event event) {
        ArrayList<EventListener<? extends Event>> listenerList = listeners.get(event.getClass());
        if(listenerList != null && !listenerList.isEmpty()) {
            System.out.println("Calling event " + event.getClass().getSimpleName() + " to " + listenerList.size() + " listeners");
            try {
                listenerList.forEach(listener -> {
                    System.out.println(listener.getClass().getName());
                    ((EventListener<Event>) listener).onEvent(event);
                });
            } catch (ConcurrentModificationException ignore) {}
        }
    }

}