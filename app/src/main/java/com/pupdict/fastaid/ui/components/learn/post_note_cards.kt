package com.pupdict.fastaid.ui.components.learn

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


val stroke = Stroke(width = 4f,
    pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
)

@Composable
fun PostNoteCard(
    modifier: Modifier = Modifier,
    postNoteType: String,
    description: String,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
            .background(color = MaterialTheme.colorScheme.surface)
            .drawBehind {
                when (postNoteType) {
                    "alert" -> {
                        drawRoundRect(
                            color = Color.Red,
                            style = stroke,
                            cornerRadius = CornerRadius(8.dp.toPx()
                            )
                        )
                    }
                    "notice" -> {
                        drawRoundRect(
                            color = Color.Red,
                            style = stroke,
                            cornerRadius = CornerRadius(8.dp.toPx()
                            )
                        )
                    }
                    else -> Unit
                }
            }
    ){
        Box(
            modifier = modifier
                .background(color = MaterialTheme.colorScheme.surface)
        ) {
            Text(
                modifier = modifier
                    .padding(20.dp),
                text = description,
                style = textStyle.copy(textAlign = TextAlign.Justify)
            )
        }
    }
}

