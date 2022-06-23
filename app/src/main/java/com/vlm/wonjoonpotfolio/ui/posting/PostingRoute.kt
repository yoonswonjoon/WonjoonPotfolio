package com.vlm.wonjoonpotfolio.ui.posting

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.vlm.wonjoonpotfolio.data.posting.Posting

@Composable
fun PostingRoute(
    postingViewModel: PostingViewModel,
    rememberLazyListState: LazyListState
)
{
    val viewState by postingViewModel.uiState.collectAsState()
    val items = postingViewModel.pagingData.collectAsLazyPagingItems()


//    derivedStateOf { rememberLazyListState.firstVisibleItemIndex }
    LazyColumn(state = if(items.itemCount>0 ) rememberLazyListState else rememberLazyListState()){
        if(viewState.postingList!=null){
//            items.loadState
        }

        item { Button(onClick = postingViewModel::upLoadPosting) {
            Text(text = "TestButton")
        } }


        itemsIndexed(items){ index, item->
            PostingItem(posting =item!!.toPosting(null))
        }
    }

//    Column() {
//        Row(horizontalArrangement = Arrangement.End,modifier = Modifier
//            .fillMaxWidth()
//            .padding(15.dp)) {
//            Button(onClick = { /*TODO*/ }) {
//                Text(text = "글쓰기")
//            }
//        }
//
//        LazyColumn(state = rememberLazyListState){
//            if(viewState.postingList!=null){
//
//            }
//
//            item { Button(onClick = postingViewModel::upLoadPosting) {
//                Text(text = "TestButton")
//            } }
//
//
//            itemsIndexed(items){ index, item->
//                PostingItem(posting =item!!.toPosting(null))
//            }
//        }
//    }

}


@Composable
fun PostingItem(posting: Posting){
    Text(text = posting.pid, modifier = Modifier
        .fillMaxWidth()
        .defaultMinSize(150.dp)
        .height(150.dp))
}
