package com.epam.task1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UtilsTest {

    @Test
    void isPositiveNumberTest_NOT_A_NUMBER_PARAMETER(){
        String NOT_A_NUMBER_PARAMETER = "NOT_A_NUMBER_PARAMETER";

        assertThrows(IllegalArgumentException.class, () -> {
            Utils.isPositiveNumber(NOT_A_NUMBER_PARAMETER) ;
        });
    }

    @Test
    void isPositiveNumberTest_POSITIVE_DOUBLE_NUMBER_PARAMETER(){
        String POSITIVE_DOUBLE_NUMBER = "22.3";

        assertTrue(Utils.isPositiveNumber(POSITIVE_DOUBLE_NUMBER));
    }
}
