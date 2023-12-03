package ru.atm.banknote;

import java.util.Objects;

public class BanknoteImpl implements Banknote {

    private final Denomination denomination;

    public BanknoteImpl(Denomination denomination) {
        this.denomination = denomination;
    }

    @Override
    public Denomination getDenomination() {
        return denomination;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof BanknoteImpl other)) {
            return false;
        }
        return this.denomination.getValue() == other.denomination.getValue();
    }

    @Override
    public int hashCode() {
        return Objects.hash(denomination.getValue());
    }
}
