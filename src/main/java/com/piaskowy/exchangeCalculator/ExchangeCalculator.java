package com.piaskowy.exchangeCalculator;

import com.piaskowy.exchangeCalculator.exception.CanNotGiveExactChangeException;
import com.piaskowy.exchangeCalculator.exception.NotEnoughMoneyException;

import java.util.*;

public class ExchangeCalculator {
    private final Map<Coin, Integer> cashRegister;

    public ExchangeCalculator(final Map<Coin, Integer> cashRegister) {
        this.cashRegister = cashRegister;
    }

    public void giveChange(double amount) {
        System.out.println("Dla reszty " + amount + " zł:");
        int changeInGr = (int) (amount * 100);

        List<Coin> availableCoins = new ArrayList<>(cashRegister.keySet());
        Collections.sort(availableCoins, Collections.reverseOrder());

        if (changeInGr > getTotalCashInGr() || !canMakeChange(changeInGr)) {
            throw new NotEnoughMoneyException("Brak wystarczającej liczby pieniędzy");
        }

        List<Coin> changeCoins = new ArrayList<>();

        for (Coin coin : availableCoins) {
            int quantity = cashRegister.get(coin);
            int nominalInGr = coin.nominalInGr();

            if (nominalInGr <= changeInGr && quantity > 0) {
                int quantityToGive = Math.min(changeInGr / nominalInGr, quantity);

                changeInGr -= quantityToGive * nominalInGr;
                quantity -= quantityToGive;

                cashRegister.put(coin, quantity);
                for (int i = 0; i < quantityToGive; i++) {
                    changeCoins.add(coin);
                }

                if (changeInGr == 0) {
                    System.out.println(formatChangeCoins(changeCoins));
                    return;
                }
            }
        }
        throw new CanNotGiveExactChangeException("Z dostępnej kombinacji monet nie można uzyskac pelnej reszty");
    }


    private String formatChangeCoins(List<Coin> coins) {
        Map<String, Integer> coinCounts = new LinkedHashMap<>();


        for (Coin coin : coins) {
            coinCounts.put(coin.name(), coinCounts.getOrDefault(coin.name(), 0) + 1);
        }

        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, Integer> entry : coinCounts.entrySet()) {
            String coinName = entry.getKey();
            int coinCount = entry.getValue();

            sb.append("Wydaj ").append(coinCount).append(" monet ").append(coinName).append("\n");
        }

        return sb.toString();
    }

    private boolean canMakeChange(int amount) {
        for (Coin coin : cashRegister.keySet()) {
            int quantity = cashRegister.get(coin);
            int nominalInGr = coin.nominalInGr();
            if (nominalInGr <= amount && quantity > 0) {
                int numCoinsNeeded = amount / nominalInGr;
                if (numCoinsNeeded <= quantity) {
                    return true;
                }
            }
        }
        return false;
    }

    private int getTotalCashInGr() {
        int total = 0;
        for (Map.Entry<Coin, Integer> entry : cashRegister.entrySet()) {
            Coin coin = entry.getKey();
            int quantity = entry.getValue();
            total += coin.nominalInGr() * quantity;
        }
        return total;
    }


}
