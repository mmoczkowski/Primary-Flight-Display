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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mmoczkowski.pfd.theme.PfdColors
import com.mmoczkowski.pfd.theme.PfdDimensions
import com.mmoczkowski.pfd.theme.PfdTheme
import com.mmoczkowski.pfd.theme.PfdTypography
import com.mmoczkowski.pfd.theme.defaultPfdColorScheme
import com.mmoczkowski.pfd.theme.defaultPfdDimensions
import com.mmoczkowski.pfd.theme.defaultPfdTypography

@Composable
fun PrimaryFlightDisplay(
    roll: Float,
    pitch: Float,
    heading: Float,
    track: Float,
    course: Float,
    altitude: Float,
    targetAltitude: Int,
    speed: Float,
    targetSpeed: Int,
    verticalSpeed: Float,
    modifier: Modifier = Modifier,
    colors: PfdColors = defaultPfdColorScheme(),
    typography: PfdTypography = defaultPfdTypography(),
    dimensions: PfdDimensions = defaultPfdDimensions(),
    attitudeIndicator: @Composable (roll: Float, pitch: Float) -> Unit = { roll, pitch ->
        AttitudeIndicator(
            roll = roll,
            pitch = pitch,
            modifier = Modifier.clip(RoundedCornerShape(5))
        )
    },
    compass: @Composable (heading: Float, track: Float, course: Float) -> Unit = { heading, track, course ->
        Compass(
            heading = heading,
            track = track,
            course = course
        )
    },
    altitudeIndicator: @Composable (altitude: Float, targetAltitude: Int) -> Unit = { altitude, targetAltitude ->
        AltitudeTape(
            altitude = altitude,
            targetAltitude = targetAltitude
        )
    },
    speedIndicator: @Composable (speed: Float, targetSpeed: Int) -> Unit = { speed, targetSpeed ->
        SpeedTape(
            speed = speed,
            targetSpeed = targetSpeed
        )
    },
    verticalSpeedIndicator: @Composable (verticalSpeed: Float) -> Unit = { verticalSpeed ->
        VerticalSpeedIndicator(
            verticalSpeed = verticalSpeed
        )
    }
) {
    PfdTheme(
        colors = colors,
        typography = typography,
        dimensions = dimensions
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(
                space = 16.dp,
                alignment = Alignment.CenterHorizontally
            ),
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxSize()
                .background(color = Color.Black)
                .padding(16.dp),
        ) {
            Box(
                modifier = Modifier
                    .weight(0.6f)
                    .fillMaxHeight()
            ) {
                speedIndicator(speed, targetSpeed)
            }

            Column(
                modifier = Modifier.weight(2f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier.weight(0.6f)
                ) {
                    attitudeIndicator(roll, pitch)
                }
                Box(
                    modifier = Modifier.weight(0.4f)
                ) {
                    compass(heading, track, course)
                }
            }

            Box(
                modifier = Modifier
                    .weight(0.6f)
                    .fillMaxHeight()
            ) {
                altitudeIndicator(altitude, targetAltitude)
            }
            Box(
                modifier = Modifier
                    .weight(0.4f)
                    .fillMaxHeight(0.7f)
            ) {
                verticalSpeedIndicator(verticalSpeed)
            }
        }
    }
}
