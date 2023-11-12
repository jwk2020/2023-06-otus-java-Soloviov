package ru.autologger;

public class Demo {

    public static void main(String[] args) {
        TestLoggingInterface testLogging = Ioc.createTestLoggingClass();
        testLogging.calculation(6);
        testLogging.calculation(6, 7);
        testLogging.calculation(6, 7, "8");
    }
}