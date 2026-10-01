package com.example.calculator

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun GlassKeypad(
    modifier: Modifier = Modifier,
    rowHeight: Dp = 66.dp,
    sciRowHeight: Dp = 50.dp,
    isScientificMode: Boolean = false,
    onDigit: (String) -> Unit,
    onDecimal: () -> Unit,
    onOperator: (String) -> Unit,
    onScientificFunction: (String) -> Unit,
    onToggleSign: () -> Unit,
    onPercentage: () -> Unit,
    onClear: () -> Unit,
    onEnter: () -> Unit
) {
    val dividerColor = GlassTheme.DividerColor
    val dividerThickness = 1.dp

    Column(modifier = modifier.fillMaxWidth()) {
        // Top divider separating display from keypad
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(dividerThickness)
                .background(dividerColor)
        )

        // Scientific Advanced Rows (Revealed when Scientific Mode is toggled ON)
        AnimatedVisibility(
            visible = isScientificMode,
            enter = expandVertically() + fadeIn(),
            exit = shrinkVertically() + fadeOut()
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                // Row S1: sin | cos | tan | π
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(sciRowHeight)
                ) {
                    GlassKeyButton(
                        text = "sin",
                        textColor = GlassTheme.KeyScientificColor,
                        fontSize = 17f,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.weight(1f),
                        onClick = { onScientificFunction("sin") },
                        testTag = "key_sin"
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(dividerThickness)
                            .background(dividerColor)
                    )
                    GlassKeyButton(
                        text = "cos",
                        textColor = GlassTheme.KeyScientificColor,
                        fontSize = 17f,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.weight(1f),
                        onClick = { onScientificFunction("cos") },
                        testTag = "key_cos"
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(dividerThickness)
                            .background(dividerColor)
                    )
                    GlassKeyButton(
                        text = "tan",
                        textColor = GlassTheme.KeyScientificColor,
                        fontSize = 17f,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.weight(1f),
                        onClick = { onScientificFunction("tan") },
                        testTag = "key_tan"
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(dividerThickness)
                            .background(dividerColor)
                    )
                    GlassKeyButton(
                        text = "π",
                        textColor = GlassTheme.KeyOperatorColor,
                        fontSize = 19f,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.weight(1f),
                        onClick = { onScientificFunction("π") },
                        testTag = "key_pi"
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(dividerThickness)
                        .background(dividerColor)
                )

                // Row S2: ln | log | √ | ^
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(sciRowHeight)
                ) {
                    GlassKeyButton(
                        text = "ln",
                        textColor = GlassTheme.KeyScientificColor,
                        fontSize = 17f,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.weight(1f),
                        onClick = { onScientificFunction("ln") },
                        testTag = "key_ln"
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(dividerThickness)
                            .background(dividerColor)
                    )
                    GlassKeyButton(
                        text = "log",
                        textColor = GlassTheme.KeyScientificColor,
                        fontSize = 17f,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.weight(1f),
                        onClick = { onScientificFunction("log") },
                        testTag = "key_log"
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(dividerThickness)
                            .background(dividerColor)
                    )
                    GlassKeyButton(
                        text = "√",
                        textColor = GlassTheme.KeyOperatorColor,
                        fontSize = 20f,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.weight(1f),
                        onClick = { onScientificFunction("√") },
                        testTag = "key_sqrt"
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(dividerThickness)
                            .background(dividerColor)
                    )
                    GlassKeyButton(
                        text = "^",
                        textColor = GlassTheme.KeyOperatorColor,
                        fontSize = 20f,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.weight(1f),
                        onClick = { onScientificFunction("^") },
                        testTag = "key_power"
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(dividerThickness)
                        .background(dividerColor)
                )

                // Row S3: ( | ) | x² | e
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(sciRowHeight)
                ) {
                    GlassKeyButton(
                        text = "(",
                        textColor = GlassTheme.KeyNumberColor,
                        fontSize = 20f,
                        modifier = Modifier.weight(1f),
                        onClick = { onScientificFunction("(") },
                        testTag = "key_open_parenthesis"
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(dividerThickness)
                            .background(dividerColor)
                    )
                    GlassKeyButton(
                        text = ")",
                        textColor = GlassTheme.KeyNumberColor,
                        fontSize = 20f,
                        modifier = Modifier.weight(1f),
                        onClick = { onScientificFunction(")") },
                        testTag = "key_close_parenthesis"
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(dividerThickness)
                            .background(dividerColor)
                    )
                    GlassKeyButton(
                        text = "x²",
                        textColor = GlassTheme.KeyScientificColor,
                        fontSize = 17f,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.weight(1f),
                        onClick = { onScientificFunction("x²") },
                        testTag = "key_square"
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(dividerThickness)
                            .background(dividerColor)
                    )
                    GlassKeyButton(
                        text = "e",
                        textColor = GlassTheme.KeyOperatorColor,
                        fontSize = 19f,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.weight(1f),
                        onClick = { onScientificFunction("e") },
                        testTag = "key_e"
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(dividerThickness)
                        .background(dividerColor)
                )
            }
        }

        // Row 1: C | ± | % | ÷
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(rowHeight)
        ) {
            GlassKeyButton(
                text = "C",
                textColor = GlassTheme.KeyNumberColor,
                fontSize = 22f,
                modifier = Modifier.weight(1f),
                onClick = onClear,
                testTag = "key_clear"
            )
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(dividerThickness)
                    .background(dividerColor)
            )
            GlassKeyButton(
                text = "±",
                textColor = GlassTheme.KeyNumberColor,
                fontSize = 22f,
                modifier = Modifier.weight(1f),
                onClick = onToggleSign,
                testTag = "key_toggle_sign"
            )
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(dividerThickness)
                    .background(dividerColor)
            )
            GlassKeyButton(
                text = "%",
                textColor = GlassTheme.KeyNumberColor,
                fontSize = 20f,
                modifier = Modifier.weight(1f),
                onClick = onPercentage,
                testTag = "key_percentage"
            )
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(dividerThickness)
                    .background(dividerColor)
            )
            GlassKeyButton(
                text = "÷",
                textColor = GlassTheme.KeyOperatorColor,
                fontSize = 26f,
                modifier = Modifier.weight(1f),
                onClick = { onOperator("÷") },
                testTag = "key_divide"
            )
        }

        // Horizontal divider 1
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(dividerThickness)
                .background(dividerColor)
        )

        // Row 2: 7 | 8 | 9 | ×
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(rowHeight)
        ) {
            GlassKeyButton(
                text = "7",
                textColor = GlassTheme.KeyNumberColor,
                fontSize = 24f,
                modifier = Modifier.weight(1f),
                onClick = { onDigit("7") },
                testTag = "key_7"
            )
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(dividerThickness)
                    .background(dividerColor)
            )
            GlassKeyButton(
                text = "8",
                textColor = GlassTheme.KeyNumberColor,
                fontSize = 24f,
                modifier = Modifier.weight(1f),
                onClick = { onDigit("8") },
                testTag = "key_8"
            )
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(dividerThickness)
                    .background(dividerColor)
            )
            GlassKeyButton(
                text = "9",
                textColor = GlassTheme.KeyNumberColor,
                fontSize = 24f,
                modifier = Modifier.weight(1f),
                onClick = { onDigit("9") },
                testTag = "key_9"
            )
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(dividerThickness)
                    .background(dividerColor)
            )
            GlassKeyButton(
                text = "×",
                textColor = GlassTheme.KeyOperatorColor,
                fontSize = 24f,
                modifier = Modifier.weight(1f),
                onClick = { onOperator("×") },
                testTag = "key_multiply"
            )
        }

        // Horizontal divider 2
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(dividerThickness)
                .background(dividerColor)
        )

        // Row 3: 4 | 5 | 6 | −
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(rowHeight)
        ) {
            GlassKeyButton(
                text = "4",
                textColor = GlassTheme.KeyNumberColor,
                fontSize = 24f,
                modifier = Modifier.weight(1f),
                onClick = { onDigit("4") },
                testTag = "key_4"
            )
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(dividerThickness)
                    .background(dividerColor)
            )
            GlassKeyButton(
                text = "5",
                textColor = GlassTheme.KeyNumberColor,
                fontSize = 24f,
                modifier = Modifier.weight(1f),
                onClick = { onDigit("5") },
                testTag = "key_5"
            )
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(dividerThickness)
                    .background(dividerColor)
            )
            GlassKeyButton(
                text = "6",
                textColor = GlassTheme.KeyNumberColor,
                fontSize = 24f,
                modifier = Modifier.weight(1f),
                onClick = { onDigit("6") },
                testTag = "key_6"
            )
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(dividerThickness)
                    .background(dividerColor)
            )
            GlassKeyButton(
                text = "−",
                textColor = GlassTheme.KeyOperatorColor,
                fontSize = 26f,
                modifier = Modifier.weight(1f),
                onClick = { onOperator("−") },
                testTag = "key_minus"
            )
        }

        // Horizontal divider 3
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(dividerThickness)
                .background(dividerColor)
        )

        // Row 4: 1 | 2 | 3 | +
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(rowHeight)
        ) {
            GlassKeyButton(
                text = "1",
                textColor = GlassTheme.KeyNumberColor,
                fontSize = 24f,
                modifier = Modifier.weight(1f),
                onClick = { onDigit("1") },
                testTag = "key_1"
            )
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(dividerThickness)
                    .background(dividerColor)
            )
            GlassKeyButton(
                text = "2",
                textColor = GlassTheme.KeyNumberColor,
                fontSize = 24f,
                modifier = Modifier.weight(1f),
                onClick = { onDigit("2") },
                testTag = "key_2"
            )
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(dividerThickness)
                    .background(dividerColor)
            )
            GlassKeyButton(
                text = "3",
                textColor = GlassTheme.KeyNumberColor,
                fontSize = 24f,
                modifier = Modifier.weight(1f),
                onClick = { onDigit("3") },
                testTag = "key_3"
            )
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(dividerThickness)
                    .background(dividerColor)
            )
            GlassKeyButton(
                text = "+",
                textColor = GlassTheme.KeyOperatorColor,
                fontSize = 26f,
                modifier = Modifier.weight(1f),
                onClick = { onOperator("+") },
                testTag = "key_plus"
            )
        }

        // Horizontal divider 4
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(dividerThickness)
                .background(dividerColor)
        )

        // Row 5: . | 0 (spans columns 2 & 3) | Enter
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(rowHeight)
        ) {
            GlassKeyButton(
                text = ".",
                textColor = GlassTheme.KeyNumberColor,
                fontSize = 26f,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
                onClick = onDecimal,
                testTag = "key_dot"
            )
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(dividerThickness)
                    .background(dividerColor)
            )
            // 0 button spanning 2 columns, centered exactly as in the design
            GlassKeyButton(
                text = "0",
                textColor = GlassTheme.KeyNumberColor,
                fontSize = 24f,
                modifier = Modifier.weight(2f),
                onClick = { onDigit("0") },
                testTag = "key_0"
            )
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(dividerThickness)
                    .background(dividerColor)
            )
            GlassKeyButton(
                text = "Enter",
                textColor = GlassTheme.KeyEnterColor,
                fontSize = 19f,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f),
                onClick = onEnter,
                testTag = "key_enter"
            )
        }
    }
}
