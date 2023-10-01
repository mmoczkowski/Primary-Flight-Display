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

package com.mmoczkowski.pfd.sample

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.mmoczkowski.pfd.PrimaryFlightDisplay

fun main() = application {
    Window(
        title = "Primary Flight Display",
        state = rememberWindowState(size = DpSize(800.dp, 1024.dp)),
        onCloseRequest = ::exitApplication
    ) {
        MaterialTheme {

            val infiniteTransition = rememberInfiniteTransition()
            val roll: Float by infiniteTransition.animateFloat(
                initialValue = -5f,
                targetValue = 5f,
                animationSpec = infiniteRepeatable(
                    animation = tween(12000, easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse
                )
            )
            val altitude: Float by infiniteTransition.animateFloat(
                initialValue = 999f,
                targetValue = 1001f,
                animationSpec = infiniteRepeatable(
                    animation = tween(10000, easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse
                )
            )

            var previousAltitude by remember { mutableStateOf(altitude) }
            var previousTime by remember { mutableStateOf(System.currentTimeMillis()) }
            var verticalSpeed by remember { mutableStateOf(0f) }
            LaunchedEffect(altitude) {
                val currentTime = System.currentTimeMillis()
                val deltaTime = currentTime - previousTime
                val rawVerticalSpeed = ((altitude - previousAltitude) / (deltaTime / 1000f)) * 60f
                val smoothingFactor = 0.01f
                verticalSpeed = (1f - smoothingFactor) * verticalSpeed + smoothingFactor * rawVerticalSpeed
                previousTime = currentTime
                previousAltitude = altitude
            }

            val speed: Float by infiniteTransition.animateFloat(
                initialValue = 186f,
                targetValue = 175f,
                animationSpec = infiniteRepeatable(
                    animation = tween(10000, easing = FastOutSlowInEasing),
                    repeatMode = RepeatMode.Reverse
                )
            )
            val pitch by derivedStateOf { verticalSpeed * 2f }
            val heading by derivedStateOf { -roll * 2f }

            PrimaryFlightDisplay(
                roll = roll,
                pitch = pitch,
                heading = heading,
                track = 19f,
                course = 16f,
                altitude = altitude,
                targetAltitude = 1000,
                speed = speed,
                targetSpeed = 180,
                verticalSpeed = verticalSpeed
            )
        }
    }
}
