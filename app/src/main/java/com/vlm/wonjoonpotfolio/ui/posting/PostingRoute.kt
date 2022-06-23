package com.vlm.wonjoonpotfolio.ui.posting

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.vlm.wonjoonpotfolio.data.posting.Posting

@Composable
fun PostingRoute(
    postingViewModel: PostingViewModel
)
{
    val viewState by postingViewModel.uiState.collectAsState()
    val items = postingViewModel.items.collectAsLazyPagingItems()

    LazyColumn(){
        if(viewState.postingList!=null){

        }

        item { Button(onClick = postingViewModel::upLoadPosting) {
            Text(text = "TestButton")
        } }


        itemsIndexed(items){ index, item->
            PostingItem(posting =item!!.toPosting(null))
        }
    }
}


@Composable
fun PostingItem(posting: Posting){
    Text(text = posting.pid, modifier = Modifier
        .fillMaxWidth()
        .defaultMinSize(150.dp)
        .height(150.dp))
}
