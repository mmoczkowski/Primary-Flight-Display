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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

internal object PfdTheme {
    val colors: PfdColors
        @Composable
        get() = LocalPfdColors.current

    val typography: PfdTypography
        @Composable
        get() = LocalPfdTypography.current

    val dimensions: PfdDimensions
        @Composable
        get() = LocalPfdDimensions.current
}

@Composable
internal fun PfdTheme(
    colors: PfdColors,
    typography: PfdTypography,
    dimensions: PfdDimensions,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalPfdColors provides colors,
        LocalPfdTypography provides typography,
        LocalPfdDimensions provides dimensions,
        content = content
    )
}
