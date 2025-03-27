package de.prog3.projektarbeit.events;

        import de.prog3.projektarbeit.eventHandling.events.Cancellable;
        import org.junit.jupiter.api.BeforeEach;
        import org.junit.jupiter.api.DisplayName;
        import org.junit.jupiter.api.Test;

        import static org.junit.jupiter.api.Assertions.*;

        /**
         * Testklasse für die Cancellable-Schnittstelle.
         */
        class CancellableTest {

            private Cancellable cancellable;

            /**
             * Initialisiert das Cancellable-Objekt vor jedem Test.
             */
            @BeforeEach
            void setUp() {
                cancellable = new Cancellable() {
                    private boolean cancelled;

                    @Override
                    public boolean isCancelled() {
                        return cancelled;
                    }

                    @Override
                    public void setCancelled(boolean cancelled) {
                        this.cancelled = cancelled;
                    }
                };
            }

            /**
             * Testet, ob isCancelled() initial false zurückgibt.
             */
            @Test
            @DisplayName("isCancelled should return false initially")
            void isCancelled_returnsFalseInitially() {
                assertFalse(cancellable.isCancelled());
            }

            /**
             * Testet, ob setCancelled(true) den Status auf true setzt.
             */
            @Test
            @DisplayName("setCancelled should set cancelled to true")
            void setCancelled_setsCancelledToTrue() {
                cancellable.setCancelled(true);
                assertTrue(cancellable.isCancelled());
            }

            /**
             * Testet, ob setCancelled(false) den Status auf false setzt.
             */
            @Test
            @DisplayName("setCancelled should set cancelled to false")
            void setCancelled_setsCancelledToFalse() {
                cancellable.setCancelled(true);
                cancellable.setCancelled(false);
                assertFalse(cancellable.isCancelled());
            }
        }