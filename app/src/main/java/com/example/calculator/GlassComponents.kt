package com.example.calculator

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Color Palette precisely sampled from the design with 4K clarity
object GlassTheme {
    val GlassCardBorder = Brush.linearGradient(
        colors = listOf(
            Color(0x9EFFFFFF),
            Color(0x28FFFFFF),
            Color(0x75FFFFFF),
            Color(0x38FFFFFF)
        ),
        start = Offset(0f, 0f),
        end = Offset(800f, 1400f)
    )

    val GlassCardBackground = Brush.verticalGradient(
        colors = listOf(
            Color(0x40FFFFFF),
            Color(0x18FFFFFF),
            Color(0x22FFFFFF),
            Color(0x30FFFFFF)
        )
    )

    val DividerColor = Color(0x33FFFFFF)
    val ExpressionColor = Color(0xFF868DBF)
    val ResultColor = Color(0xFFFFFFFF)
    val KeyNumberColor = Color(0xFF9AA2D4)
    val KeyOperatorColor = Color(0xFF7587E0)
    val KeyScientificColor = Color(0xFFA5B4FC)
    val KeyEnterColor = Color(0xFF4AC4F3)
    val WindowDotColor = Color(0xCCFFFFFF)
    val ActiveGlowColor = Color(0xFF5CE1E6)
}

/**
 * Animated ambient floating bubbles/orbs with 4K HD smooth gradients and soft breathing highlights.
 */
@Composable
fun FloatingAmbientOrbs(modifier: Modifier = Modifier) {
    val transition = rememberInfiniteTransition(label = "ambient_orbs")

    val floatOffset1 by transition.animateFloat(
        initialValue = -14f,
        targetValue = 16f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 4500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "orb1_float"
    )

    val floatOffset2 by transition.animateFloat(
        initialValue = 12f,
        targetValue = -14f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 5400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "orb2_float"
    )

    val pulseGlow by transition.animateFloat(
        initialValue = 0.9f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_glow"
    )

    Box(modifier = modifier.fillMaxSize()) {
        // Top-left frosted lavender sphere (crisp 4K multi-layer radial gradient)
        Box(
            modifier = Modifier
                .offset(x = 18.dp, y = (70 + floatOffset1).dp)
                .size(115.dp)
                .drawBehind {
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color(0x75D8C3FF),
                                Color(0x40BE9DFF),
                                Color(0x159F77F8),
                                Color(0x00A076F9)
                            ),
                            radius = size.minDimension / 1.7f * pulseGlow
                        )
                    )
                }
        )

        // Top-center cyan bubble
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(x = (-30).dp, y = (100 + floatOffset2).dp)
                .size(70.dp)
                .drawBehind {
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color(0x757CF3FF),
                                Color(0x3565D6FC),
                                Color(0x1041B3E0),
                                Color(0x0041B3E0)
                            ),
                            radius = size.minDimension / 1.8f
                        )
                    )
                }
        )

        // Mid-right glowing magenta orb
        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .offset(x = (-12).dp, y = (-70 + floatOffset1).dp)
                .size(56.dp)
                .drawBehind {
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color(0x8FFF77B9),
                                Color(0x48E040FB),
                                Color(0x12FF4081),
                                Color(0x00FF4081)
                            ),
                            radius = size.minDimension / 1.8f
                        )
                    )
                }
        )

        // Bottom-right glowing aqua bubble
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = (-26).dp, y = (-120 + floatOffset2).dp)
                .size(96.dp)
                .drawBehind {
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color(0x805EFCE8),
                                Color(0x403FE3F2),
                                Color(0x121BD0D5),
                                Color(0x001BD0D5)
                            ),
                            radius = size.minDimension / 1.7f * pulseGlow
                        )
                    )
                }
        )
    }
}

/**
 * The frosted glassmorphism calculator container with 4K clarity and specular highlight.
 */
@Composable
fun GlassCalculatorCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val cornerRadius = 32.dp

    Box(
        modifier = modifier
            .shadow(
                elevation = 24.dp,
                shape = RoundedCornerShape(cornerRadius),
                ambientColor = Color(0x453F4687),
                spotColor = Color(0x557C6FF8)
            )
            .clip(RoundedCornerShape(cornerRadius))
            .background(GlassTheme.GlassCardBackground)
            .border(
                width = 1.35.dp,
                brush = GlassTheme.GlassCardBorder,
                shape = RoundedCornerShape(cornerRadius)
            )
    ) {
        // Delicate 4K specular light reflection along the top curve
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0x3AFFFFFF),
                            Color(0x10FFFFFF),
                            Color(0x00FFFFFF)
                        )
                    )
                )
        )

        content()
    }
}

/**
 * Three window header dots on the top-left of the glass card.
 */
@Composable
fun GlassWindowDots(
    modifier: Modifier = Modifier,
    onDotsClick: () -> Unit
) {
    Row(
        modifier = modifier
            .testTag("window_dots_button")
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onDotsClick)
            .padding(horizontal = 4.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(3) {
            Box(
                modifier = Modifier
                    .size(6.5.dp)
                    .background(GlassTheme.WindowDotColor, CircleShape)
            )
        }
    }
}

/**
 * Toggle pill for switching between Basic and Scientific modes, plus Angle unit (DEG / RAD).
 */
@Composable
fun GlassModeControls(
    isScientificMode: Boolean,
    isRadian: Boolean,
    onToggleScientific: () -> Unit,
    onToggleAngleUnit: () -> Unit,
    modifier: Modifier = Modifier
) {
    val haptic = LocalHapticFeedback.current

    val sciBgColor by animateColorAsState(
        targetValue = if (isScientificMode) Color(0x444AC4F3) else Color(0x1FFFFFFF),
        label = "sci_bg"
    )
    val sciBorderColor by animateColorAsState(
        targetValue = if (isScientificMode) Color(0x995CE1E6) else Color(0x35FFFFFF),
        label = "sci_border"
    )
    val sciTextColor by animateColorAsState(
        targetValue = if (isScientificMode) Color.White else GlassTheme.KeyNumberColor,
        label = "sci_text"
    )

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Angle unit toggle (DEG / RAD) - visible in scientific mode
        if (isScientificMode) {
            Box(
                modifier = Modifier
                    .testTag("toggle_angle_unit_button")
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0x24FFFFFF))
                    .border(1.dp, Color(0x40FFFFFF), RoundedCornerShape(12.dp))
                    .clickable {
                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                        onToggleAngleUnit()
                    }
                    .padding(horizontal = 9.dp, vertical = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (isRadian) "RAD" else "DEG",
                    color = Color.White,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = FontFamily.SansSerif,
                    letterSpacing = 0.5.sp
                )
            }
        }

        // Scientific fx toggle button
        Box(
            modifier = Modifier
                .testTag("toggle_scientific_button")
                .clip(RoundedCornerShape(14.dp))
                .background(sciBgColor)
                .border(1.dp, sciBorderColor, RoundedCornerShape(14.dp))
                .clickable {
                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    onToggleScientific()
                }
                .padding(horizontal = 11.dp, vertical = 4.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "fx",
                    color = sciTextColor,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif
                )
                Text(
                    text = if (isScientificMode) "Sci" else "Basic",
                    color = sciTextColor,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily.SansSerif
                )
            }
        }
    }
}

/**
 * Keypad cell button with smooth ripple, touch scaling, and haptic feedback.
 */
@Composable
fun GlassKeyButton(
    text: String,
    textColor: Color,
    fontSize: Float = 24f,
    fontWeight: FontWeight = FontWeight.Normal,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    testTag: String = "key_$text"
) {
    val haptic = LocalHapticFeedback.current
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val pressedBackground = if (isPressed) Color(0x3EFFFFFF) else Color.Transparent

    Box(
        modifier = modifier
            .fillMaxHeight()
            .testTag(testTag)
            .background(pressedBackground)
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(color = Color.White, bounded = true),
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    onClick()
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = fontSize.sp,
            fontWeight = fontWeight,
            fontFamily = FontFamily.SansSerif,
            textAlign = TextAlign.Center
        )
    }
}
