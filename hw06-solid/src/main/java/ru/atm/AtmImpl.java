package ru.atm;

import ru.atm.banknote.Banknote;
import ru.atm.service.Atm;

import java.util.List;

public class AtmImpl implements Atm {

    private final Atm atmService;

    public AtmImpl(Atm atmService) {
        this.atmService = atmService;
    }

    @Override
    public void deposit(List<Banknote> banknotes) {
        atmService.deposit(banknotes);
    }

    @Override
    public List<Banknote> withdraw(int amount)  {
        return atmService.withdraw(amount);
    }

    @Override
    public int getBalance() {
        return atmService.getBalance();
    }
}
