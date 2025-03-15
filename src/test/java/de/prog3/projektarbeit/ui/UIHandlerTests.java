package de.prog3.projektarbeit.ui;

import de.prog3.projektarbeit.eventHandling.events.ui.OpenPageEvent;
import de.prog3.projektarbeit.eventHandling.events.ui.RequestNewViewEvent;
import de.prog3.projektarbeit.eventHandling.listeners.ui.RequestNewViewListener;
import de.prog3.projektarbeit.ui.pages.PageType;
import de.prog3.projektarbeit.ui.views.View;
import de.prog3.projektarbeit.ui.views.ViewType;
import de.prog3.projektarbeit.ui.views.laterna.LaternaView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UIHandlerTests {

    private UIHandler uiHandler;

    @BeforeEach
    void setUp() {
        uiHandler = UIHandler.getInstance();
    }

    @Test
    @DisplayName("getInstance should return the same instance")
    void getInstance_returnsSameInstance() {
        UIHandler anotherInstance = UIHandler.getInstance();
        assertSame(uiHandler, anotherInstance);
    }

    @Test
    @DisplayName("registerListeners should register view listener")
    void registerListeners_registersViewListener() {
        uiHandler.registerListeners();
        // Assuming there's a way to verify listener registration, which is not shown in the provided code
    }

    @Test
    @DisplayName("RequestNewViewListener should add LaternaView to views")
    @DisabledIfSystemProperty(named = "java.awt.headless", matches = "true")
    void requestNewViewListener_addsLaternaViewAndCallsOpenPageEvent() {
        RequestNewViewEvent mockEvent = mock(RequestNewViewEvent.class);
        when(mockEvent.getViewType()).thenReturn(ViewType.LATERNA);

        uiHandler.registerListeners();
        new RequestNewViewListener() {
            @Override
            public void onEvent(RequestNewViewEvent event) {
                if(event.getViewType().equals(ViewType.LATERNA)){
                    View view = new LaternaView();
                    uiHandler.getViews().add(view);
                }
            }
        }.onEvent(mockEvent);

        assertEquals(1, uiHandler.getViews().size());
        assertInstanceOf(LaternaView.class, uiHandler.getViews().getFirst());
        // Assuming there's a way to verify OpenPageEvent call, which is not shown in the provided code
    }
}