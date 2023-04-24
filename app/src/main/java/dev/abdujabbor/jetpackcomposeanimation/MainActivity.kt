package dev.abdujabbor.jetpackcomposeanimation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    MyUI()
                }
            }
        }
    }
}
@Composable
private fun MyUI() {

    val visibleState = remember {
        MutableTransitionState(false).apply {
            targetState = true // start the animation immediately
        }
    }

    AnimatedVisibility(
        visibleState = visibleState,
        enter = fadeIn() + slideInHorizontally(),
        exit = fadeOut() + slideOutHorizontally()
    ) {
        Column() {

            Text( text = "This is an image")

            Image(
                modifier = Modifier
                    .size(size = 120.dp)
                    .clip(shape = CircleShape),
                painter = painterResource(id = R.drawable.drawablekermit),
                contentDescription = "Dog",
                contentScale = ContentScale.Crop
            )
        }
    }
    Button(
        modifier = Modifier.padding(top = 8.dp),
        onClick = {
            visibleState.targetState = !visibleState.targetState
        }
    ) {
        Text(text = "Toggle Visibility")
    }

    // use the MutableTransitionState to know the current animation state
    Text(
        text = when {
            visibleState.isIdle && visibleState.currentState -> "Visible" // enter transition is completed
            !visibleState.isIdle && visibleState.currentState -> "Disappearing" // exit transition is running
            visibleState.isIdle && !visibleState.currentState -> "Invisible" // exit transition is completed
            else -> "Appearing" // enter transition is running
        }
    )
}