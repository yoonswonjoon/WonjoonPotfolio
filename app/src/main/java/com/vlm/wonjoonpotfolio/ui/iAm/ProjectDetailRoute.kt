package com.vlm.wonjoonpotfolio.ui.iAm

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vlm.wonjoonpotfolio.ui.component.RedColor
import com.vlm.wonjoonpotfolio.ui.component.TextWithMainBody
import com.vlm.wonjoonpotfolio.ui.component.TextWithSubTile

@Composable
fun ProjectDetailRoute(
    viewModel: IAmViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()

    val scrollState = rememberScrollState()
    Column(
        Modifier
            .verticalScroll(scrollState)
            .fillMaxSize()
    ) {
        Row() { // 사진 , 기본기능
            Column() {
                TextWithMainBody(text = "다운로드 Uri")
                TextWithMainBody(text = "개발 기간")
                TextWithMainBody(text = "적용 스텍")
                TextWithMainBody(text = "제작인원")
                Row() {
                    
                }
            }
        }
        TextWithSubTile(text = "프로젝트 소개", color = RedColor, modifier = Modifier.padding(5.dp))
        TextWithSubTile(text = "관련 사진", color = RedColor,modifier = Modifier.padding(5.dp))
        TextWithSubTile(text = "힘들었던 점", color = RedColor,modifier = Modifier.padding(5.dp))
        Divider()
        TextWithSubTile(text = "관련 질문", color = RedColor,modifier = Modifier.padding(5.dp))
        
    }
}