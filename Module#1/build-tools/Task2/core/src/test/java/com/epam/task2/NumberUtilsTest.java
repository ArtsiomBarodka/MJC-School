package com.epam.task2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NumberUtilsTest {

    @Test
    void isAllPositiveNumbersTest_NOT_ALL_POSITIVE_NUMBER_PARAMETER(){
        String number1 = "22.2";
        String number2 = "-23";
        String number3 = "43";

        boolean result = NumberUtils.isAllPositiveNumbers(number1, number2, number3);
        assertFalse(result);
    }

    @Test
    void isAllPositiveNumbersTest_ALL_POSITIVE_NUMBER_PARAMETER(){
        String number1 = "22.2";
        String number2 = "23";
        String number3 = "43";

        boolean result = NumberUtils.isAllPositiveNumbers(number1, number2, number3);
        assertTrue(result);
    }
}
