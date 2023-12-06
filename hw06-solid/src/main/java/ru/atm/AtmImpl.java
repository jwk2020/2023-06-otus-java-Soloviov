package ru.atm;

import ru.atm.banknote.Banknote;
import ru.atm.storage.Storage;

import java.util.List;

public class AtmImpl implements Atm {

    private final Storage storage;

    public AtmImpl(Storage storage) {
        this.storage = storage;
    }

    @Override
    public void deposit(List<Banknote> banknotes) {
        storage.deposit(banknotes);
    }

    @Override
    public List<Banknote> withdraw(int amount) {
        return storage.withdraw(amount);
    }

    @Override
    public int getBalance() {
        return storage.getBalance();
    }
}
