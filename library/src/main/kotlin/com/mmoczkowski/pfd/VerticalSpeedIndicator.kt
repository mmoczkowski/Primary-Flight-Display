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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mmoczkowski.pfd.theme.PfdTheme
import kotlin.math.absoluteValue

@Composable
internal fun VerticalSpeedIndicator(
    verticalSpeed: Float,
    modifier: Modifier = Modifier,
    maxScaleMagnitude: Int = 5,
    strokeColor: Color = PfdTheme.colors.indicatorColor,
    strokeWidth: Dp = PfdTheme.dimensions.indicatorStrokeThickness,
    spacingRegular: Dp = PfdTheme.dimensions.spacingRegular,
) {
    BoxWithConstraints(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .background(color = PfdTheme.colors.bgColor)
    ) {
        val listState: LazyListState = rememberLazyListState()
        LazyColumn(
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxSize(),
            state = listState
        ) {
            items((maxScaleMagnitude downTo -maxScaleMagnitude).toList()) { index ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(spacingRegular),
                    modifier = Modifier.padding(end = spacingRegular)
                ) {
                    Divider(
                        modifier = Modifier.weight(1f),
                        color = strokeColor,
                        thickness = strokeWidth
                    )
                    Text(text = "${index.absoluteValue}", style = PfdTheme.typography.regular)
                }
            }
        }
        val offset = (verticalSpeed / maxScaleMagnitude).coerceIn(-1f, 1f)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = -(maxHeight - maxHeight / (maxScaleMagnitude * 2 + 1)) / 2f * offset),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .rotate(-90f)
                    .clip(TriangleShape)
                    .background(color = Color.White)
            )
            DigitCounter(
                value = verticalSpeed,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth(),
                decimalCount = 1,
                absoluteValue = true
            )
        }
    }
}
