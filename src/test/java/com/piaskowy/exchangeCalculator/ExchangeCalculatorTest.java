package com.piaskowy.exchangeCalculator;

import com.piaskowy.exchangeCalculator.exception.CanNotGiveExactChangeException;
import com.piaskowy.exchangeCalculator.exception.NotEnoughMoneyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class ExchangeCalculatorTest {
    private ExchangeCalculator cashRegister;

    @BeforeEach
    public void setUp() {

        final Map<Coin, Integer> cashRegisterMoney = new HashMap<>();

        cashRegisterMoney.put(new Coin("5 zł", 500), 1);
        cashRegisterMoney.put(new Coin("20 gr", 20), 1);
        cashRegisterMoney.put(new Coin("10 gr", 10), 1);
        cashRegisterMoney.put(new Coin("5 gr", 5), 1);

        cashRegister = new ExchangeCalculator(cashRegisterMoney);
    }

    @Test
    public void testGiveChange() {
        double amount1 = 5.35;
        String expectedChange1 = "Dla reszty 5.35 zł:\n" +
                "Wydaj 1 monet 5 zł\n" +
                "Wydaj 1 monet 20 gr\n" +
                "Wydaj 1 monet 10 gr\n" +
                "Wydaj 1 monet 5 gr\n\n";

        try {
            String otuput = getConsoleOutput(() -> cashRegister.giveChange(amount1));
            assertEquals(expectedChange1, otuput);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }

    }

    @Test
    public void testGiveChange_shouldThrowIfNotEnoughMoney() {
        double amount1 = 200000;
        try {
            assertThatThrownBy(() -> cashRegister.giveChange(amount1))
                    .isInstanceOf(NotEnoughMoneyException.class)
                    .hasMessageContaining("Brak wystarczającej liczby pieniędzy");
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }

    }

    @Test
    public void testGiveChange_shouldThrowCanNotGivenExactChangeException_ifCantCalculateFullChange() {
        double amount1 = 5.31;
        try {
            assertThatThrownBy(() -> cashRegister.giveChange(amount1))
                    .isInstanceOf(CanNotGiveExactChangeException.class)
                    .hasMessageContaining("Z dostępnej kombinacji monet nie można uzyskac pelnej reszty");
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }

    }


    private String getConsoleOutput(Runnable codeToExecute) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        try {
            System.setOut(new PrintStream(outputStream));
            codeToExecute.run();
            return outputStream.toString();
        } finally {
            System.setOut(originalOut);
        }
    }

}