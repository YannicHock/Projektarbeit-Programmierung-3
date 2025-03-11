package de.prog3.projektarbeit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ExampleTest {

    private static App app;

    @BeforeAll
    public static void setUp() {
        app = new App(); // Ensure this line correctly initializes the app instance
    }

    @Test
    public void testMagicNumber() {
    }

    @Test
    public void testAllWaysFalse() {
    }
}