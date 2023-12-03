package ru.atm.exception;

public class ImpossibleWithdrawAmountException extends RuntimeException {

    public ImpossibleWithdrawAmountException(String message) {
        super(message);
    }
}
