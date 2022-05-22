package com.vlm.wonjoonpotfolio.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


@Composable
fun TextWithSubTile(
    modifier : Modifier = Modifier,
    text : String,
    color : Color = Color.Black,
    style : TextStyle = MaterialTheme.typography.h6,
    fontWeight :FontWeight = FontWeight.Bold
){
    Text(
        modifier = modifier,
        text = text,
        style = style,
        color = color,
        fontWeight =fontWeight,
    )
}


@Composable
fun TextWithMainBody(
    modifier : Modifier = Modifier,
    text : String,
    color : Color = Color.Black,
    style : TextStyle = MaterialTheme.typography.body1,
    fontWeight :FontWeight = FontWeight.Normal
){
    Text(
        modifier = modifier,
        text = text,
        style = style,
        color = color,
        fontWeight =fontWeight
    )
}