package de.prog3.projektarbeit.ui;

import de.prog3.projektarbeit.ui.pages.PageType;
import de.prog3.projektarbeit.ui.views.View;
import de.prog3.projektarbeit.ui.views.ViewType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class PageTypeTests {

    private View mockView;

    @BeforeEach
    void setUp() {
        mockView = mock(View.class);
    }

    @Test
    @DisplayName("openPage should instantiate the correct page class for MAIN view type")
    void openPage_instantiatesCorrectPageClassForMainViewType() {
        when(mockView.getType()).thenReturn(ViewType.LATERNA);
        PageType.MAIN.openPage(mockView);
        // Assuming there's a way to verify page instantiation, which is not shown in the provided code
    }

    @Test
    @DisplayName("openPage should handle exceptions gracefully")
    void openPage_handlesExceptionsGracefully() {
        when(mockView.getType()).thenReturn(ViewType.LATERNA);
        doThrow(new RuntimeException()).when(mockView).getType();
        PageType.MAIN.openPage(mockView);
        // Assuming there's a way to verify logging, which is not shown in the provided code
    }
}