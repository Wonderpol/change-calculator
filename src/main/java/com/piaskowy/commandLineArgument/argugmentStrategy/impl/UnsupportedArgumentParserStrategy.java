package com.piaskowy.commandLineArgument.argugmentStrategy.impl;

import com.piaskowy.commandLineArgument.argugmentStrategy.ArgumentParserStrategy;
import com.piaskowy.commandLineArgument.exception.InvalidOptionException;

import java.lang.reflect.Field;

public class UnsupportedArgumentParserStrategy implements ArgumentParserStrategy {
    @Override
    public void parse(final String key, final String value, final Object obj, final Field field) throws InvalidOptionException {
        throw new InvalidOptionException("Unsupported argument type: " + field.getType());
    }
}
