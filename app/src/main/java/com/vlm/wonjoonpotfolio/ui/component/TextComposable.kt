package com.vlm.wonjoonpotfolio.ui.component

import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.unit.Dp
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
fun TextTitleWithBodyVertical(
    modifier : Modifier = Modifier,
    title : String,
    titleColor : Color = Color.Black,
    titleStyle : TextStyle = MaterialTheme.typography.subtitle1,
    titleFontWeight :FontWeight = FontWeight.Bold,
    body : String? = null,
    bodyColor : Color = Color.Black,
    bodyStyle : TextStyle = MaterialTheme.typography.body1,
    bodyFontWeight :FontWeight = FontWeight.Normal,
    visible : Boolean = body != null && body.isNotEmpty(),
    bodyHorizontalPadding : Dp = 5.dp,
    titleBodySpace : Dp = 0.dp
){
  if(visible){
      Column() {
          Text(
              modifier = modifier,
              text = title,
              style = titleStyle,
              color = titleColor,
              fontWeight =titleFontWeight,
          )
          Spacer(modifier = Modifier.height(titleBodySpace))

          androidx.compose.material.Surface(modifier = Modifier.padding(bodyHorizontalPadding)) {
              Text(
                  modifier = modifier,
                  text = body!!,
                  style = bodyStyle,
                  color = bodyColor,
                  fontWeight =bodyFontWeight
              )
          }
      }
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

