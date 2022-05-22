package com.vlm.wonjoonpotfolio.ui.component

import android.graphics.drawable.Icon
import android.opengl.Visibility
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun FloatingAction(
    modifier : Modifier = Modifier,
    visibility: Boolean,
    icon : ImageVector = Icons.Rounded.ExitToApp,
    onClick : () -> Unit
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = visibility,
        exit = scaleOut(),
        enter = scaleIn()
    ) {
        FloatingActionButton(
            onClick = onClick,
        ) {
            Icon(imageVector = icon, contentDescription = null)
        }
    }
}