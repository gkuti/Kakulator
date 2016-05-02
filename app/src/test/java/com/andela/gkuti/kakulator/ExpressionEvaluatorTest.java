package com.andela.gkuti.kakulator;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ExpressionEvaluatorTest {
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testEvaluate() throws Exception {
        double result = ExpressionEvaluator.evaluate("5 * 5 - 2 * 5 + 3");
        assertEquals(18.0, result, 0);
        double result2 = ExpressionEvaluator.evaluate("5 + 5 - 15 / 5 + 3");
        assertEquals(10.0, result2, 0);
        double result3 = ExpressionEvaluator.evaluate("8 * 6 - 49 / 7 + 3 * 6 - 13 + 9 / 3");
        assertEquals(49.0, result3, 0);
    }
}