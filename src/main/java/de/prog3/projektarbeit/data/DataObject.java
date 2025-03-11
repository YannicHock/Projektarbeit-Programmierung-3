package de.prog3.projektarbeit.data;

import de.prog3.projektarbeit.eventHandling.events.Event;
import de.prog3.projektarbeit.eventHandling.listeners.EventListener;

import java.util.ArrayList;
import java.util.UUID;

public abstract class DataObject {
    abstract public void registerListener();
    private final ArrayList<EventListener<? extends Event>> listeners;

    public DataObject(){
        listeners = new ArrayList<>();
        registerListener();
    }

    public void addListener(EventListener<? extends Event> listener){
        listeners.add(listener);
    }
}
