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

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.mmoczkowski.pfd.theme.PfdTheme

@Composable
fun AltitudeTape(
    altitude: Float,
    targetAltitude: Int?,
    modifier: Modifier = Modifier,
    bgColor: Color = PfdTheme.colors.bgColor,
    textStyle: TextStyle = PfdTheme.typography.regular,
    strokeColor: Color = PfdTheme.colors.indicatorColor,
    strokeWidth: Dp = PfdTheme.dimensions.indicatorStrokeThickness,
    indicatorSizeLong: Dp = PfdTheme.dimensions.indicatorSizeLong,
    indicatorSizeShort: Dp = PfdTheme.dimensions.indicatorSizeShort,
    spacing: Dp = PfdTheme.dimensions.spacingRegular
) {
    Column(
        modifier = modifier
            .background(bgColor)
            .border(
                width = strokeWidth,
                color = strokeColor
            )
    ) {
        if(targetAltitude != null) {
            Text(
                text = "%d".format(targetAltitude),
                style = PfdTheme.typography.large.copy(color = PfdTheme.colors.courseColor),
                textAlign = TextAlign.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacing)
            )
        }
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = strokeColor,
            thickness = strokeWidth
        )
        Box(
            contentAlignment = Alignment.Center,
        ) {
            Tape(
                value = altitude,
                targetValue = targetAltitude,
                spacing = spacing,
                textStyle = textStyle,
                strokeColor = strokeColor,
                strokeThickness = strokeWidth,
                indicatorSizeLong = indicatorSizeLong,
                indicatorSizeShort = indicatorSizeShort,
                isLeftOriented = true,
                modifier = Modifier.padding(end = spacing)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = indicatorSizeLong),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(indicatorSizeLong)
                        .rotate(-90f)
                        .clip(TriangleShape)
                        .background(color = strokeColor)
                )
                DigitCounter(
                    value = altitude,
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
