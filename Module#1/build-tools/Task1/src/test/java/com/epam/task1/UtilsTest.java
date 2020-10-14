package com.epam.task1;

import org.junit.Test;

public class UtilsTest {
    @Test(expected=IllegalArgumentException.class)
    public void isPositiveNumberTest_NOT_A_NUMBER_PARAMETER(){
        String NOT_A_NUMBER_PARAMETER = "NOT_A_NUMBER_PARAMETER";
        Utils.isPositiveNumber(NOT_A_NUMBER_PARAMETER);
    }
}
