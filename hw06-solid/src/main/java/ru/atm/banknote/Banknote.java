package ru.atm.banknote;

public class Banknote {

    private final Denomination denomination;

    public Banknote(Denomination denomination) {
        this.denomination = denomination;
    }

    public Denomination getDenomination() {
        return denomination;
    }
}
