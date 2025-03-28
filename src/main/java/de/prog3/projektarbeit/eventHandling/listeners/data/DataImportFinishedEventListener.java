package de.prog3.projektarbeit.eventHandling.listeners.data;

import de.prog3.projektarbeit.eventHandling.events.data.DataImportEvent;
import de.prog3.projektarbeit.eventHandling.events.data.DataImportFinishedEvent;
import de.prog3.projektarbeit.eventHandling.listeners.EventListener;

public abstract class DataImportFinishedEventListener extends EventListener<DataImportFinishedEvent> {
    protected DataImportFinishedEventListener() {
        super(DataImportFinishedEvent.class);
    }

    @Override
    public abstract void onEvent(DataImportFinishedEvent event);
}
