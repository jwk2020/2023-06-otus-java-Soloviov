package ru.atm.storage;

import ru.atm.banknote.Denomination;

public interface Storage {

    Cell getCell(Denomination denomination);

}
