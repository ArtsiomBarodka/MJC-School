package com.epam.task2;

import org.apache.commons.lang3.math.NumberUtils;

import java.util.Objects;

public final class Utils {
    private static final String NOT_A_NUMBER_PARAMETERS_EXCEPTION = "Not a number passed to method parameters.";


    public static boolean isPositiveNumber(String str){
        if (!NumberUtils.isCreatable(str)) throw new IllegalArgumentException(NOT_A_NUMBER_PARAMETERS_EXCEPTION);

        return NumberUtils.toDouble(str) > 0;
    }

    public static boolean isAllPositiveNumbers(String ...str){
        if(Objects.isNull(str)) throw new IllegalArgumentException(NOT_A_NUMBER_PARAMETERS_EXCEPTION);

        for (String aStr : str) {
            if (!isPositiveNumber(aStr)) {
                return false;
            }
        }
        return true;
    }

    private Utils(){

    }
}
