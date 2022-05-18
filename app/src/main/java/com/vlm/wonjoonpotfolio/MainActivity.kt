package com.vlm.wonjoonpotfolio

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.google.firebase.firestore.FirebaseFirestore
import com.vlm.wonjoonpotfolio.data.iAm.GetIAmDataUseCase
import com.vlm.wonjoonpotfolio.data.iAm.IAm
import com.vlm.wonjoonpotfolio.data.iAm.IAmRepository
import com.vlm.wonjoonpotfolio.ui.iAm.IAmViewModel
import com.vlm.wonjoonpotfolio.ui.theme.WonjoonPotfolioTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PortfolioMain()

        }
    }
}