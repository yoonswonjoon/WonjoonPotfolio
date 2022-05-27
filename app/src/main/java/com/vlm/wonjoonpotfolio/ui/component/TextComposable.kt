package com.vlm.wonjoonpotfolio.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow
import com.vlm.wonjoonpotfolio.domain.ProjectStringType


@Composable
fun TextWithSubTile(
    modifier : Modifier = Modifier,
    text : String,
    color : Color = Color.Black,
    style : TextStyle = MaterialTheme.typography.h6,
    fontWeight :FontWeight = FontWeight.Bold,
    visible : Boolean = true
){
    if(visible){
        Text(
            modifier = modifier,
            text = text,
            style = style,
            color = color,
            fontWeight =fontWeight,
        )
    }
}


@Composable
fun TextWithMainBody(
    modifier : Modifier = Modifier,
    text : String?,
    color : Color = Color.Black,
    style : TextStyle = MaterialTheme.typography.body1,
    fontWeight :FontWeight = FontWeight.Normal,
    visible: Boolean = text != null
){
    if(visible){
        Text(
            modifier = modifier,
            text = text!!,
            style = style,
            color = color,
            fontWeight =fontWeight
        )
    }
}

@Composable
fun TextDescribeStacksApp(
    modifier : Modifier = Modifier,
    title : String = "",
    titleColor: Color = Color.Black,
    titleWeight: FontWeight = FontWeight.Bold,
    text : List<ProjectStringType>,
    color : Color = Color.Black,
    style : TextStyle = MaterialTheme.typography.body1,
    fontWeight :FontWeight = FontWeight.Normal,
    visible: Boolean = text.isNotEmpty(),
    onClick : (String?)->Unit
){
    if(visible){
        FlowRow() {
            Text(text = title,color = titleColor, fontWeight = titleWeight)
            for (i in text){
                when(i){
                    is ProjectStringType.LinkedString ->{
                        Surface(modifier = Modifier.padding(5.dp), shape = RoundedCornerShape(5.dp), color = RedColor){
                            ClickableText(text = AnnotatedString(i.name), onClick = { onClick(i.uri) } )
                        }
                    }
                    is ProjectStringType.NormalText ->{
                        Surface(modifier = Modifier.padding(5.dp), shape = RoundedCornerShape(5.dp)){
                            Text(text =i.name , color = Color.Gray)
                        }

                    }
                }
            }
        }
    }
}

