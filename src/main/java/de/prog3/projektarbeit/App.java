package de.prog3.projektarbeit;

import de.prog3.projektarbeit.userinterface.ExampleInterface;

public class App {

    private ExampleInterface exampleInterface;

    public App() {
        this.exampleInterface = new ExampleInterface();
    }

    public void start() {
        this.exampleInterface.start();
    }


    public static void main(String[] args) {
        App app = new App();
        app.start();
    }


    public int magicNumber() {
        return 42;
    }

    public boolean allWaysFalse(){
        return false;
    }
}
