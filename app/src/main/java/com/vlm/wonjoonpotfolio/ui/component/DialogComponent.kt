package com.vlm.wonjoonpotfolio.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog


@Composable
fun DialogBasic(
    onDismiss : () -> Unit,
    onOk: () ->Unit,
    justOkBtn : Boolean = false,
    okText: String,
    noText: String,
    composable : @Composable ColumnScope.() -> Unit
){
    Surface(
        modifier = Modifier.padding(8.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Dialog(
            onDismissRequest = { onDismiss() },

            ) {
            Column() {
                composable()

                Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                    if(!justOkBtn){
                        TextButton(onClick = { onDismiss() }, modifier = Modifier.background(Color.Gray)) {
                            Text(text = noText, color = Color.Black)
                        }
                    }
                    TextButton(onClick = {onOk()}, modifier = Modifier.background(RedColor)) {
                        Text(text = okText, color = Color.White)
                    }
                }

            }
        }
    }

}