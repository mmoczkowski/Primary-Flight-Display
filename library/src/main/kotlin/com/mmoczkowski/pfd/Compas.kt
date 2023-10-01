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

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import com.mmoczkowski.pfd.theme.PfdTheme

@Composable
fun Compass(
    heading: Float,
    modifier: Modifier = Modifier,
    track: Float? = null,
    course: Float? = null,
    aircraftSymbol: @Composable () -> Unit = {
        Icon(
            painter = painterResource("img/ic_compass.svg"),
            contentDescription = null,
            tint = PfdTheme.colors.indicatorColor
        )
    },
    bgColor: Color = PfdTheme.colors.bgColor,
    courseColor: Color = PfdTheme.colors.courseColor,
    trackColor: Color = PfdTheme.colors.trackColor,
    strokeColor: Color = PfdTheme.colors.indicatorColor,
    strokeWidth: Dp = PfdTheme.dimensions.indicatorStrokeWidth,
    indicatorSize: Dp = PfdTheme.dimensions.indicatorSizeLong,
    spacing: Dp = PfdTheme.dimensions.spacingRegular
) {
    val strokeWidthPx = with(LocalDensity.current) { strokeWidth.toPx() }

    val headingCoerced = (heading % 360).let { value ->
        if (value < 0) {
            value + 360
        } else {
            value
        }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            AnimatedVisibility(visible = track != null) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = trackColor)) {
                            append("TRK ")
                        }
                        append("%03.0f°".format(track))
                    },
                    style = PfdTheme.typography.small,
                    modifier = Modifier
                        .background(bgColor)
                        .border(width = strokeWidth, color = strokeColor)
                        .padding(spacing)
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "%03.0f°".format(headingCoerced),
                    style = PfdTheme.typography.small,
                    modifier = Modifier
                        .background(bgColor)
                        .border(width = strokeWidth, color = strokeColor)
                        .padding(spacing)
                )
                Box(
                    modifier = Modifier
                        .size(indicatorSize)
                        .rotate(180f)
                        .clip(TriangleShape)
                        .background(color = strokeColor)
                )
            }
            AnimatedVisibility(visible = course != null) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = courseColor)) {
                            append("CRS ")
                        }
                        append("%03.0f°".format(course))
                    },
                    style = PfdTheme.typography.small,
                    modifier = Modifier
                        .background(PfdTheme.colors.bgColor)
                        .border(width = strokeWidth, color = strokeColor)
                        .padding(spacing)
                )
            }
        }

        BoxWithConstraints(
            modifier = Modifier
                .aspectRatio(1f)
                .background(color = bgColor, shape = CircleShape)
                .rotate(-headingCoerced)
                .drawWithContent {
                    (0 until 360 step 5).forEach { degrees ->
                        rotate(degrees = degrees.toFloat()) {
                            drawLine(
                                color = strokeColor,
                                start = Offset(
                                    x = center.x,
                                    y = size.height - if (degrees % 10 == 0) indicatorSize.toPx() else indicatorSize.toPx() / 2
                                ),
                                end = Offset(x = center.x, y = size.height),
                                strokeWidth = strokeWidthPx
                            )
                        }
                    }

                    if (course != null) {
                        rotate(degrees = course) {
                            drawLine(
                                color = courseColor,
                                start = Offset(
                                    x = center.x,
                                    y = indicatorSize.toPx()
                                ),
                                end = Offset(x = center.x, y = size.height - indicatorSize.toPx()),
                                strokeWidth = strokeWidthPx
                            )

                        }
                    }

                    if (track != null) {
                        rotate(degrees = track) {
                            drawLine(
                                color = trackColor,
                                start = Offset(
                                    x = center.x,
                                    y = indicatorSize.toPx()
                                ),
                                end = Offset(x = center.x, y = size.height - indicatorSize.toPx()),
                                strokeWidth = strokeWidthPx
                            )

                        }
                    }

                    drawContent()
                },
            contentAlignment = Alignment.Center
        ) {
            (0 until 360 step 10).forEach { degrees ->
                Text(
                    text = when (degrees) {
                        0 -> "N"
                        90 -> "E"
                        180 -> "S"
                        270 -> "W"
                        else -> "%02d".format(degrees / 10)
                    },
                    style = PfdTheme.typography.small,
                    modifier = Modifier
                        .rotate(degrees.toFloat())
                        .offset(indicatorSize - (maxHeight / 2))
                        .relativeOffset(x = 0.5f)
                )
            }

            if (course != null) {
                Box(
                    modifier = Modifier
                        .rotate(course)
                        .offset(y = -(maxHeight - indicatorSize) / 2)
                        .size(indicatorSize)
                        .clip(TriangleShape)
                        .background(color = courseColor)
                )
            }

            if (track != null) {
                Box(
                    modifier = Modifier
                        .rotate(track)
                        .offset(y = -(maxHeight - indicatorSize) / 2)
                        .size(indicatorSize)
                        .clip(TriangleShape)
                        .background(color = trackColor)
                )
            }

            Box(modifier = Modifier.rotate(headingCoerced)) {
                aircraftSymbol()
            }
        }
    }
}
