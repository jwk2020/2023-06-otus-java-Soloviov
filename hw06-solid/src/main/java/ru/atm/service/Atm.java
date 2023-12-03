package ru.atm.service;

import ru.atm.banknote.Banknote;

import java.util.List;

public interface Atm {

    void deposit(List<Banknote> banknotes);

    List<Banknote> withdraw(int amount);

    int getBalance();

}
