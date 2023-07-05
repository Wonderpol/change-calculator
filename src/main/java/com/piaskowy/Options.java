package com.piaskowy;

import com.piaskowy.commandLineArgument.Option;

import java.util.List;

public class Options {
    @Option(name = "--change", type = List.class, description = "Opcja jest wymagana", required = true)
    public List<Double> files;
}