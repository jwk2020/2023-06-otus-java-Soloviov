package ru.atm.storage;


import ru.atm.banknote.Banknote;
import ru.atm.banknote.Denomination;
import ru.atm.exception.ImpossibleWithdrawAmountException;

import java.util.*;

import static java.util.stream.Collectors.toMap;

public class CellStorage implements Storage {

    private Map<Denomination, Cell> cellHolder = new HashMap<>();

    public CellStorage(Denomination[] denominations) {
        init(denominations);
    }

    @Override
    public void deposit(List<Banknote> banknotes) {
        for (var banknote : banknotes) {
            cellHolder.get(banknote.getDenomination()).add(banknote);
        }
    }

    @Override
    public List<Banknote> withdraw(int amount) {
        List<Banknote> result = new ArrayList<>();
        var denominations = Arrays.asList(Denomination.values());
        denominations.sort(Collections.reverseOrder());
        for (var denomination : denominations) {
            int availableCount = cellHolder.get(denomination).getCount();
            int removeCount = Math.min(availableCount, amount / denomination.getValue());
            result.addAll(cellHolder.get(denomination).remove(removeCount));
            amount -= removeCount * denomination.getValue();
            if (amount == 0) {
                return result;
            }
        }
        deposit(result);
        throw new ImpossibleWithdrawAmountException("Unable to withdraw the requested amount");
    }

    @Override
    public int getBalance() {
        int result = 0;
        for (var denomination : Denomination.values()) {
            result += denomination.getValue() * cellHolder.get(denomination).getCount();
        }
        return result;
    }

    private void init(Denomination[] denominations) {
        cellHolder = Arrays.stream(denominations)
                .collect(toMap(v -> v, v -> new BanknoteCell()));
    }
}
