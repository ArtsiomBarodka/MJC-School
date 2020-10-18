package com.epam.task2;

import org.junit.Assert;
import org.junit.Test;

public class UtilsTest {
    @Test(expected=IllegalArgumentException.class)
    public void isPositiveNumberTest_NOT_A_NUMBER_PARAMETER(){
        String NOT_A_NUMBER_PARAMETER = "NOT_A_NUMBER_PARAMETER";
        Utils.isPositiveNumber(NOT_A_NUMBER_PARAMETER);
    }

    @Test
    public void isAllPositiveNumbersTest_NOT_ALL_POSITIVE_NUMBER_PARAMETER(){
        String number1 = "22.2";
        String number2 = "-23";
        String number3 = "43";

        boolean result = Utils.isAllPositiveNumbers(number1, number2, number3);
        Assert.assertFalse(result);
    }
}
