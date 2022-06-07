package com.vlm.wonjoonpotfolio.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties


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

                Spacer(modifier = Modifier.height(10.dp))
                Row(horizontalArrangement = if(justOkBtn)Arrangement.End else Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()) {
                    if (!justOkBtn) {
                        Surface(
                            shape = RoundedCornerShape(8.dp)
                        ){
                            TextButton(
                                onClick = { onDismiss() },
                                modifier = Modifier
                                    .background(Color.Gray)
                                    .clip(RoundedCornerShape(8.dp))
                            ) {
                                Text(text = noText, color = Color.Black)
                            }
                        }
                    }
                    Surface(
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        TextButton(
                            onClick = { onOk() },
                            modifier = Modifier
                                .background(RedColor)
                                .clip(RoundedCornerShape(8.dp))
                        ) {
                            Text(text = okText, color = Color.White, modifier =  Modifier)
                        }
                    }

                }

            }
        }

    }


}

@Composable
fun CircularProcessingDialog(onDismissRequest : () -> Unit){
    Dialog(
        onDismissRequest = {  },
        DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
//                .border((0.5).dp, Color.Red, roundedConerShapOption)
            ,
            contentAlignment = Alignment.Center
                //.background(ColorWhite)
        ){
//            Image(painter = painterResource(
//                id = R.drawable.ic_balloony),
//                modifier = Modifier.size(55.dp).align(Alignment.Center),
//                contentDescription = null )
            CircularProgressIndicator(modifier = Modifier.size(100.dp))
        }
    }
}