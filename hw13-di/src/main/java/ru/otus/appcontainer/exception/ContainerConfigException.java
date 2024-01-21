package ru.otus.appcontainer.exception;

public class ContainerConfigException extends RuntimeException {

    public ContainerConfigException(String message) {
        super(message);
    }

    public ContainerConfigException(String message, Throwable cause) {
        super(message, cause);
    }
}