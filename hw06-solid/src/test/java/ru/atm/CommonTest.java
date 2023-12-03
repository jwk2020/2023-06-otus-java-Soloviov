package ru.atm;

import ru.atm.banknote.Banknote;
import ru.atm.banknote.BanknoteImpl;
import ru.atm.banknote.Denomination;

import java.util.Arrays;
import java.util.List;

public abstract class CommonTest {

    public List<Banknote> makeInitialBanknoteList() {
        return makeBanknoteList(
                new BanknoteImpl(Denomination.FIFTY),
                new BanknoteImpl(Denomination.FIFTY),
                new BanknoteImpl(Denomination.ONE_HUNDRED),
                new BanknoteImpl(Denomination.ONE_HUNDRED),
                new BanknoteImpl(Denomination.TWO_HUNDRED),
                new BanknoteImpl(Denomination.TWO_HUNDRED),
                new BanknoteImpl(Denomination.FIVE_HUNDRED),
                new BanknoteImpl(Denomination.FIVE_HUNDRED),
                new BanknoteImpl(Denomination.ONE_THOUSAND),
                new BanknoteImpl(Denomination.TWO_THOUSAND),
                new BanknoteImpl(Denomination.FIVE_THOUSAND)
        );
    }

    public List<Banknote> makeBanknoteList(Banknote... banknotes) {
        return Arrays.asList(banknotes);
    }

    public int getBanknotesSum(List<Banknote> banknotes) {
        return banknotes.stream()
                .mapToInt(banknote -> banknote.getDenomination().getValue())
                .sum();
    }
}
