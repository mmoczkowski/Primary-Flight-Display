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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp
import com.mmoczkowski.pfd.theme.PfdTheme
import kotlin.math.absoluteValue
import kotlin.math.pow
import kotlin.math.sqrt

@Composable
internal fun Horizon(
    roll: Float,
    pitch: Float,
    modifier: Modifier = Modifier,
    skyColor: Color = PfdTheme.colors.skyColor,
    groundColor: Color = PfdTheme.colors.groundColor,
    strokeColor: Color = PfdTheme.colors.indicatorColor,
    strokeWidth: Dp = PfdTheme.dimensions.indicatorStrokeWidth,
    textStyle: TextStyle = PfdTheme.typography.regular,
    spacing: Dp = PfdTheme.dimensions.spacingLarge
) {
    val textMeasurer = rememberTextMeasurer()

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .clipToBounds()
    ) {
        drawRect(
            color = skyColor,
            topLeft = Offset.Zero,
            size = size
        )
        val items: List<Int> = (90 downTo -90 step 5).toList()
        val spacingPerDegree = spacing.toPx() * items.lastIndex / 180f

        // Set origin to center
        translate(left = size.width / 2, top = size.height / 2) {
            // Apply pitch
            translate(left = 0f, top = pitch * spacingPerDegree) {
                // Apply roll
                rotate(degrees = roll, pivot = Offset.Zero) {
                    val diagonal: Float = sqrt(size.width.pow(2) + size.height.pow(2))
                    drawRect(
                        color = groundColor,
                        topLeft = Offset(x = -diagonal / 2, y = 0f),
                        size = Size(width = diagonal, height = diagonal)
                    )

                    items.forEach { degrees ->
                        translate(top = degrees * spacingPerDegree) {
                            val xOffset = size.width / if (degrees % 10 == 0) 4 else 6
                            val textLayout = textMeasurer.measure(
                                text = degrees.absoluteValue.toString(),
                                style = textStyle
                            )

                            if (degrees == 0) {
                                drawLine(
                                    color = strokeColor,
                                    strokeWidth = strokeWidth.toPx(),
                                    start = Offset(-size.width / 2f, 0f),
                                    end = Offset(size.width / 2f, 0f)
                                )
                            } else {
                                if (degrees % 10 == 0) {
                                    drawText(
                                        textLayoutResult = textLayout,
                                        topLeft = Offset(
                                            x = -xOffset - textLayout.size.width,
                                            y = -textLayout.size.height / 2f
                                        )
                                    )

                                    drawText(
                                        textLayoutResult = textLayout,
                                        topLeft = Offset(
                                            x = xOffset,
                                            y = -textLayout.size.height / 2f
                                        )
                                    )
                                }
                                drawLine(
                                    color = strokeColor,
                                    strokeWidth = strokeWidth.toPx(),
                                    start = Offset(-xOffset, 0f),
                                    end = Offset(xOffset, 0f)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
