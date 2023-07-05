package com.piaskowy;

import com.piaskowy.commandLineArgument.Option;

import java.util.List;

public class Options {
    @Option(name = "--files", type = List.class, description = "Opcja jest wymagana", required = true)
    List<Double> files;
}