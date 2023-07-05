package com.piaskowy.commandLineArgument;

import com.piaskowy.Options;
import com.piaskowy.commandLineArgument.exception.BadOptionUsageException;
import com.piaskowy.commandLineArgument.exception.BadOptionValueTypeException;
import com.piaskowy.commandLineArgument.exception.CanNotSetOptionValueException;
import com.piaskowy.commandLineArgument.exception.MissingRequiredOptionException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

public class ArgsParser {
    public static void parse(Options options, String[] args) {
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            try {
                Field[] fields = options.getClass().getDeclaredFields();
                for (Field field : fields) {
                    Option optionAnnotation = field.getAnnotation(Option.class);
                    if (optionAnnotation != null && arg.equals(optionAnnotation.name())) {
                        Class<?> fieldType = field.getType();
                        field.setAccessible(true);
                        if (fieldType.equals(String.class)) {
                            field.set(options, args[i + 1]);
                            i++;
                        } else if (fieldType.equals(List.class)) {
                            List<String> values = new ArrayList<>();
                            for (int j = i + 1; j < args.length; j++) {
                                String fileArg = args[j];
                                if (fileArg.startsWith("-")) {
                                    break;
                                }
                                values.add(fileArg);
                                i++;
                            }

                            Class<?> genericType = (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
                            List<Object> convertedValues = new ArrayList<>();
                            for (String value : values) {
                                Object convertedValue = convertToType(value, genericType);
                                if (convertedValue == null) {
                                    throw new BadOptionValueTypeException("Niepoprawny typ wartości dla argumentu: " + optionAnnotation.name() + ", wymagany: " + genericType.getSimpleName());
                                }
                                convertedValues.add(convertedValue);
                            }
                            field.set(options, convertedValues);
                        }
                        break;
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new BadOptionUsageException("Bad option usage");
            } catch (IllegalAccessException e) {
                throw new CanNotSetOptionValueException("Can not set option value.");
            }
        }
        checkRequiredOptions(options);
    }

    private static void checkRequiredOptions(Options options) {
        Field[] fields = options.getClass().getDeclaredFields();
        for (Field field : fields) {
            Option optionAnnotation = field.getAnnotation(Option.class);
            if (optionAnnotation != null && optionAnnotation.required()) {
                try {
                    field.setAccessible(true);
                    Object value = field.get(options);
                    if (value == null) {
                        throw new MissingRequiredOptionException("Missing required option: " + optionAnnotation.name() + ". Description: " + optionAnnotation.description());
                    }
                } catch (IllegalAccessException e) {
                    throw new IllegalArgumentException("Can not access option value.");
                }
            }
        }
    }


    private static Object convertToType(String value, Class<?> targetType) {
        try {
            Constructor<?> constructor = targetType.getConstructor(String.class);
            return constructor.newInstance(value);
        } catch (Exception e) {
            return null;
        }
    }
}