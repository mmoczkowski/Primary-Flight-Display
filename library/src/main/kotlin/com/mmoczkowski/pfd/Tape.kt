/*
 * Copyright (C) 2023 Miłosz Moczkowski
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mmoczkowski.pfd

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp
import com.mmoczkowski.pfd.theme.PfdTheme

@Composable
internal fun Tape(
    value: Float,
    targetValue: Int?,
    modifier: Modifier = Modifier,
    spacing: Dp = PfdTheme.dimensions.spacingLarge,
    textStyle: TextStyle = PfdTheme.typography.regular,
    strokeColor: Color = PfdTheme.colors.indicatorColor,
    strokeThickness: Dp = PfdTheme.dimensions.indicatorStrokeThickness,
    indicatorSizeLong: Dp = PfdTheme.dimensions.indicatorSizeLong,
    indicatorSizeShort: Dp = PfdTheme.dimensions.indicatorSizeShort,
    isLeftOriented: Boolean = false,
) {
    val textMeasurer = rememberTextMeasurer()

    Canvas(
        modifier = modifier.fillMaxSize().clipToBounds()
    ) {
        val centerY = (size.height) / 2f
        val centerItemHeight =
            drawText(
                number = value.toInt(),
                y = centerY,
                offset = (value % 1f),
                spacing = spacing.toPx(),
                textMeasurer = textMeasurer,
                textStyle = textStyle,
                strokeColor = strokeColor,
                strokeThickness = strokeThickness.toPx(),
                indicatorSizeLong = indicatorSizeLong.toPx(),
                indicatorSizeShort = indicatorSizeShort.toPx(),
                isLeft = isLeftOriented
            )

        var y = centerY + centerItemHeight
        var index = 0
        while (y <= size.height + centerItemHeight) {
            y += drawText(
                number = value.toInt() - ++index,
                y = y,
                offset = (value % 1f),
                spacing = spacing.toPx(),
                textMeasurer = textMeasurer,
                textStyle = textStyle,
                strokeColor = strokeColor,
                strokeThickness = strokeThickness.toPx(),
                indicatorSizeLong = indicatorSizeLong.toPx(),
                indicatorSizeShort = indicatorSizeShort.toPx(),
                isLeft = isLeftOriented
            )
        }

        index = 0
        y = centerY - centerItemHeight
        while (y >= -centerItemHeight) {
            y -= drawText(
                number = value.toInt() + ++index,
                y = y,
                offset = (value % 1f),
                spacing = spacing.toPx(),
                textMeasurer = textMeasurer,
                textStyle = textStyle,
                strokeColor = strokeColor,
                strokeThickness = strokeThickness.toPx(),
                indicatorSizeLong = indicatorSizeLong.toPx(),
                indicatorSizeShort = indicatorSizeShort.toPx(),
                isLeft = isLeftOriented
            )
        }

        if (targetValue != null) {
            val strokeWidthPx = indicatorSizeLong.toPx()
            translate(
                left = if (isLeftOriented) strokeWidthPx / 2 else size.width - strokeWidthPx / 2,
                top = size.height / 2
            ) {
                drawLine(
                    color = Color.Magenta,
                    start = Offset(0f, 0f),
                    end = Offset(0f, (value - targetValue) * centerItemHeight),
                    strokeWidth = strokeWidthPx,
                    blendMode = BlendMode.Plus
                )
            }
        }
    }
}

private fun DrawScope.drawText(
    number: Int,
    y: Float,
    offset: Float,
    spacing: Float,
    textMeasurer: TextMeasurer,
    textStyle: TextStyle,
    strokeColor: Color,
    strokeThickness: Float,
    indicatorSizeLong: Float,
    indicatorSizeShort: Float,
    isLeft: Boolean = false
): Float {
    val textLayout = textMeasurer.measure(text = number.toString(), style = textStyle)
    val textHeight = textLayout.size.height
    val textX = if (isLeft) {
        size.width - textLayout.size.width
    } else 0f
    val textY = y - textHeight / 2 + (textHeight + spacing) * offset
    drawText(
        textLayoutResult = textLayout,
        topLeft = Offset(
            x = textX,
            y = textY
        )
    )
    translate(top = textHeight / 2f) {
        drawLine(
            color = strokeColor,
            start = Offset(if (isLeft) 0f else size.width - indicatorSizeLong, textY),
            end = Offset(if (isLeft) indicatorSizeLong else size.width, textY),
            strokeWidth = strokeThickness
        )

    }
    translate(top = spacing / 2 + textHeight) {
        drawLine(
            color = strokeColor,
            start = Offset(if (isLeft) 0f else size.width - indicatorSizeShort, textY),
            end = Offset(if (isLeft) indicatorSizeShort else size.width, textY),
            strokeWidth = strokeThickness
        )
    }
    return textLayout.size.height.toFloat() + spacing
}
