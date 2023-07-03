package com.piaskowy;

import com.piaskowy.commandLineArgument.Option;

public class Options {
    @Option(name = "change", required = true, hasValue = true)
    String option;

    public String getOption() {
        return option;
    }

    public void setOption(final String option) {
        this.option = option;
    }
}
