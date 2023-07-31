package com.pupdict.fastaid.ui.components.learn

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.ContentAlpha
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalMaterial3Api
@Composable
fun ExpandableCard(
    modifier: Modifier = Modifier,
    title: String?,
    description: AnnotatedString,
    postNoteType: String,
    postNote: String?,
    titleStyle: TextStyle = MaterialTheme.typography.titleLarge,
    descriptionStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    descriptionTextAlign: TextAlign = TextAlign.Justify,
    ) {
    var expandedState by rememberSaveable { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f
    )
    Card(
        modifier = modifier
            .padding(20.dp)
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.surface)
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        onClick = {
            expandedState = !expandedState
        }, interactionSource = NoRippleInteractionSource()
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
                Text(
                    modifier = modifier
                        .padding(top = 10.dp),
                    text = description,
                    textAlign = descriptionTextAlign,
                    style = descriptionStyle,
                )
                postNote?.let {
                    PostNoteCard(description = it, postNoteType = postNoteType)
                }
            }
        }
    }
}

class NoRippleInteractionSource : MutableInteractionSource {

    override val interactions: Flow<Interaction> = emptyFlow()

    override suspend fun emit(interaction: Interaction) {}

    override fun tryEmit(interaction: Interaction) = true
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PreviewCard() {
    ExpandableCard(title = "test", description = buildAnnotatedString { append("test") } , postNoteType = "", postNote = null)
}

