package com.piaskowy;

import com.piaskowy.cashRegister.CashRegister;
import com.piaskowy.commandLineArgument.ArgsParser;

public class Main {
    public static void main(String[] args) {
        CashRegister pettyCashRegister = new CashRegister();
        final Options options = new Options();
        ArgsParser.parse(options, args);

        options.files.forEach(val -> {
            try {
                pettyCashRegister.giveChange(val);
            } catch (RuntimeException e) {
                System.out.println(e.getLocalizedMessage() + "\n");
            }
        });

    }
}
