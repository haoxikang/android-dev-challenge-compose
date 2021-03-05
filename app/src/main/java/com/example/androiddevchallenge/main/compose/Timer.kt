package com.example.androiddevchallenge.main.compose

import androidx.compose.foundation.clickable
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.androiddevchallenge.ui.theme.MyTheme

@Composable
fun Timer(
    hString: String,
    mString: String,
    sString: String,
    modifier: Modifier = Modifier,
    onTimeClick: (TimeDialogType) -> Unit = {}
) {
    ConstraintLayout(modifier = modifier) {
        val (h, divider1, m, divider2, s) = createRefs()
        createHorizontalChain(
            h,
            divider1,
            m,
            divider2,
            s,
            chainStyle = ChainStyle.Packed
        )
        NumberRow(
            text = hString,
            Modifier
                .constrainAs(h) {
                    start.linkTo(parent.start, 16.dp)
                    top.linkTo(parent.top, 16.dp)
                    bottom.linkTo(parent.bottom, 16.dp)
                }
                .clickable {
                    onTimeClick(TimeDialogType.TYPE_HOUR)
                }
        )

        NumberRow(
            text = ":",
            modifier = Modifier.constrainAs(divider1) {
                top.linkTo(h.top)
                bottom.linkTo(h.bottom)
                start.linkTo(h.end, 16.dp)
            }
        )

        NumberRow(
            text = mString,
            Modifier
                .constrainAs(m) {
                    top.linkTo(h.top)
                    bottom.linkTo(h.bottom)
                }
                .clickable {
                    onTimeClick(TimeDialogType.TYPE_MIN)
                }
        )

        NumberRow(
            text = ":",
            modifier = Modifier.constrainAs(divider2) {
                top.linkTo(h.top)
                bottom.linkTo(h.bottom)
                start.linkTo(m.end, 16.dp)

            }
        )

        NumberRow(
            text = sString,
            Modifier
                .constrainAs(s) {
                    top.linkTo(h.top)
                    bottom.linkTo(h.bottom)
                }
                .clickable {
                    onTimeClick(TimeDialogType.TYPE_SECOND)
                }
        )
    }
}

@Composable
fun NumberRow(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        fontSize = 72.sp,
        modifier = modifier
    )
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        Surface(color = MaterialTheme.colors.background) {
            Timer("12", "27", "08")
        }
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        Surface(color = MaterialTheme.colors.background) {
            Timer("12", "27", "08")
        }
    }
}