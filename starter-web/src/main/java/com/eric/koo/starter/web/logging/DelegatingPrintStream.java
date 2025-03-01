package com.eric.koo.starter.web.logging;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

class DelegatingPrintStream extends PrintStream {

    private final List<String> packages;
    private final PrintStream loggingPrintStream;
    private final PrintStream defaultPrintStream;

    DelegatingPrintStream(List<String> packages) {
        super(System.out);
        this.packages = packages;
        this.loggingPrintStream = new LoggingPrintStream();
        this.defaultPrintStream = System.out;
    }

    private PrintStream getPrintStream() {
        var isLoggingRequired = Arrays.stream(Thread.currentThread().getStackTrace())
                .map(StackTraceElement::getClassName)
                .anyMatch(className -> 
                        packages.stream()
                                .anyMatch(className::startsWith)
                );
        
        return isLoggingRequired 
                ? loggingPrintStream 
                : defaultPrintStream;
    }

    @Override
    public void print(boolean b) {
        getPrintStream().print(b);
    }

    @Override
    public void print(char c) {
        getPrintStream().print(c);
    }

    @Override
    public void print(int i) {
        getPrintStream().print(i);
    }

    @Override
    public void print(long l) {
        getPrintStream().print(l);
    }

    @Override
    public void print(float f) {
        getPrintStream().print(f);
    }

    @Override
    public void print(double d) {
        getPrintStream().print(d);
    }

    @Override
    public void print(char[] s) {
        getPrintStream().print(s);
    }

    @Override
    public void print(String s) {
        getPrintStream().print(s);
    }

    @Override
    public void print(Object obj) {
        getPrintStream().print(obj);
    }

    @Override
    public void println() {
        getPrintStream().println();
    }

    @Override
    public void println(boolean x) {
        getPrintStream().println(x);
    }

    @Override
    public void println(char x) {
        getPrintStream().println(x);
    }

    @Override
    public void println(int x) {
        getPrintStream().println(x);
    }

    @Override
    public void println(long x) {
        getPrintStream().println(x);
    }

    @Override
    public void println(float x) {
        getPrintStream().println(x);
    }

    @Override
    public void println(double x) {
        getPrintStream().println(x);
    }

    @Override
    public void println(char[] x) {
        getPrintStream().println(x);
    }

    @Override
    public void println(String x) {
        getPrintStream().println(x);
    }

    @Override
    public void println(Object x) {
        getPrintStream().println(x);
    }
}
