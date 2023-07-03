package com.piaskowy.commandLineArgument.argugmentStrategy;

import com.piaskowy.commandLineArgument.argugmentStrategy.impl.IntegerArgumentParserStrategy;
import com.piaskowy.commandLineArgument.argugmentStrategy.impl.StringArgumentParserStrategy;
import com.piaskowy.commandLineArgument.argugmentStrategy.impl.UnsupportedArgumentParserStrategy;

import java.lang.reflect.Field;

public class ArgumentParserStrategyFactory {
    public ArgumentParserStrategy getStrategyForField(Field field) {
        Class<?> fieldType = field.getType();

        if (fieldType == String.class) {
            return new StringArgumentParserStrategy();
        } else if (fieldType == int.class || fieldType == Integer.class) {
            return new IntegerArgumentParserStrategy();
        }

        return new UnsupportedArgumentParserStrategy();
    }
}
