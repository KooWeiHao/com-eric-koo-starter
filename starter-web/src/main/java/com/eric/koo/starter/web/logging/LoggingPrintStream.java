package com.eric.koo.starter.web.logging;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import java.io.PrintStream;

@Log4j2
class LoggingPrintStream extends PrintStream {

    LoggingPrintStream() {
        super(System.out);
    }

    @Override
    public void print(boolean b) {
        log.debug(b);
    }

    @Override
    public void print(char c) {
        log.debug(c);
    }

    @Override
    public void print(int i) {
        log.debug(i);
    }

    @Override
    public void print(long l) {
        log.debug(l);
    }

    @Override
    public void print(float f) {
        log.debug(f);
    }

    @Override
    public void print(double d) {
        log.debug(d);
    }

    @Override
    public void print(char[] s) {
        log.debug(s);
    }

    @Override
    public void print(String s) {
        log.debug(s);
    }

    @Override
    public void print(Object obj) {
        log.debug(obj);
    }

    @Override
    public void println() {
        log.debug(StringUtils.EMPTY);
    }

    @Override
    public void println(boolean x) {
        log.debug(x);
    }

    @Override
    public void println(char x) {
        log.debug(x);
    }

    @Override
    public void println(int x) {
        log.debug(x);
    }

    @Override
    public void println(long x) {
        log.debug(x);
    }

    @Override
    public void println(float x) {
        super.println(x);
    }

    @Override
    public void println(double x) {
        log.debug(x);
    }

    @Override
    public void println(char[] x) {
        log.debug(x);
    }

    @Override
    public void println(String x) {
        log.debug(x);
    }

    @Override
    public void println(Object x) {
        log.debug(x);
    }
}
