package com.pupdict.fastaid.ui.components.learn

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.ContentAlpha


@Composable
fun NormalText(
    text: String ,
    modifier: Modifier = Modifier,
) {
    Spacer(modifier = modifier.height(16.dp))
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge.copy(textAlign = TextAlign.Justify)
    )
    Spacer(modifier = modifier.height(16.dp))
}


@Composable
fun NumberedText(
    number: Int,
    title: String,
    description: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(text = "$number. $title", style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Justify))
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = description, style = MaterialTheme.typography.bodyLarge.copy(textAlign = TextAlign.Justify))
    }
}

@Composable
fun BulletPointText(
    modifier: Modifier = Modifier,
    title: String? = null,
    text: String,
) {
    Column(modifier = modifier) {
        Text(
            text = if (title != null) "• $title" else "• $text",
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Justify))
        if (title == null) {
            Text(text = "", style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Justify))
        } else {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = text)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandableContentCardsTemplate(
    modifier: Modifier = Modifier ,
    title: String? ,
    titleStyle: TextStyle = MaterialTheme.typography.titleLarge ,
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
                //.background(Color.Red)
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

            }
        }
    }
}