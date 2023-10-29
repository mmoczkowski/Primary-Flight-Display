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
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import com.mmoczkowski.pfd.theme.PfdTheme

@Composable
internal fun BankIndicator(
    roll: Float,
    modifier: Modifier = Modifier,
    strokeColor: Color = PfdTheme.colors.indicatorColor,
    strokeWidth: Dp = PfdTheme.dimensions.indicatorStrokeThickness,
    indicatorColor: Color = PfdTheme.colors.indicatorColor,
    indicatorSizeLong: Dp = PfdTheme.dimensions.indicatorSizeLong,
    indicatorSizeShort: Dp = PfdTheme.dimensions.indicatorSizeShort
) {
    val strokeWidthPx = with(LocalDensity.current) { strokeWidth.toPx() }
    BoxWithConstraints(
        modifier = modifier.fillMaxSize()
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            drawArc(
                color = strokeColor,
                topLeft = Offset(
                    indicatorSizeLong.toPx(),
                    indicatorSizeLong.toPx()
                ),
                size = size.copy(
                    height = size.width - indicatorSizeLong.toPx() * 2,
                    width = size.width - indicatorSizeLong.toPx() * 2
                ),
                startAngle = -180f,
                sweepAngle = 180f,
                useCenter = false,
                style = Stroke(width = strokeWidthPx)
            )
            (-90..90 step 10).forEachIndexed { index, degrees ->
                rotate(degrees.toFloat(), pivot = center.copy(y = size.height)) {
                    drawLine(
                        color = strokeColor,
                        start = center.copy(y = indicatorSizeLong.toPx()),
                        end = center.copy(y = if (index % 2 == 0) 0f else indicatorSizeShort.toPx()),
                        strokeWidth = strokeWidthPx,
                        cap = StrokeCap.Round
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .size(indicatorSizeLong)
                .offset(y = -maxHeight + indicatorSizeLong)
                .rotate(180f)
                .clip(TriangleShape)
                .background(color = indicatorColor)
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .size(indicatorSizeLong)
                .rotate(roll)
                .offset(y = -maxHeight + indicatorSizeLong * 2)
                .clip(TriangleShape)
                .background(color = indicatorColor)
        )
    }
}
