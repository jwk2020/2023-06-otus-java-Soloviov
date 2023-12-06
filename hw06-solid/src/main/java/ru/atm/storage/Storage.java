package ru.atm.storage;

import ru.atm.banknote.Banknote;

import java.util.List;

public interface Storage {

    void deposit(List<Banknote> banknotes);

    List<Banknote> withdraw(int amount);

    int getBalance();

}
