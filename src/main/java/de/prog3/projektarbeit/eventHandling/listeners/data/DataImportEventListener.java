package de.prog3.projektarbeit.eventHandling.listeners.data;

import de.prog3.projektarbeit.eventHandling.events.data.DataImportEvent;
import de.prog3.projektarbeit.eventHandling.listeners.EventListener;

public abstract class DataImportEventListener extends EventListener<DataImportEvent> {
    protected DataImportEventListener() {
        super(DataImportEvent.class);
    }

    @Override
    public abstract void onEvent(DataImportEvent event);
}
