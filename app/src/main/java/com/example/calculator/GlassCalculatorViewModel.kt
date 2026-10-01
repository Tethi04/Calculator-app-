package com.example.calculator

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class CalculatorUiState(
    val expression: String = "(62 × 25) + 471",
    val displayValue: String = "2021",
    val isEvaluated: Boolean = true,
    val isScientificMode: Boolean = false,
    val isRadian: Boolean = false, // false = DEG, true = RAD
    val history: List<HistoryItem> = listOf(
        HistoryItem(expression = "(62 × 25) + 471", result = "2021")
    ),
    val showHistory: Boolean = false,
    val errorMessage: String? = null
)

class GlassCalculatorViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CalculatorUiState())
    val uiState: StateFlow<CalculatorUiState> = _uiState.asStateFlow()

    fun toggleScientificMode() {
        _uiState.update { it.copy(isScientificMode = !it.isScientificMode) }
    }

    fun toggleAngleUnit() {
        _uiState.update { it.copy(isRadian = !it.isRadian) }
    }

    fun onDigit(digit: String) {
        _uiState.update { state ->
            if (state.isEvaluated) {
                state.copy(
                    expression = "",
                    displayValue = digit,
                    isEvaluated = false,
                    errorMessage = null
                )
            } else {
                val newDisplay = if (state.displayValue == "0") digit else state.displayValue + digit
                state.copy(
                    displayValue = newDisplay,
                    errorMessage = null
                )
            }
        }
    }

    fun onDecimal() {
        _uiState.update { state ->
            if (state.isEvaluated) {
                state.copy(
                    expression = "",
                    displayValue = "0.",
                    isEvaluated = false,
                    errorMessage = null
                )
            } else {
                if (!state.displayValue.contains(".")) {
                    val newDisplay = if (state.displayValue.isEmpty()) "0." else state.displayValue + "."
                    state.copy(displayValue = newDisplay, errorMessage = null)
                } else {
                    state
                }
            }
        }
    }

    fun onOperator(op: String) {
        _uiState.update { state ->
            val currentVal = state.displayValue.ifEmpty { "0" }
            val newExpr = if (state.expression.isEmpty() || state.isEvaluated) {
                "$currentVal $op "
            } else {
                if (state.displayValue.isNotEmpty()) {
                    "${state.expression}$currentVal $op "
                } else {
                    val trimmed = state.expression.trimEnd()
                    if (trimmed.endsWith("+") || trimmed.endsWith("−") || trimmed.endsWith("×") || trimmed.endsWith("÷") || trimmed.endsWith("^")) {
                        trimmed.dropLast(1).trimEnd() + " $op "
                    } else {
                        state.expression + "$op "
                    }
                }
            }

            state.copy(
                expression = newExpr,
                displayValue = "",
                isEvaluated = false,
                errorMessage = null
            )
        }
    }

    fun onScientificFunction(fn: String) {
        _uiState.update { state ->
            when (fn) {
                "sin", "cos", "tan", "log", "ln", "√" -> {
                    val baseExpr = if (state.isEvaluated) "" else state.expression
                    state.copy(
                        expression = "$baseExpr$fn(",
                        displayValue = "",
                        isEvaluated = false,
                        errorMessage = null
                    )
                }
                "π" -> {
                    val baseExpr = if (state.isEvaluated) "" else state.expression
                    state.copy(
                        expression = "${baseExpr}π",
                        displayValue = "",
                        isEvaluated = false,
                        errorMessage = null
                    )
                }
                "e" -> {
                    val baseExpr = if (state.isEvaluated) "" else state.expression
                    state.copy(
                        expression = "${baseExpr}e",
                        displayValue = "",
                        isEvaluated = false,
                        errorMessage = null
                    )
                }
                "^" -> {
                    val currentVal = state.displayValue.ifEmpty { "0" }
                    val baseExpr = if (state.isEvaluated) currentVal else "${state.expression}$currentVal"
                    state.copy(
                        expression = "$baseExpr ^ ",
                        displayValue = "",
                        isEvaluated = false,
                        errorMessage = null
                    )
                }
                "x²" -> {
                    val currentVal = state.displayValue.ifEmpty { "0" }
                    val baseExpr = if (state.isEvaluated) currentVal else "${state.expression}$currentVal"
                    state.copy(
                        expression = "$baseExpr ^ 2",
                        displayValue = "",
                        isEvaluated = false,
                        errorMessage = null
                    )
                }
                "(" -> {
                    val baseExpr = if (state.isEvaluated) "" else state.expression
                    state.copy(
                        expression = "$baseExpr(",
                        displayValue = "",
                        isEvaluated = false,
                        errorMessage = null
                    )
                }
                ")" -> {
                    val currentVal = state.displayValue
                    val baseExpr = if (state.isEvaluated) "" else state.expression
                    state.copy(
                        expression = "$baseExpr$currentVal)",
                        displayValue = "",
                        isEvaluated = false,
                        errorMessage = null
                    )
                }
                "!" -> {
                    val currentVal = state.displayValue.ifEmpty { "0" }
                    val baseExpr = if (state.isEvaluated) currentVal else "${state.expression}$currentVal"
                    state.copy(
                        expression = "$baseExpr!",
                        displayValue = "",
                        isEvaluated = false,
                        errorMessage = null
                    )
                }
                "1/x" -> {
                    val currentVal = state.displayValue.ifEmpty { "0" }
                    state.copy(
                        expression = "1 ÷ ($currentVal)",
                        displayValue = "",
                        isEvaluated = false,
                        errorMessage = null
                    )
                }
                else -> state
            }
        }
    }

    fun onToggleSign() {
        _uiState.update { state ->
            if (state.displayValue.isEmpty() || state.displayValue == "0") {
                state
            } else if (state.displayValue.startsWith("-")) {
                state.copy(displayValue = state.displayValue.removePrefix("-"))
            } else {
                state.copy(displayValue = "-" + state.displayValue)
            }
        }
    }

    fun onPercentage() {
        _uiState.update { state ->
            val current = state.displayValue.toDoubleOrNull()
            if (current != null) {
                val pct = current / 100.0
                val formatted = if (pct % 1.0 == 0.0) pct.toLong().toString() else pct.toString()
                state.copy(displayValue = formatted)
            } else {
                state
            }
        }
    }

    fun onClear() {
        _uiState.update { state ->
            if (state.displayValue.isNotEmpty() && !state.isEvaluated) {
                state.copy(displayValue = "0")
            } else {
                state.copy(expression = "", displayValue = "0", isEvaluated = false, errorMessage = null)
            }
        }
    }

    fun onClearAll() {
        _uiState.update { state ->
            state.copy(expression = "", displayValue = "0", isEvaluated = false, errorMessage = null)
        }
    }

    fun onBackspace() {
        _uiState.update { state ->
            if (state.isEvaluated) {
                state.copy(expression = "", displayValue = "0", isEvaluated = false)
            } else if (state.displayValue.isNotEmpty()) {
                val dropped = state.displayValue.dropLast(1)
                state.copy(displayValue = if (dropped.isEmpty() || dropped == "-") "0" else dropped)
            } else if (state.expression.isNotEmpty()) {
                val trimmed = state.expression.trimEnd()
                state.copy(expression = trimmed.dropLast(1))
            } else {
                state
            }
        }
    }

    fun onEnter() {
        _uiState.update { state ->
            val fullExpr = if (state.displayValue.isNotEmpty()) {
                "${state.expression}${state.displayValue}".trim()
            } else {
                state.expression.trim()
            }

            if (fullExpr.isEmpty()) {
                return@update state
            }

            val evalResult = CalculatorEngine.evaluate(fullExpr, state.isRadian)
            evalResult.fold(
                onSuccess = { resultText ->
                    val historyEntry = HistoryItem(
                        expression = fullExpr,
                        result = resultText
                    )
                    state.copy(
                        expression = fullExpr,
                        displayValue = resultText,
                        isEvaluated = true,
                        history = listOf(historyEntry) + state.history.take(49),
                        errorMessage = null
                    )
                },
                onFailure = { error ->
                    state.copy(
                        errorMessage = error.message ?: "Error",
                        displayValue = "Error",
                        isEvaluated = true
                    )
                }
            )
        }
    }

    fun toggleHistory() {
        _uiState.update { it.copy(showHistory = !it.showHistory) }
    }

    fun selectHistoryItem(item: HistoryItem) {
        _uiState.update { state ->
            state.copy(
                expression = item.expression,
                displayValue = item.result,
                isEvaluated = true,
                showHistory = false,
                errorMessage = null
            )
        }
    }

    fun clearHistory() {
        _uiState.update { it.copy(history = emptyList()) }
    }
}
