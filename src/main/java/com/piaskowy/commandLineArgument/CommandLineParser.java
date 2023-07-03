package com.piaskowy.commandLineArgument;

import com.piaskowy.commandLineArgument.argugmentStrategy.ArgumentParserStrategy;
import com.piaskowy.commandLineArgument.argugmentStrategy.ArgumentParserStrategyFactory;
import com.piaskowy.commandLineArgument.exception.InvalidOptionException;
import com.piaskowy.commandLineArgument.exception.InvalidOptionValueException;
import com.piaskowy.commandLineArgument.exception.RequiredOptionMissingException;

import java.lang.reflect.Field;
import java.util.Arrays;

import static java.lang.String.format;

public class CommandLineParser {
    private final ArgumentParserStrategyFactory strategyFactory;

    public CommandLineParser() {
        this.strategyFactory = new ArgumentParserStrategyFactory();
    }

    public void parse(Object obj, String... args) throws IllegalStateException, IllegalAccessException {
        Class<?> clazz = obj.getClass();

        for (int i = 0; i < args.length; i++) {
            String arg = args[i];

            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(Option.class)) {
                    Option option = field.getAnnotation(Option.class);
                    String key = option.name();
                    String value = option.defaultValue();

                    if (arg.startsWith(key)) {
                        if (option.hasValue() && arg.length() > key.length()) {
                            value = arg.substring(key.length() + 1);
                        } else if (option.hasValue() && i + 1 < args.length) {
                            value = args[i + 1];
                            i++;
                        } else {
                            throw new InvalidOptionException(format("Option %s requires value", key));
                        }
                        validateOptionValue(option, value);
                        field.setAccessible(true);
                        ArgumentParserStrategy strategy = strategyFactory.getStrategyForField(field);
                        strategy.parse(key, value, obj, field);
                    }
                }
            }
        }
        checkRequiredOptions(obj);
    }

    private void validateOptionValue(Option option, String value) {
        if (option.validValues().length > 0 && !Arrays.asList(option.validValues()).contains(value)) {
            throw new InvalidOptionValueException("Invalid value for option " + option.name() + ": " + value);
        }

        if (option.minValue() != Double.MIN_VALUE && Double.parseDouble(value) < option.minValue()) {
            throw new InvalidOptionValueException("Value for option " + option.name() + " is below the minimum allowed: " + value);
        }

        if (option.maxValue() != Double.MAX_VALUE && Double.parseDouble(value) > option.maxValue()) {
            throw new InvalidOptionValueException("Value for option " + option.name() + " exceeds the maximum allowed: " + value);
        }
    }

    private void checkRequiredOptions(Object obj) throws IllegalAccessException {
        for (Field field : obj.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Option.class)) {
                Option option = field.getAnnotation(Option.class);
                boolean isRequired = option.required();
                field.setAccessible(true);

                if (isRequired && field.get(obj) == null) {
                    String optionName = option.name();
                    throw new RequiredOptionMissingException(format("Required option %s is missing", optionName));
                }
            }
        }
    }
}
