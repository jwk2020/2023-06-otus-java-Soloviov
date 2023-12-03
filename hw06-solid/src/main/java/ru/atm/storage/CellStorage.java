package ru.atm.storage;


import ru.atm.banknote.Denomination;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

public class CellStorage implements Storage {

    private Map<Denomination, Cell> cellHolder = new HashMap<>();

    public CellStorage(Denomination[] denominations) {
        init(denominations);
    }

    @Override
    public Cell getCell(Denomination denomination) {
        return cellHolder.get(denomination);
    }

    private void init(Denomination[] denominations) {
        cellHolder = Arrays.stream(denominations)
                .collect(toMap(v -> v, v -> new BanknoteCell()));
    }
}
