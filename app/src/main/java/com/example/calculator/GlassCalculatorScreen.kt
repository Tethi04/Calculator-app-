package com.example.calculator

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R

@Composable
fun GlassCalculatorScreen(
    viewModel: GlassCalculatorViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        // High-fidelity 4K background matching the original design
        Image(
            painter = painterResource(id = R.drawable.img_glass_bg),
            contentDescription = "Glassmorphic Fluid Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Animated floating ambient 3D bubbles and orbs with 4K HD lighting
        FloatingAmbientOrbs()

        // Centered Glass Calculator Box
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding()
                .padding(horizontal = 14.dp, vertical = 12.dp)
                .verticalScroll(scrollState),
            contentAlignment = Alignment.Center
        ) {
            val availableHeight = maxHeight
            val isSci = uiState.isScientificMode

            val baseRowHeight = when {
                isSci && availableHeight < 720.dp -> 48.dp
                isSci -> 54.dp
                availableHeight < 680.dp -> 58.dp
                else -> 66.dp
            }

            val sciRowHeight = if (availableHeight < 720.dp) 42.dp else 46.dp

            GlassCalculatorCard(
                modifier = Modifier
                    .fillMaxWidth(0.96f)
                    .widthIn(max = 390.dp)
                    .testTag("calculator_glass_card")
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Window Header: Dots (Left) + Scientific Mode Pill (Right)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, top = 16.dp, end = 16.dp, bottom = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        GlassWindowDots(
                            onDotsClick = { viewModel.toggleHistory() }
                        )

                        // Mode switcher for Scientific mode and DEG/RAD
                        GlassModeControls(
                            isScientificMode = uiState.isScientificMode,
                            isRadian = uiState.isRadian,
                            onToggleScientific = { viewModel.toggleScientificMode() },
                            onToggleAngleUnit = { viewModel.toggleAngleUnit() }
                        )
                    }

                    // Display Area: Expression & Main Result
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 22.dp, vertical = 8.dp)
                            .pointerInput(Unit) {
                                detectHorizontalDragGestures { _, dragAmount ->
                                    if (dragAmount < -20f || dragAmount > 20f) {
                                        viewModel.onBackspace()
                                    }
                                }
                            },
                        horizontalAlignment = Alignment.End
                    ) {
                        // Expression Line, e.g. "(62 × 25) + 471" or "sin(45) + √64"
                        Text(
                            text = uiState.expression.ifEmpty { " " },
                            color = GlassTheme.ExpressionColor,
                            fontSize = if (isSci) 16.sp else 18.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = FontFamily.SansSerif,
                            textAlign = TextAlign.End,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("expression_display")
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        // Dynamic text size calculation for 4K crisp scaling
                        val resultText = uiState.displayValue.ifEmpty { "0" }
                        val resultFontSize = when {
                            resultText.length > 14 -> 22.sp
                            resultText.length > 11 -> 26.sp
                            resultText.length > 8 -> 32.sp
                            isSci -> 38.sp
                            else -> 44.sp
                        }

                        // Big Result Line, e.g. "2021"
                        AnimatedContent(
                            targetState = resultText,
                            transitionSpec = {
                                fadeIn() togetherWith fadeOut()
                            },
                            label = "result_animation"
                        ) { targetText ->
                            Text(
                                text = targetText,
                                color = GlassTheme.ResultColor,
                                fontSize = resultFontSize,
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = FontFamily.SansSerif,
                                textAlign = TextAlign.End,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("result_display")
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    // Glass Keypad with optional Scientific Rows
                    GlassKeypad(
                        rowHeight = baseRowHeight,
                        sciRowHeight = sciRowHeight,
                        isScientificMode = uiState.isScientificMode,
                        onDigit = { viewModel.onDigit(it) },
                        onDecimal = { viewModel.onDecimal() },
                        onOperator = { viewModel.onOperator(it) },
                        onScientificFunction = { viewModel.onScientificFunction(it) },
                        onToggleSign = { viewModel.onToggleSign() },
                        onPercentage = { viewModel.onPercentage() },
                        onClear = { viewModel.onClear() },
                        onEnter = { viewModel.onEnter() }
                    )
                }
            }
        }

        // History Dialog Overlay
        if (uiState.showHistory) {
            GlassHistoryDialog(
                history = uiState.history,
                onDismiss = { viewModel.toggleHistory() },
                onSelectItem = { item -> viewModel.selectHistoryItem(item) },
                onClearHistory = { viewModel.clearHistory() }
            )
        }
    }
}
