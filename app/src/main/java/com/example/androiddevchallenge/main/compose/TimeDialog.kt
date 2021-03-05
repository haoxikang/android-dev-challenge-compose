package com.example.androiddevchallenge.main.compose

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.content.ContextCompat
import java.lang.Exception
import androidx.core.content.ContextCompat.getSystemService


enum class TimeDialogType(val maxNumber: Int) {
    TYPE_HOUR(Int.MAX_VALUE),
    TYPE_MIN(60),
    TYPE_SECOND(60),
    TYPE_DISMISS(-1)
}

@Composable
fun TimeDialog(type: TimeDialogType, current: String, onDismiss: (Int) -> Unit) {

    var currentNumber by remember { mutableStateOf(current.toInt()) }

    fun filterInput(s: String) {
        if (s.isEmpty()) currentNumber = 0
        try {
            val number = s.toInt()
            if (number <= type.maxNumber) {
                currentNumber = number
            }
        } catch (e: Exception) {
        }
    }
    Dialog(onDismissRequest = {
        onDismiss(currentNumber)
    }) {
        Card(
            shape = RoundedCornerShape(20),
            elevation = 8.dp,
            modifier = Modifier
                .requiredHeight(180.dp)
                .requiredWidth(240.dp)

        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
            ) {
                val textField = createRef()
                TextField(
                    value = currentNumber.toString(),
                    onValueChange = { s -> filterInput(s) },
                    modifier = Modifier.constrainAs(textField) {
                        start.linkTo(parent.start, 16.dp)
                        top.linkTo(parent.top, 16.dp)
                        end.linkTo(parent.end, 16.dp)
                        width = Dimension.fillToConstraints
                    }
                )
            }
        }
    }
}