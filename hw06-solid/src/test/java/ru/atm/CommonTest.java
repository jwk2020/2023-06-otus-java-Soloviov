package ru.atm;

import ru.atm.banknote.Banknote;
import ru.atm.banknote.Denomination;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public abstract class CommonTest {

    public List<Banknote> makeInitialBanknoteList() {
        return makeBanknoteList(
                new Banknote(Denomination.FIFTY),
                new Banknote(Denomination.FIFTY),
                new Banknote(Denomination.ONE_HUNDRED),
                new Banknote(Denomination.ONE_HUNDRED),
                new Banknote(Denomination.TWO_HUNDRED),
                new Banknote(Denomination.TWO_HUNDRED),
                new Banknote(Denomination.FIVE_HUNDRED),
                new Banknote(Denomination.FIVE_HUNDRED),
                new Banknote(Denomination.ONE_THOUSAND),
                new Banknote(Denomination.TWO_THOUSAND),
                new Banknote(Denomination.FIVE_THOUSAND)
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

    public static void assertBanknoteList(List<Banknote> expected, List<Banknote> actual) {
        assertArrayEquals(expected.stream()
                        .mapToInt(banknote -> banknote.getDenomination().getValue())
                        .toArray(),
                actual.stream()
                        .mapToInt(banknote -> banknote.getDenomination().getValue())
                        .toArray());
    }
}
