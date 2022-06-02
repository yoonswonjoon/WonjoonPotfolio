package com.vlm.wonjoonpotfolio.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog


@Composable
fun DialogBasic(
    onDismiss: () -> Unit,
    onOk: () -> Unit,
    justOkBtn: Boolean = false,
    okText: String,
    noText: String,
    composable: @Composable ColumnScope.() -> Unit,
) {

    Dialog(
        onDismissRequest = { onDismiss() },
    ) {
        Surface(
            modifier = Modifier,
            shape = RoundedCornerShape(10.dp),
        ) {
            Column(modifier = Modifier
                .background(Color.White)
                .padding(15.dp)) {
                composable()

                Row(horizontalArrangement = if(justOkBtn)Arrangement.End else Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()) {
                    if (!justOkBtn) {
                        TextButton(onClick = { onDismiss() },
                            modifier = Modifier.background(Color.Gray),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(text = noText, color = Color.Black)
                        }
                    }
                    Surface(
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        TextButton(onClick = { onOk() }, modifier = Modifier
                            .background(RedColor)
                            .clip(RoundedCornerShape(8.dp))) {
                            Text(text = okText, color = Color.White, modifier =  Modifier)
                        }
                    }

                }

            }
        }

    }


}