package com.vlm.wonjoonpotfolio.ui.iAm

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vlm.wonjoonpotfolio.ui.component.DialogBasic

@Composable
fun ProjectEvaluationRoute(
    haveEvaluated : Boolean,
    evaluate : (Int) -> Unit
) {
    val (point,setPoint) = androidx.compose.runtime.remember {
        androidx.compose.runtime.mutableStateOf(0)
    }
    if(!haveEvaluated){
        Column(modifier = Modifier.fillMaxSize()) {
            EvaluationRow(max = 5, point = point, onClick = {
                setPoint(it)
            })
            Text(text = "평가를 마쳐야 평균 평점을 확인 할 수 있습니다")
            OutlinedButton(
                onClick = {

                }
            ) {
                Text(text = "평가하기")
            }
        }

    }else{

    }
}

@Composable
fun EvaluationRow(max : Int, point : Int, onClick: (Int) -> Unit){
    Row() {
        for (i in 1..max){
            if (i <= point){
                Icon(
                    modifier = Modifier
                        .clickable {
                            onClick(i)
                                   },
                    imageVector = Icons.Rounded.Star,
                    contentDescription = null,
                    tint = Color.Yellow)
            }else{
                Icon(
                    modifier = Modifier.clickable { onClick(i) },
                    imageVector = Icons.Rounded.Star,
                    contentDescription = null,
                )
            }

        }
    }
}

@Composable
fun NeedPointDialog(onDismiss : () -> Unit, onOk : () -> Unit){
//    DialogBasic(onDismiss = { /*TODO*/ }, onOk = { /*TODO*/ }, okText = , noText = "", justOkBtn = true) {
//        Text(text = "1개 이상의 점수를 주셔야 해요")
//    }
}

@Preview
@Composable
fun ProjectEvaluationRoutePreview(){
    ProjectEvaluationRoute(false,){

    }
}