package com.unvise.transportbook.utils;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParserTest {

    @Test
    void parseByAvailabilityTest() {
        String[] arrThatReturnTrue = {"Yes", "yes ","true ", "True", "tRue"};
        String[] arrThatReturnFalse = {"nO", "   no ", "No", "0", "1", "faLse"};

        Arrays.stream(arrThatReturnTrue).forEach(v -> assertTrue(Parser.parseByAvailability(v)));
        Arrays.stream(arrThatReturnFalse).forEach(v -> assertFalse(Parser.parseByAvailability((v))));
    }

}
