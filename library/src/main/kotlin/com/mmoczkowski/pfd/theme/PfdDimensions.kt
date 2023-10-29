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

package com.mmoczkowski.pfd.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class PfdDimensions(
    val indicatorStrokeThickness: Dp,
    val indicatorSizeLong: Dp,
    val indicatorSizeShort: Dp,
    val spacingRegular: Dp,
    val spacingLarge: Dp
)

fun defaultPfdDimensions(
    indicatorStrokeThickness: Dp = 2.dp,
    indicatorSizeLong: Dp = 16.dp,
    indicatorSizeShort: Dp = 8.dp,
    spacingRegular: Dp = 8.dp,
    spacingLarge: Dp = 32.dp
) = PfdDimensions(
    indicatorStrokeThickness = indicatorStrokeThickness,
    indicatorSizeLong = indicatorSizeLong,
    indicatorSizeShort = indicatorSizeShort,
    spacingRegular = spacingRegular,
    spacingLarge = spacingLarge
)

val LocalPfdDimensions = staticCompositionLocalOf { defaultPfdDimensions() }
