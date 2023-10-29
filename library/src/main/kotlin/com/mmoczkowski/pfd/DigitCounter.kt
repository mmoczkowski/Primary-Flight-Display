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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.mmoczkowski.pfd.theme.PfdTheme
import kotlin.math.absoluteValue
import kotlin.math.pow

@Composable
internal fun DigitCounter(
    value: Float,
    modifier: Modifier = Modifier,
    strokeColor: Color = PfdTheme.colors.indicatorColor,
    strokeWidth: Dp = PfdTheme.dimensions.indicatorStrokeThickness,
    spacing: Dp = PfdTheme.dimensions.spacingRegular,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Center,
    decimalCount: Int = 2,
    absoluteValue: Boolean = false
) {
    LazyRow(
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .border(width = strokeWidth, color = strokeColor)
            .background(Color.Black)
            .padding(spacing)
            .clipToBounds()

    ) {
        val digitCount: Int = value.absoluteValue.toInt().toString().length

        items(count = digitCount) { index ->
            val order = 10f.pow(digitCount - index)
            val progress = (value / order) % 1f
            val value = if (absoluteValue) progress.absoluteValue else progress

            Digit(value = value, scaling = order * 1f)
        }

        items(decimalCount) { decimalIndex ->
            Digit(
                value = (value * 10f.pow(decimalIndex)) % 1f.pow(decimalIndex),
                textStyle = PfdTheme.typography.regular
            )
        }
    }
}

@Composable
private fun Digit(
    value: Float,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = PfdTheme.typography.large,
    scaling: Float = 1f
) {
    var height by remember { mutableStateOf(0) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .wrapContentSize()
            .onSizeChanged { intSize -> height = intSize.height },
    ) {
        val textSize = with(LocalDensity.current) { height.toDp() }

        val currentDigit = (value * 10f).toInt().absoluteValue
        val currentDigitProgress = ((value * 10f) % 1f).pow(scaling)
        Text(
            text = "$currentDigit",
            modifier = Modifier.offset(y = textSize * currentDigitProgress),
            style = textStyle,
        )

        val nextDigit = (currentDigit + 1).let { if (it > 9) 0 else it }
        val nextDigitProgress = currentDigitProgress - 1f
        Text(
            text = "$nextDigit",
            modifier = Modifier.offset(y = textSize * nextDigitProgress),
            style = textStyle,
        )

        val previousDigit = (currentDigit - 1).let { if (it < 0) 9 else it }
        val previousDigitProgress = currentDigitProgress + 1f
        Text(
            text = "$previousDigit",
            modifier = Modifier.offset(y = textSize * previousDigitProgress),
            style = textStyle,
        )
    }
}
