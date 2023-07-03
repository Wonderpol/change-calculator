package com.piaskowy.commandLineArgument.argugmentStrategy;

import com.piaskowy.commandLineArgument.exception.InvalidOptionException;

import java.lang.reflect.Field;

public interface ArgumentParserStrategy {
    void parse(String key, String value, Object obj, Field field) throws InvalidOptionException;
}
