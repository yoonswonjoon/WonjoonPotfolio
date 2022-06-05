package com.vlm.wonjoonpotfolio.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow
import com.vlm.wonjoonpotfolio.domain.ProjectStringType
import com.vlm.wonjoonpotfolio.domain.addUriToString
import com.vlm.wonjoonpotfolio.domain.commaSplit
import com.vlm.wonjoonpotfolio.ui.theme.Shapes


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
    visible : Boolean = body != null && body.isNotEmpty(),
    bodyHorizontalPadding : Dp = 5.dp,
    titleBodySpace : Dp = 0.dp,
    composable: @Composable ColumnScope.() -> Unit
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

          Surface(modifier = Modifier.padding(bodyHorizontalPadding)) {
              composable()
          }
      }
  }
}

@Composable
fun TextDescribeStacksApp(
    modifier : Modifier = Modifier,
    text : Map<String,List<ProjectStringType>>,
    style : TextStyle = MaterialTheme.typography.body2,
    fontWeight :FontWeight = FontWeight.Normal,
    visible: Boolean = text.isNotEmpty(),
    onClick : (String?)->Unit
){
    if(visible){
        Column() {
            text.map {
                Row() {
                    Text(text = it.key, fontWeight = FontWeight.Bold, modifier = modifier)
                    FlowRow() {
                        it.value.forEach{ a->
                            when(a){
                                is ProjectStringType.LinkedString ->{
                                    Surface(modifier = Modifier.clickable { onClick(a.uri) }, shape = RoundedCornerShape(5.dp), color = a.antiColor) {
                                        Text(modifier =modifier, text =a.name , color = a.color, style = style)
                                    }

                                    Spacer(modifier = Modifier.width(3.dp))
                                }
                                is ProjectStringType.NormalText ->{
                                    Surface(modifier = modifier, shape = RoundedCornerShape(5.dp)){
                                        Text(text =a.name , color = Color.Gray, style = style)
                                    }

                                    Spacer(modifier = Modifier.width(3.dp))

                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun OutLinedBasicText(
    outLinedColor :Color =RedColor,
    outLinedDp : Dp = 1.dp,
    contentColor : Color = Color.White,
    composable: @Composable ColumnScope.() -> Unit
){
    Surface(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(outLinedColor)
            .padding(outLinedDp)
    ) {
        Column(modifier = Modifier.padding(5.dp).background(color = contentColor)) {
            composable()
        }
    }
}