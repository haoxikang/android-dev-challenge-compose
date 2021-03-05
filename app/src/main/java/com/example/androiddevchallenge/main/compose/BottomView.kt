package com.example.androiddevchallenge.main.compose

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.ui.theme.MyTheme


@Composable
fun StartView(
    modifier: Modifier = Modifier,
    isCountingDown: Boolean = false,
    onClick: () -> Unit = {}
) {
    FloatingActionButton(
        onClick = onClick,
        backgroundColor = MaterialTheme.colors.secondary,
        elevation = FloatingActionButtonDefaults.elevation(16.dp, 32.dp),
        modifier = modifier
    ) {
        Icon(
            painter = if (isCountingDown)
                painterResource(id = R.drawable.ic_stop_filled)
            else painterResource(id = R.drawable.ic_play),
            contentDescription = null
        )
    }


}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun BottomLightPreview() {
    MyTheme {
        ConstraintLayout {
            StartView(
                modifier = Modifier
                    .width(64.dp)
                    .height(64.dp)
            )
        }
    }
}


@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun BottomDarkPreview() {
    MyTheme(darkTheme = true) {
        Surface(color = MaterialTheme.colors.background) {
            ConstraintLayout {
                StartView(
                    modifier = Modifier
                        .width(64.dp)
                        .height(64.dp)
                )
            }
        }
    }
}