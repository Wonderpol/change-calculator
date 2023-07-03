package com.piaskowy.commandLineArgument;

import com.piaskowy.commandLineArgument.exception.InvalidOptionException;
import com.piaskowy.commandLineArgument.exception.InvalidOptionValueException;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;

public class CommandLineParser {
    private Map<String, String> options;

    public CommandLineParser() {
        this.options = new HashMap<>();
    }

    public void parse(Object obj, String... args) throws IllegalStateException, IllegalAccessException {
        Class<?> clazz = obj.getClass();

        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Option.class)) {
                Option option = field.getAnnotation(Option.class);
                String key = option.name();
                String value = option.defaultValue();

                if (args != null) {
                    for (int i = 0; i < args.length; i++) {
                        if (args[i].equals(key)) {
                            if (option.hasValue() && i + 1 < args.length) {
                                value = args[i + 1];
                            } else {
                                throw new InvalidOptionException(format("Option %s requires value", key));
                            }
                        }
                    }
                }

                validateOptionValue(option, value);

                field.setAccessible(true);
                setOptionValue(field, obj, value);
            }
        }
        checkRequiredOptions(clazz);
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

    private void setOptionValue(Field field, Object obj, String value) throws IllegalAccessException {
        field.setAccessible(true);

        Class<?> fieldType = field.getType();
        if (fieldType == String.class) {
            field.set(obj, value);
        } else if (fieldType == int.class) {
            field.setInt(obj, Integer.parseInt(value));
        } else if (fieldType == double.class) {
            field.setDouble(obj, Double.parseDouble(value));
        } else if (fieldType == String[].class) {
            field.set(obj, value.split(","));
        }
    }

    private void checkRequiredOptions(Class<?> clazz) throws InvalidOptionException {
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Option.class)) {
                Option option = field.getAnnotation(Option.class);
                if (option.required() && !options.containsKey(option.name())) {
                    throw new InvalidOptionException("Required option missing: " + option.name());
                }
            }
        }
    }
}
