package de.prog3.projektarbeit.eventHandling.events.data;

import de.prog3.projektarbeit.eventHandling.events.Event;

import java.util.ArrayList;
import java.util.Optional;

public class DataImportFinishedEvent extends Event {
    private final ArrayList<Exception> exceptions;

    public DataImportFinishedEvent(ArrayList<Exception> exceptions) {
        super("DataImportEvent");
        this.exceptions = exceptions;
    }

    public Optional<ArrayList<Exception>> getExceptions() {
        return Optional.ofNullable(exceptions);
    }

}
