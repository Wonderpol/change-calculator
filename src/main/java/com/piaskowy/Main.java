package com.piaskowy;

import com.piaskowy.commandLineArgument.ArgsParser;

public class Main {
    public static void main(String[] args) {
        Options options = new Options();

        try {
            ArgsParser.parse(options, args);
        } catch (RuntimeException e) {
            System.out.println(e.getLocalizedMessage());
        }

        // Przykładowe użycie odczytanych opcji
        System.out.println("Files: " + options.files);
    }
}