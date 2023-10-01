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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType

data class PfdTypography(
    val small: TextStyle,
    val regular: TextStyle,
    val large: TextStyle
)

fun defaultPfdTypography(
    small: TextStyle = TextStyle(
        color = Color.White,
        fontFamily = FontFamily(Font(resource = "fonts/b612_mono_regular.ttf")),
        fontSize = TextUnit(12f, TextUnitType.Sp)
    ),
    regular: TextStyle = TextStyle(
        color = Color.White,
        fontFamily = FontFamily(Font(resource = "fonts/b612_mono_regular.ttf")),
        fontSize = TextUnit(16f, TextUnitType.Sp)
    ),
    large: TextStyle = TextStyle(
        color = Color.White,
        fontFamily = FontFamily(Font(resource = "fonts/b612_mono_regular.ttf")),
        fontSize = TextUnit(20f, TextUnitType.Sp)
    )
): PfdTypography = PfdTypography(
    small = small,
    regular = regular,
    large = large
)

val LocalPfdTypography = staticCompositionLocalOf { defaultPfdTypography() }
