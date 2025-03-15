package de.prog3.projektarbeit.eventHandling.events;

public interface Cancellable {


    boolean isCancelled();
    void setCancelled(boolean cancelled);

}
