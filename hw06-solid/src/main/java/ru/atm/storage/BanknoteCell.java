package ru.atm.storage;

import ru.atm.banknote.Banknote;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class BanknoteCell implements Cell {

    private final Deque<Banknote> banknotes = new LinkedList<>();

    @Override
    public void add(Banknote banknote) {
        banknotes.add(banknote);
    }

    @Override
    public List<Banknote> remove(int count) {
        List<Banknote> result = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            result.add(banknotes.poll());
        }
        return result;
    }

    @Override
    public int getCount() {
        return banknotes.size();
    }
}
