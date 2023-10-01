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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.Dp
import com.mmoczkowski.pfd.theme.PfdTheme

@Composable
internal fun AircraftSymbol(
    modifier: Modifier = Modifier,
    strokeWidth: Dp = PfdTheme.dimensions.indicatorStrokeWidth
) {
    Canvas(
        modifier = modifier.fillMaxSize()
    ) {
        drawPoints(
            points = listOf(
                Offset(0f, 0f),
                Offset(size.width * 2f / 8f, 0f),
                Offset(size.width * 3f / 8f, size.height),
                Offset(size.width * 4f / 8f, 0f),
                Offset(size.width * 5f / 8f, size.height),
                Offset(size.width * 6f / 8f, 0f),
                Offset(size.width, 0f)
            ),
            pointMode = PointMode.Polygon,
            strokeWidth = strokeWidth.toPx(),
            cap = StrokeCap.Square,
            color = Color.White,
            blendMode = BlendMode.Difference
        )
    }
}
