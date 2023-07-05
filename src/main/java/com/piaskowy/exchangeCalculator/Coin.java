package com.piaskowy.exchangeCalculator;

public record Coin(String name, int nominalInGr) implements Comparable<Coin> {
    public Coin {
        if (nominalInGr <= 0) {
            throw new IllegalArgumentException("Nominal must be positive");
        }
    }

    @Override
    public int compareTo(Coin other) {
        return Integer.compare(nominalInGr, other.nominalInGr);
    }
}