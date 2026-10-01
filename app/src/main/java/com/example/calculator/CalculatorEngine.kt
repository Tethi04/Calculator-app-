package com.example.calculator

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale
import java.util.Stack
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.ln
import kotlin.math.log10
import kotlin.math.pow
import kotlin.math.round
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.tan

data class HistoryItem(
    val id: Long = System.currentTimeMillis(),
    val expression: String,
    val result: String,
    val timestamp: Long = System.currentTimeMillis()
)

object CalculatorEngine {

    private val decimalFormat: DecimalFormat = DecimalFormat("#,##0.########", DecimalFormatSymbols(Locale.US)).apply {
        isGroupingUsed = false
    }

    /**
     * Evaluates a mathematical expression string containing numbers, operators (+, −, ×, ÷, %, ^),
     * scientific functions (sin, cos, tan, log, ln, √, !), constants (π, e), and parentheses.
     */
    fun evaluate(rawExpression: String, isRadian: Boolean = false): Result<String> {
        return try {
            val sanitized = sanitizeExpression(rawExpression)
            if (sanitized.isEmpty()) {
                return Result.success("0")
            }
            val tokens = tokenize(sanitized)
            val postfix = infixToPostfix(tokens)
            val result = evaluatePostfix(postfix, isRadian)

            val formatted = formatNumber(result)
            Result.success(formatted)
        } catch (e: ArithmeticException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(IllegalArgumentException(e.message ?: "Invalid expression"))
        }
    }

    private fun sanitizeExpression(expr: String): String {
        return expr
            .replace("×", "*")
            .replace("÷", "/")
            .replace("−", "-")
            .replace("sqrt", "√")
            .replace("π", Math.PI.toString())
            .replace("e", Math.E.toString())
            .replace(" ", "")
    }

    private fun isFunction(name: String): Boolean {
        return name in setOf("sin", "cos", "tan", "log", "ln", "√")
    }

    private fun tokenize(expr: String): List<String> {
        val tokens = mutableListOf<String>()
        var i = 0
        var expectUnary = true

        while (i < expr.length) {
            val c = expr[i]

            when {
                c.isDigit() || c == '.' -> {
                    val sb = StringBuilder()
                    while (i < expr.length && (expr[i].isDigit() || expr[i] == '.')) {
                        sb.append(expr[i])
                        i++
                    }
                    tokens.add(sb.toString())
                    expectUnary = false
                    continue
                }
                c == '-' && expectUnary -> {
                    // Check if it's unary minus preceding a number
                    val sb = StringBuilder("-")
                    i++
                    if (i < expr.length && (expr[i].isDigit() || expr[i] == '.')) {
                        while (i < expr.length && (expr[i].isDigit() || expr[i] == '.')) {
                            sb.append(expr[i])
                            i++
                        }
                        tokens.add(sb.toString())
                        expectUnary = false
                    } else {
                        // Unary minus preceding a function or parenthesis: treat as 0 - ...
                        tokens.add("0")
                        tokens.add("-")
                        expectUnary = true
                    }
                    continue
                }
                c.isLetter() || c == '√' -> {
                    val sb = StringBuilder()
                    if (c == '√') {
                        sb.append('√')
                        i++
                    } else {
                        while (i < expr.length && expr[i].isLetter()) {
                            sb.append(expr[i])
                            i++
                        }
                    }
                    val word = sb.toString()
                    if (isFunction(word)) {
                        tokens.add(word)
                        expectUnary = true
                    } else {
                        tokens.add(word)
                        expectUnary = false
                    }
                    continue
                }
                c == '(' -> {
                    tokens.add("(")
                    expectUnary = true
                }
                c == ')' -> {
                    tokens.add(")")
                    expectUnary = false
                }
                c == '%' -> {
                    tokens.add("%")
                    expectUnary = false
                }
                c == '!' -> {
                    tokens.add("!")
                    expectUnary = false
                }
                c == '+' || c == '-' || c == '*' || c == '/' || c == '^' -> {
                    tokens.add(c.toString())
                    expectUnary = true
                }
            }
            i++
        }
        return tokens
    }

    private fun precedence(op: String): Int {
        return when (op) {
            "+", "-" -> 1
            "*", "/" -> 2
            "%", "^" -> 3
            "!" -> 4
            "sin", "cos", "tan", "log", "ln", "√" -> 5
            else -> 0
        }
    }

    private fun isOperator(token: String): Boolean {
        return token in setOf("+", "-", "*", "/", "%", "^", "!")
    }

    private fun infixToPostfix(tokens: List<String>): List<String> {
        val output = mutableListOf<String>()
        val stack = Stack<String>()

        for (token in tokens) {
            val num = token.toDoubleOrNull()
            when {
                num != null -> output.add(token)
                isFunction(token) -> stack.push(token)
                token == "(" -> stack.push(token)
                token == ")" -> {
                    while (stack.isNotEmpty() && stack.peek() != "(") {
                        output.add(stack.pop())
                    }
                    if (stack.isNotEmpty() && stack.peek() == "(") {
                        stack.pop()
                    }
                    // If function was before (, pop it too
                    if (stack.isNotEmpty() && isFunction(stack.peek())) {
                        output.add(stack.pop())
                    }
                }
                isOperator(token) -> {
                    while (stack.isNotEmpty() && precedence(stack.peek()) >= precedence(token)) {
                        output.add(stack.pop())
                    }
                    stack.push(token)
                }
            }
        }

        while (stack.isNotEmpty()) {
            output.add(stack.pop())
        }

        return output
    }

    private fun factorial(n: Double): Double {
        if (n < 0 || n != round(n)) throw IllegalArgumentException("Factorial only for non-negative integers")
        var result = 1.0
        val intN = n.toInt()
        for (j in 2..intN) {
            result *= j
        }
        return result
    }

    private fun cleanPrecision(value: Double): Double {
        if (abs(value - round(value)) < 1e-12) {
            return round(value)
        }
        return value
    }

    private fun evaluatePostfix(postfix: List<String>, isRadian: Boolean): BigDecimal {
        val stack = Stack<BigDecimal>()

        for (token in postfix) {
            val num = token.toDoubleOrNull()
            if (num != null) {
                stack.push(BigDecimal(token))
            } else if (token == "%") {
                if (stack.isEmpty()) throw IllegalArgumentException("Malformed %")
                val a = stack.pop()
                stack.push(a.divide(BigDecimal("100"), 10, RoundingMode.HALF_UP))
            } else if (token == "!") {
                if (stack.isEmpty()) throw IllegalArgumentException("Malformed !")
                val a = stack.pop().toDouble()
                val fact = factorial(a)
                stack.push(BigDecimal(fact.toString()))
            } else if (isFunction(token)) {
                if (stack.isEmpty()) throw IllegalArgumentException("Missing argument for $token")
                val a = stack.pop().toDouble()
                val res = when (token) {
                    "sin" -> {
                        val angle = if (isRadian) a else Math.toRadians(a)
                        cleanPrecision(sin(angle))
                    }
                    "cos" -> {
                        val angle = if (isRadian) a else Math.toRadians(a)
                        cleanPrecision(cos(angle))
                    }
                    "tan" -> {
                        val angle = if (isRadian) a else Math.toRadians(a)
                        val cosVal = cos(angle)
                        if (abs(cosVal) < 1e-12) throw ArithmeticException("Undefined (tan 90°)")
                        cleanPrecision(tan(angle))
                    }
                    "log" -> {
                        if (a <= 0) throw ArithmeticException("Log domain error")
                        cleanPrecision(log10(a))
                    }
                    "ln" -> {
                        if (a <= 0) throw ArithmeticException("Ln domain error")
                        cleanPrecision(ln(a))
                    }
                    "√" -> {
                        if (a < 0) throw ArithmeticException("Negative square root")
                        cleanPrecision(sqrt(a))
                    }
                    else -> a
                }
                stack.push(BigDecimal.valueOf(res))
            } else if (isOperator(token)) {
                if (stack.size < 2) throw IllegalArgumentException("Malformed expression")
                val b = stack.pop()
                val a = stack.pop()
                val res = when (token) {
                    "+" -> a.add(b)
                    "-" -> a.subtract(b)
                    "*" -> a.multiply(b)
                    "/" -> {
                        if (b.compareTo(BigDecimal.ZERO) == 0) {
                            throw ArithmeticException("Division by zero")
                        }
                        a.divide(b, 10, RoundingMode.HALF_UP)
                    }
                    "^" -> {
                        val powVal = cleanPrecision(a.toDouble().pow(b.toDouble()))
                        BigDecimal.valueOf(powVal)
                    }
                    else -> BigDecimal.ZERO
                }
                stack.push(res)
            }
        }

        if (stack.size != 1) throw IllegalArgumentException("Malformed postfix")
        return stack.pop()
    }

    fun formatNumber(value: BigDecimal): String {
        val stripped = value.stripTrailingZeros()
        return if (stripped.scale() <= 0) {
            stripped.toBigInteger().toString()
        } else {
            decimalFormat.format(stripped)
        }
    }
}
