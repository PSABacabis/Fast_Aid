package com.pupdict.fastaid.ui.screens.learn

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pupdict.fastaid.EmergencyButton
import com.pupdict.fastaid.R
import com.pupdict.fastaid.utils.Routes

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun HeartAttackScreen(
    modifier: Modifier = Modifier,
    onNavigate: (route: String) -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            // TopAppBar content goes here
            TopAppBar(
                title = { Text(text = "Apply First Aid - Heart Attack") },
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
            HeartAttackContent()
        }
    }
}

@Composable
fun HeartAttackContent() {
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
                    painter = painterResource(id = R.drawable.heartattackpicemergency) ,
                    contentDescription = "Covid Photo",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                )
                Text(text = "Creator: andriano_cz | Credit: Getty Images/iStockphoto", style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.height(24.dp))
                Text(text = "Tip! You can tap the items below to indicate step progress", style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold))
                BoxContentTEST(modifier = Modifier , step = 1 , instruction = "The person may have persistent vice-like chest pain, or isolated unexplained discomfort in arms, neck, jaw, back, or stomach.")
                BoxContentTEST(modifier = Modifier , step = 2 , instruction = "Call 143 as soon as possible or get someone else to do it.")
                BoxContentTEST(modifier = Modifier , step = 3 , instruction = "The person must receive medical assistance as soon as possible because a heart attack is potentially very serious and needs immediate attention")
                BoxContentTEST(modifier = Modifier , step = 4 , instruction = "Make sure they are in a position that is comfortable for them (e.g. Sit them on the floor, learning against a wall or a chair)")
                EmergencyButton(number = "143")
            }
        }

    }
}

@Composable
fun BoxContentTEST(
    modifier: Modifier,
    step: Int,
    instruction: String
) {
    var isBackgroundPrimary by remember { mutableStateOf(false) }

    // Define the text color based on the background color
    val textColor = if (isBackgroundPrimary) {
        MaterialTheme.colorScheme.onPrimary
    } else {
        MaterialTheme.colorScheme.onSurface
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(
                if (isBackgroundPrimary) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface ,
                RoundedCornerShape(8.dp)
            )
            .pointerInput(Unit) {
                detectTapGestures { isBackgroundPrimary = !isBackgroundPrimary }
            }
            .clickable(
                onClick = { isBackgroundPrimary = !isBackgroundPrimary } ,
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = step.toString(),
                style = MaterialTheme.typography.displaySmall.copy(color = textColor),
                textAlign = TextAlign.Center
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
                .weight(5f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = instruction,
                style = MaterialTheme.typography.bodyLarge,
                color = textColor
            )
        }
    }
}
