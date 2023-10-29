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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mmoczkowski.pfd.theme.PfdTheme

@Composable
fun AttitudeIndicator(
    roll: Float,
    pitch: Float,
    modifier: Modifier = Modifier,
    skyColor: Color = PfdTheme.colors.skyColor,
    groundColor: Color = PfdTheme.colors.groundColor,
    strokeColor: Color = PfdTheme.colors.indicatorColor,
    strokeWidth: Dp = PfdTheme.dimensions.indicatorStrokeThickness,
    indicatorColor: Color = PfdTheme.colors.indicatorColor,
    indicatorSizeLong: Dp = PfdTheme.dimensions.indicatorSizeLong,
    indicatorSizeShort: Dp = PfdTheme.dimensions.indicatorSizeShort,
    textStyle: TextStyle = PfdTheme.typography.regular,
    spacing: Dp = PfdTheme.dimensions.spacingLarge
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Horizon(
            roll = roll,
            pitch = pitch,
            skyColor = skyColor,
            groundColor = groundColor,
            strokeColor = strokeColor,
            strokeWidth = strokeWidth,
            textStyle = textStyle,
            spacing = spacing
        )
        BankIndicator(
            roll = roll,
            strokeColor = strokeColor,
            strokeWidth = strokeWidth,
            indicatorColor = indicatorColor,
            indicatorSizeLong = indicatorSizeLong,
            indicatorSizeShort = indicatorSizeShort,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .aspectRatio(2f)
                .align(Alignment.TopCenter)
        )
        AircraftSymbol(
            strokeWidth = strokeWidth,
            modifier = Modifier
                .fillMaxSize(0.5f)
                .aspectRatio(4f)
                .relativeOffset(y = 0.5f)
        )
    }
}
