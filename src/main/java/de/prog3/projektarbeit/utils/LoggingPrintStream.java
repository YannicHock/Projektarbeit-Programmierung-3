package de.prog3.projektarbeit.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintStream;

public class LoggingPrintStream extends PrintStream {
    private static final Logger logger = LoggerFactory.getLogger("StandardOut");
    private final PrintStream original;

    public LoggingPrintStream(PrintStream original) {
        super(original);
        this.original = original;
    }

    @Override
    public void println(String x) {
        logCallerInfo();
        original.println(x);
    }

    @Override
    public void print(String s) {
        logCallerInfo();
        original.print(s);
    }

    private void logCallerInfo() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StackTraceElement caller = null;
        for (StackTraceElement element : stackTrace) {
            if (!element.getClassName().equals(LoggingPrintStream.class.getName()) &&
                    !element.getClassName().startsWith("java.") &&
                    !element.getClassName().startsWith("sun.")) {
                caller = element;
                break;
            }
        }
        if (caller != null) {
            logger.warn("{}:{} System.out verwendet das sollte dringend vermieden werden", caller.getFileName(), caller.getLineNumber());
        }
    }
}