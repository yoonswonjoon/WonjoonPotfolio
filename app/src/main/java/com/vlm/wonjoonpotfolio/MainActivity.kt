package com.vlm.wonjoonpotfolio

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.firestore.FirebaseFirestore
import com.vlm.wonjoonpotfolio.data.iAm.IAm
import com.vlm.wonjoonpotfolio.ui.theme.WonjoonPotfolioTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

//    @Inject lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        firestore.collection("iam").document("basicInfo").get().addOnSuccessListener {
//            val name = it?.data?.get("name") ?: "who"
//            Toast.makeText(this,name.toString(),Toast.LENGTH_LONG).show()
//        }


        setContent {
            PortfolioMain()
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WonjoonPotfolioTheme {
        Greeting("Android")
    }
}