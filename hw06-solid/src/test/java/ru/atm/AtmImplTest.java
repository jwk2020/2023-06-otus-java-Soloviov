package ru.atm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.atm.banknote.BanknoteImpl;
import ru.atm.banknote.Denomination;
import ru.atm.exception.ImpossibleWithdrawAmountException;
import ru.atm.service.Atm;
import ru.atm.service.AtmService;
import ru.atm.storage.CellStorage;

import static org.junit.jupiter.api.Assertions.*;


class AtmImplTest extends CommonTest {

    private static final int INITIAL_BALANCE = 9700;
    private static final int SUCCESS_WITHDRAW_AMOUNT = 1100;
    private static final int WRONG_WITHDRAW_AMOUNT = 1;

    private Atm atm;

    @BeforeEach
    void setUp() {
        atm = new AtmImpl(new AtmService(new CellStorage(Denomination.values())));
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
                new BanknoteImpl(Denomination.FIFTY),
                new BanknoteImpl(Denomination.ONE_HUNDRED),
                new BanknoteImpl(Denomination.TWO_HUNDRED),
                new BanknoteImpl(Denomination.FIVE_HUNDRED),
                new BanknoteImpl(Denomination.ONE_THOUSAND),
                new BanknoteImpl(Denomination.TWO_THOUSAND),
                new BanknoteImpl(Denomination.FIVE_THOUSAND)
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
                new BanknoteImpl(Denomination.ONE_THOUSAND),
                new BanknoteImpl(Denomination.ONE_HUNDRED)
        );
        assertIterableEquals(expectedBanknotes, actualBanknotes);
        assertEquals(initialBalance - getBanknotesSum(expectedBanknotes), atm.getBalance());
    }

    @Test
    @DisplayName("Возврат ошибки, если сумму выдать нельзя")
    void shouldThrowImpossibleWithdrawAmountExceptionTest() {
        assertThrows(ImpossibleWithdrawAmountException.class, () -> {
            atm.withdraw(WRONG_WITHDRAW_AMOUNT);
        });
    }

    private void depositInitialBanknotes() {
        atm.deposit(makeInitialBanknoteList());
    }
}
