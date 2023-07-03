package com.piaskowy.commandLineArgument.argugmentStrategy.impl;

import com.piaskowy.commandLineArgument.argugmentStrategy.ArgumentParserStrategy;
import com.piaskowy.commandLineArgument.exception.InvalidOptionException;

import java.lang.reflect.Field;

public class IntegerArgumentParserStrategy implements ArgumentParserStrategy {
    @Override
    public void parse(final String key, final String value, final Object obj, final Field field) throws InvalidOptionException {
        try {
            field.setAccessible(true);
            field.setInt(obj, Integer.parseInt(value));
        } catch (IllegalAccessException | NumberFormatException e) {
            throw new InvalidOptionException("Error setting value for option: " + key);
        }
    }
}
