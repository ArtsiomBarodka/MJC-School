package com.epam.task1;

import org.apache.commons.lang3.math.NumberUtils;

public final class Utils {
    private static final String NOT_A_NUMBER_PARAMETERS_EXCEPTION = "Not a number passed to method parameters.";

    public static boolean isPositiveNumber(String str){
        if (!NumberUtils.isCreatable(str)) throw new IllegalArgumentException(NOT_A_NUMBER_PARAMETERS_EXCEPTION);

        return NumberUtils.toDouble(str) > 0;
    }

    private Utils(){}
}
