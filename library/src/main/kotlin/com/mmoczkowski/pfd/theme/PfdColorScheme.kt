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

data class PfdColors(
    val groundColor: Color,
    val skyColor: Color,
    val indicatorColor: Color,
    val bgColor: Color,
    val courseColor: Color,
    val trackColor: Color
)

fun defaultPfdColorScheme(
    groundColor: Color = Color(0xFFA52A2A),
    skyColor: Color = Color.Blue,
    indicatorColor: Color = Color.White,
    bgColor: Color = Color.White.copy(alpha = 0.3f),
    courseColor: Color = Color.Magenta,
    trackColor: Color = Color.Green
) = PfdColors(
    groundColor = groundColor,
    skyColor = skyColor,
    indicatorColor = indicatorColor,
    bgColor = bgColor,
    courseColor = courseColor,
    trackColor = trackColor
)

val LocalPfdColors = staticCompositionLocalOf { defaultPfdColorScheme() }
