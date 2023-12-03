package ru.atm.storage;

import ru.atm.banknote.Banknote;

import java.util.List;

public interface Cell {

    void add(Banknote banknote);

    List<Banknote> remove(int count);

    int getCount();

}
