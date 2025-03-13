package de.prog3.projektarbeit.exceptions;

import java.util.ArrayList;

public class ValidationException extends Exception{

    private final ArrayList<Exception> exceptions;
    public ValidationException(ArrayList<Exception> exceptions) {
        this.exceptions = exceptions;
    }

    public ArrayList<Exception> getExceptions() {
        return exceptions;
    }
}
