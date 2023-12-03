package ru.atm.service;

import ru.atm.banknote.Banknote;
import ru.atm.banknote.Denomination;
import ru.atm.exception.ImpossibleWithdrawAmountException;
import ru.atm.storage.Storage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AtmService implements Atm {

    private final Storage storage;

    public AtmService(Storage storage) {
        this.storage = storage;
    }

    @Override
    public void deposit(List<Banknote> banknotes) {
        for (var banknote : banknotes) {
            storage.getCell(banknote.getDenomination()).add(banknote);
        }
    }

    @Override
    public List<Banknote> withdraw(int amount) {
        List<Banknote> result = new ArrayList<>();
        var denominations = Arrays.asList(Denomination.values());
        denominations.sort(Collections.reverseOrder());
        for (var denomination : denominations) {
            int availableCount = storage.getCell(denomination).getCount();
            int removeCount = Math.min(availableCount, amount / denomination.getValue());
            result.addAll(storage.getCell(denomination).remove(removeCount));
            amount -= removeCount * denomination.getValue();
            if (amount == 0) {
                return result;
            }
        }
        throw new ImpossibleWithdrawAmountException("Unable to withdraw the requested amount");
    }

    public int getBalance() {
        int result = 0;
        for (var denomination : Denomination.values()) {
            result += denomination.getValue() * storage.getCell(denomination).getCount();
        }
        return result;
    }
}
