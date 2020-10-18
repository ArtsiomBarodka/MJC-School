package com.epam.task2;

import com.epam.task1.Utils;
import java.util.Objects;

public final class NumberUtils {
    private static final String NOT_A_NUMBER_PARAMETERS_EXCEPTION = "Not a number passed to method parameters.";

    public static boolean isAllPositiveNumbers(String ...str){
        if(Objects.isNull(str)) throw new IllegalArgumentException(NOT_A_NUMBER_PARAMETERS_EXCEPTION);

        for (String aStr : str) {
            if (!Utils.isPositiveNumber(aStr)) {
                return false;
            }
        }
        return true;
    }

    private NumberUtils(){

    }
}
