package ru.atm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.atm.banknote.Banknote;
import ru.atm.banknote.Denomination;
import ru.atm.exception.ImpossibleWithdrawAmountException;
import ru.atm.storage.CellStorage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class AtmImplTest extends CommonTest {

    private static final int INITIAL_BALANCE = 9700;
    private static final int SUCCESS_WITHDRAW_AMOUNT = 1100;
    private static final int WRONG_WITHDRAW_AMOUNT = 101;

    private Atm atm;

    @BeforeEach
    void setUp() {
        atm = new AtmImpl(new CellStorage(Denomination.values()));
        depositInitialBanknotes();
    }

    @Test
    @DisplayName("Вывод суммы остатка денежных средств")
    void balanceTest() {
        assertEquals(INITIAL_BALANCE, atm.getBalance());
    }

    @Test
    @DisplayName("Внесение банкнот разных номиналов")
    void depositTest() {
        int initialBalance = atm.getBalance();
        var banknotes = makeBanknoteList(
                new Banknote(Denomination.FIFTY),
                new Banknote(Denomination.ONE_HUNDRED),
                new Banknote(Denomination.TWO_HUNDRED),
                new Banknote(Denomination.FIVE_HUNDRED),
                new Banknote(Denomination.ONE_THOUSAND),
                new Banknote(Denomination.TWO_THOUSAND),
                new Banknote(Denomination.FIVE_THOUSAND)
        );
        atm.deposit(banknotes);
        assertEquals(initialBalance + getBanknotesSum(banknotes), atm.getBalance());
    }

    @Test
    @DisplayName("Выдача запрошенной суммы минимальным количеством банкнот")
    void withdrawTest() {
        int initialBalance = atm.getBalance();
        var actualBanknotes = atm.withdraw(SUCCESS_WITHDRAW_AMOUNT);
        var expectedBanknotes = makeBanknoteList(
                new Banknote(Denomination.ONE_THOUSAND),
                new Banknote(Denomination.ONE_HUNDRED)
        );
        assertBanknoteList(expectedBanknotes, actualBanknotes);
        assertEquals(initialBalance - getBanknotesSum(expectedBanknotes), atm.getBalance());
    }

    @Test
    @DisplayName("Возврат ошибки, если сумму выдать нельзя")
    void shouldThrowImpossibleWithdrawAmountExceptionTest() {
        int initialBalance = atm.getBalance();
        assertThrows(ImpossibleWithdrawAmountException.class, () -> {
            atm.withdraw(WRONG_WITHDRAW_AMOUNT);
        });
        assertEquals(initialBalance, atm.getBalance());
    }

    private void depositInitialBanknotes() {
        atm.deposit(makeInitialBanknoteList());
    }
}
