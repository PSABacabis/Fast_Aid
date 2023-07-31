package com.pupdict.fastaid.ui.screens.learn.medkit

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.ContentAlpha
import com.pupdict.fastaid.R
import com.pupdict.fastaid.ui.components.learn.NoRippleInteractionSource
import com.pupdict.fastaid.ui.components.learn.NormalText
import com.pupdict.fastaid.ui.components.learn.NumberedText
import com.pupdict.fastaid.utils.Routes

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedkitScreen(
    modifier: Modifier = Modifier,
    onNavigate: (route: String) -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            // TopAppBar content goes here
            TopAppBar(
                title = { Text(text = "Medkit - Epi-pen") },
                navigationIcon = {
                    IconButton(onClick = { onNavigate(Routes.FASTAID_MAINSCREEN) }) {
                        Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = null)
                    }
                },
            )
        }
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier.height(48.dp))
            EpipenScreenContent()
        }
    }
}

@Composable
fun EpipenScreenContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.epipen),
                    contentDescription = "Covid Photo",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                )
                Text(text = "A sample photo of an Epi-pen", style = MaterialTheme.typography.bodySmall)
            }
        }
        ExpandableContentCard1(title = "What is an Epi-pen?")
        ExpandableContentCard2(title = "How do you help someone to take an Epinephrine Auto-Injector?")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandableContentCard1(
    modifier: Modifier = Modifier,
    title: String?,
    titleStyle: TextStyle = MaterialTheme.typography.titleLarge,
) {
    var expandedState by rememberSaveable { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f
    )

    Card(
        modifier = modifier
            .padding(20.dp)
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300 ,
                    easing = LinearEasing
                )
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        onClick = { expandedState = !expandedState },
        interactionSource = NoRippleInteractionSource()
    ) {
        Column(
            modifier = modifier
                .padding(10.dp)
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.surface),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                title?.let {
                    Text(
                        modifier = modifier
                            .weight(6f),
                        text = it,
                        style = titleStyle.copy(fontWeight = FontWeight.SemiBold),
                        maxLines = 1,
                    )
                }
                IconButton(
                    modifier = modifier
                        .weight(1f)
                        .alpha(ContentAlpha.medium)
                        .rotate(rotationState),
                    onClick = {
                        expandedState = !expandedState
                    }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.KeyboardArrowDown,
                        contentDescription = "Drop-Down Arrow"
                    )
                }
            }
            if (expandedState) {

                Column(
                    modifier,
                    Arrangement.spacedBy(16.dp)
                ) {
                    NormalText(
                        text = "Epinephrine auto-injectors are devices that contain epinephrine (ep eh NEF rin). This medicine is used to treat severe allergic reactions called anaphylaxis (an uh ful LAK sis). When a child comes in contact with something they are allergic to, reactions usually happen fast, within 30 to 60 minutes."
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandableContentCard2(
    modifier: Modifier = Modifier,
    title: String?,
    titleStyle: TextStyle = MaterialTheme.typography.titleLarge,
) {
    var expandedState by rememberSaveable { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f
    )

    Card(
        modifier = modifier
            .padding(20.dp)
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300 ,
                    easing = LinearEasing
                )
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        onClick = { expandedState = !expandedState },
        interactionSource = NoRippleInteractionSource()
    ) {
        Column(
            modifier = modifier
                .padding(10.dp)
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.surface),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                title?.let {
                    Text(
                        modifier = modifier
                            .weight(6f),
                        text = it,
                        style = titleStyle.copy(fontWeight = FontWeight.SemiBold),
                        maxLines = 2,
                    )
                }
                IconButton(
                    modifier = modifier
                        .weight(1f)
                        .alpha(ContentAlpha.medium)
                        .rotate(rotationState),
                    onClick = {
                        expandedState = !expandedState
                    }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.KeyboardArrowDown,
                        contentDescription = "Drop-Down Arrow"
                    )
                }
            }
            if (expandedState) {

                Column(
                    modifier,
                    Arrangement.spacedBy(16.dp)
                ) {
                    Spacer(modifier = Modifier.height(12.dp))
                    NumberedText(number = 1 , title = "Remove the safety cap." , description = "Hold the auto-injector with the orange tip pointing downward and remove the safety cap by pulling it straight off. Encourage the person to avoid touching the orange tip.")
                    NumberedText(number = 2 , title = "Inject the epinephrine" , description = "With a quick, firm motion, firmly press the orange tip against the person's outer thigh, preferably the middle of the thigh. The auto-injector is designed to activate upon contact with the thigh, and it may make a clicking or hissing sound. Hold it in place for a few seconds, following the manufacturer's instructions." )
                }
            }
        }
    }
}
