package com.vlm.wonjoonpotfolio.ui.iAm

import android.widget.EditText
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.vlm.wonjoonpotfolio.R
import com.vlm.wonjoonpotfolio.data.project.evaluate.ProjectEvaluateData
import com.vlm.wonjoonpotfolio.domain.ModifierSetting
import com.vlm.wonjoonpotfolio.ui.component.CircularProcessingDialog
import com.vlm.wonjoonpotfolio.ui.component.DialogBasic
import com.vlm.wonjoonpotfolio.ui.component.OutLinedBasicText
import com.vlm.wonjoonpotfolio.ui.component.TextWithSubTile

@Composable
fun ProjectEvaluationRoute(
    haveEvaluated : Boolean,
    evaluate : (Int,String) -> Unit,
    onCloseEvaluate : ()->Unit,
    processing  : Boolean,
    evaluateList : List<ProjectEvaluateData> = listOf(),
    totalPoint: Double =0.0,
    participantCount : Int = 0,
    reLoading: () -> Unit ,
    myEvaluate: ProjectEvaluateData = ProjectEvaluateData()
) {

    var backHandlingEnabled by remember { mutableStateOf(true) }
    BackHandler(backHandlingEnabled) {
        onCloseEvaluate()
    }

    val (msg, setMsg) = remember {
        mutableStateOf("")
    }

    val (point,setPoint) = remember {
        mutableStateOf(0)
    }

    //https://www.techiedelight.com/remove-whitespaces-string-kotlin/
    if(msg.length >200){
        setMsg(msg.dropLast(1))
    }

    if(!haveEvaluated){
        NotYetEvaluated(
            point = point,
            setPoint = setPoint,
            msg = msg,
            setMsg = setMsg,
            evaluate = evaluate
        )

    }else{
        AlreadyEvaluated(
            myEvaluate = myEvaluate,
            reLoading = { reLoading() },
            list = evaluateList,
            totalPoint = totalPoint,
            participantCount = participantCount
        )
    }

    if(processing){
        CircularProcessingDialog {

        }
    }
}

@Composable
fun EvaluationRow(max : Int, point : Int, onClick: (Int) -> Unit){
    Row(modifier = Modifier) {
        for (i in 1..max){
            if (i <= point){
                Icon(
                    modifier = Modifier
                        .size(40.dp)
                        .clickable {
                            onClick(i)
                        },
                    imageVector = Icons.Rounded.Star,
                    contentDescription = null,
                    tint = Color.Yellow,

                )
            }else{
                Icon(
                    modifier = Modifier
                        .clickable { onClick(i) }
                        .size(40.dp),
                    imageVector = Icons.Rounded.Star,
                    contentDescription = null,
                )
            }

        }
    }
}
@Composable
fun NotYetEvaluated(
    point: Int,
    setPoint : (Int) -> Unit,
    msg : String,
    setMsg : (String) -> Unit,
    evaluate: (Int, String) -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        EvaluationRow(
            max = 5,
            point = point,
            onClick = {
                setPoint(it)
            }
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(text = stringResource(id = R.string.need_to_evaluate))
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "피드백을 남겨주세요(200자 이내)",

            )

        TextField(
            value = msg,
            onValueChange = setMsg,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            colors = TextFieldDefaults.textFieldColors(
                disabledIndicatorColor = Color.Transparent
            )
        )

        Spacer(modifier = Modifier.height(15.dp))
        OutlinedButton(
            onClick = {
                evaluate(point,msg)
            }
        ) {
            Text(text = "평가하기")
        }
    }
}

@Composable
fun AlreadyEvaluated(
    totalPoint: Double,
    participantCount : Int,
    reLoading : () -> Unit,
    myEvaluate : ProjectEvaluateData,
    list : List<ProjectEvaluateData>
){
    val rememberLazyListState = rememberLazyListState()
    LazyColumn(
        state = rememberLazyListState,
        contentPadding = PaddingValues(ModifierSetting.HORIZONTAL_SPACE),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        item{
            ProjectEvaluatePointShower(
                totalPoint = totalPoint,
                participantCount = participantCount,
                myEvaluate = myEvaluate
            )
        }

        item{
           TextWithSubTile(text = "평가 리스트")
        }
        items(list){
            ProjectEvaluateItem(it)
        }
    }
}

@Composable
fun PointStarItem(
    sizeInInt : Int = 40,
    point: Double
){
    Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
        for (i in 1..point.toInt()){
            Icon(
                modifier = Modifier
                    .size(sizeInInt.dp),
                imageVector = Icons.Rounded.Star,
                contentDescription = null,
                tint = Color.Yellow,
                )
        }
        if( point - point.toInt() > 0){
           val blockDp =  (sizeInInt * (1- (point - point.toInt())) / 1).dp
            Box(modifier = Modifier.size(sizeInInt.dp), contentAlignment = Alignment.BottomEnd) {
                Icon(
                    modifier = Modifier
                        .size(sizeInInt.dp),
                    imageVector = Icons.Rounded.Star,
                    contentDescription = null,
                    tint = Color.Yellow,
                )
                Row(modifier = Modifier
                    .fillMaxHeight()
                    .width(blockDp)
                    .background(Color.White)) {

                }
            }
        }
        
        Text(text = point.toString())
    }
}

@Composable
fun ProjectEvaluatePointShower(
    totalPoint: Double,
    participantCount : Int,
    myEvaluate: ProjectEvaluateData
){
    val blockEid = try{
        myEvaluate.eid.replaceRange(3,myEvaluate.eid.length,"*****")
    }catch (e:Exception){
        "error"
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally,modifier =Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            PointStarItem(point = totalPoint, sizeInInt = 50)
            Text(text = "($participantCount)")
        }
        Spacer(modifier = Modifier.height(15.dp))
        OutLinedBasicText{
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()){
                Text(text = blockEid)
                Spacer(modifier = Modifier.width(10.dp))
                PointStarItem(point = myEvaluate.point.toDouble(), sizeInInt = 20)
            }
            if(myEvaluate.msg!= null){
                Divider()
                Text(text = myEvaluate.msg, textAlign = TextAlign.Center,modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@Composable
fun ProjectEvaluateItem(
    evaluateData: ProjectEvaluateData
){
    val blockEid = evaluateData.eid.replaceRange(3,evaluateData.eid.length,"*****")
    Card(
        modifier = Modifier.padding(8.dp),
        shape = RoundedCornerShape(10.dp),
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 100.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()){
                Text(text = blockEid)
                Spacer(modifier = Modifier.width(10.dp))
                PointStarItem(point = evaluateData.point.toDouble(),sizeInInt = 20)
            }

            if(evaluateData.msg!= null){
                Divider()
                Text(text = evaluateData.msg, textAlign = TextAlign.Center,modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(10.dp))
            }
        }

    }
}


//@Preview
//@Composable
//fun ProjectEvaluationRoutePreview(){
//    ProjectEvaluationRoute(false, onCloseEvaluate = {}, evaluate = { s,i ->
//
//    },
//    processing = true,
//
//    )
//}
@Preview
@Composable
fun PointStartItemPreview(){
    Row(modifier = Modifier.background(Color.White)) {
        PointStarItem(point = 3.6)
    }
}

//@Preview
//@Composable
//fun PointStartItemPreview2(){
//    ProjectEvaluatePointShower(
//        3.7,
//        ProjectEvaluateData(
//            eid = "doojoons@naver.com" ,
//                    point = 3,
//                    msg = "null",
//        )
//    )
//}

//class CustomShape : Shape {
//    override fun createOutline(
//        size: Size,
//        layoutDirection: LayoutDirection,
//        density: Density
//    ): Outline {
//        val path = Path().apply {
//            moveTo(size.width / 2f, 0f)
//            lineTo(size.width, size.height)
//            lineTo(0f, size.height)
//            close()
//        }
//        return Outline.Generic(path)
//    }
//}

//https://juliensalvi.medium.com/custom-shape-with-jetpack-compose-1cb48a991d42