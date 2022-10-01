package com.android.favoritemakes.ui.animations

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

const val INDICATOR_SIZE = 10
private const val INDICATOR_SPACING = 10
private const val NUMBER_OF_INDICATORS = 3
private const val ANIMATION_DURATION = 300
private const val ANIMATION_DELAY = ANIMATION_DURATION / NUMBER_OF_INDICATORS
private const val INITIAL_VALUE = NUMBER_OF_INDICATORS / 2f
private const val TARGET_VALUE = -NUMBER_OF_INDICATORS / 2f

/**
 * Slightly modified version of loading button:
 * https://fvilarino.medium.com/creating-a-loading-button-in-jetpack-compose-9f63e772891c
 */
@Composable
fun LoadingIndicator(
    isVisible: Boolean,
    modifier: Modifier = Modifier,
    indicatorHeight: Int = INDICATOR_SIZE,
    color: Color = MaterialTheme.colorScheme.primary,
    indicatorSpacing: Dp = INDICATOR_SPACING.dp,
) {
    val animatedValues = List(NUMBER_OF_INDICATORS) { index ->
        var animatedValue by remember { mutableStateOf(0f) }
        LaunchedEffect(key1 = isVisible) {
            animate(
                initialValue = INITIAL_VALUE,
                targetValue = TARGET_VALUE,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = ANIMATION_DURATION),
                    repeatMode = RepeatMode.Reverse,
                    initialStartOffset = StartOffset(ANIMATION_DELAY * index),
                ),
            ) { value, _ -> animatedValue = value }
        }
        animatedValue
    }
    if (isVisible) {
        Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
            animatedValues.forEach { animatedValue ->
                LoadingDot(
                    modifier = Modifier
                        .padding(horizontal = indicatorSpacing)
                        .width(indicatorHeight.dp)
                        .aspectRatio(1f)
                        .then(Modifier.offset(y = animatedValue.dp)),
                    color = color,
                )
            }
        }
    }
}

@Composable
private fun LoadingDot(
    color: Color,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clip(shape = CircleShape)
            .background(color = color)
    )
}