package com.piaskowy;

import com.piaskowy.commandLineArgument.ArgsParser;
import com.piaskowy.exchangeCalculator.Coin;
import com.piaskowy.exchangeCalculator.ExchangeCalculator;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        final Map<Coin, Integer> cashRegisterMoney = new HashMap<>();

        cashRegisterMoney.put(new Coin("5 zł", 500), 1);
        cashRegisterMoney.put(new Coin("2 zł", 200), 3);
        cashRegisterMoney.put(new Coin("1 zł", 100), 5);
        cashRegisterMoney.put(new Coin("50 gr", 50), 10);
        cashRegisterMoney.put(new Coin("20 gr", 20), 20);
        cashRegisterMoney.put(new Coin("10 gr", 10), 200);
        cashRegisterMoney.put(new Coin("5 gr", 5), 100);
        cashRegisterMoney.put(new Coin("2 gr", 2), 100);
        cashRegisterMoney.put(new Coin("1 gr", 1), 10000);

        ExchangeCalculator pettyCashRegister = new ExchangeCalculator(cashRegisterMoney);
        final Options options = new Options();

        try {
            ArgsParser.parse(options, args);
        } catch (RuntimeException e) {
            System.out.println(e.getLocalizedMessage());
            return;
        }

        options.changes.forEach(val -> {
            try {
                pettyCashRegister.giveChange(val);
            } catch (RuntimeException e) {
                System.out.println(e.getLocalizedMessage() + "\n");
            }
        });

    }
}
