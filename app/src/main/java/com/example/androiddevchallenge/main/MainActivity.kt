/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.main

import android.os.Bundle
import android.view.View
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.androiddevchallenge.ui.theme.MyTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androiddevchallenge.main.compose.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                window.statusBarColor = MaterialTheme.colors.primary.toArgb()
                @Suppress("DEPRECATION")
                if (MaterialTheme.colors.surface.luminance() > 0.5f) {
                    window.decorView.systemUiVisibility = window.decorView.systemUiVisibility or
                            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                }
                MyApp()
            }
        }
    }
}

// Start building your app here!
@Composable
fun MyApp() {
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
        ) {
            val viewModel: MainViewModel = viewModel()
            var showDialogType by remember { mutableStateOf(TimeDialogType.TYPE_DISMISS) }
            val hour by viewModel.liveHour.observeAsState("0")
            val minute by viewModel.liveMinute.observeAsState("0")
            val second by viewModel.liveSecond.observeAsState("0")
            val isCountdown by viewModel.isCountingDown.observeAsState(false)
            val (timer, bottomView) = createRefs()
            createVerticalChain(timer, bottomView, chainStyle = ChainStyle.Packed)
            Timer(
                hString = hour, mString = minute, sString = second,
                modifier = Modifier.constrainAs(timer) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(bottomView.top, 84.dp)
                    end.linkTo(parent.end)
                }
            ) {
                showDialogType = it
            }
            if (showDialogType != TimeDialogType.TYPE_DISMISS) {
                val current = when (showDialogType) {
                    TimeDialogType.TYPE_DISMISS -> "0"
                    TimeDialogType.TYPE_HOUR -> hour
                    TimeDialogType.TYPE_MIN -> minute
                    TimeDialogType.TYPE_SECOND -> second
                }
                TimeDialog(type = showDialogType, current, onDismiss = {
                    when (showDialogType) {
                        TimeDialogType.TYPE_HOUR -> viewModel.liveHour.value = "$it"
                        TimeDialogType.TYPE_MIN -> viewModel.liveMinute.value = "$it"
                        TimeDialogType.TYPE_SECOND -> viewModel.liveSecond.value = "$it"
                        else -> {
                        }
                    }
                    showDialogType = TimeDialogType.TYPE_DISMISS

                })
            }

            StartView(
                modifier = Modifier.constrainAs(bottomView) {
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom, 32.dp)
                    end.linkTo(parent.end)
                    width = Dimension.value(64.dp)
                    height = Dimension.value(64.dp)
                },
                isCountdown
            ) {
                viewModel.startCounting()
            }
        }

    }
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        MyApp()
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        MyApp()
    }
}
