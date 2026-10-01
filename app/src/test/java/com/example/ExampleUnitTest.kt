package com.example

import com.example.calculator.CalculatorEngine
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun testShowcaseExpression() {
        val result = CalculatorEngine.evaluate("(62 × 25) + 471")
        assertTrue(result.isSuccess)
        assertEquals("2021", result.getOrNull())
    }

    @Test
    fun testBasicArithmetic() {
        assertEquals("4", CalculatorEngine.evaluate("2 + 2").getOrNull())
        assertEquals("10", CalculatorEngine.evaluate("5 × 2").getOrNull())
        assertEquals("2.5", CalculatorEngine.evaluate("5 ÷ 2").getOrNull())
        assertEquals("3", CalculatorEngine.evaluate("7 − 4").getOrNull())
    }

    @Test
    fun testOperatorPrecedence() {
        assertEquals("14", CalculatorEngine.evaluate("2 + 3 × 4").getOrNull())
        assertEquals("20", CalculatorEngine.evaluate("(2 + 3) × 4").getOrNull())
    }

    @Test
    fun testTrigonometricFunctions() {
        // In degrees (isRadian = false)
        assertEquals("0.5", CalculatorEngine.evaluate("sin(30)", isRadian = false).getOrNull())
        assertEquals("1", CalculatorEngine.evaluate("sin(90)", isRadian = false).getOrNull())
        assertEquals("1", CalculatorEngine.evaluate("cos(0)", isRadian = false).getOrNull())
        assertEquals("0", CalculatorEngine.evaluate("cos(90)", isRadian = false).getOrNull())
        assertEquals("1", CalculatorEngine.evaluate("tan(45)", isRadian = false).getOrNull())
    }

    @Test
    fun testLogarithmicFunctions() {
        assertEquals("2", CalculatorEngine.evaluate("log(100)").getOrNull())
        assertEquals("1", CalculatorEngine.evaluate("log(10)").getOrNull())
        assertEquals("0", CalculatorEngine.evaluate("ln(1)").getOrNull())
    }

    @Test
    fun testRootsAndPowers() {
        assertEquals("4", CalculatorEngine.evaluate("√(16)").getOrNull())
        assertEquals("8", CalculatorEngine.evaluate("2 ^ 3").getOrNull())
        assertEquals("25", CalculatorEngine.evaluate("5 ^ 2").getOrNull())
    }

    @Test
    fun testConstantsAndFactorial() {
        assertEquals("120", CalculatorEngine.evaluate("5!").getOrNull())
        val piRes = CalculatorEngine.evaluate("π").getOrNull()
        assertTrue(piRes?.startsWith("3.1415") == true)
    }
}
