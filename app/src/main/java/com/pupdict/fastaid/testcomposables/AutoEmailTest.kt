package com.pupdict.fastaid.testcomposables

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pupdict.fastaid.ui.components.learn.NormalText

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun OpenEmailer(receiver: String, subject: String, body: String) {
    val ctx = LocalContext.current

    NormalText(
        text = "Do you have suggestions? Or noticed a bug? Send us an email! Tap the button below.",
    )
    Button(
        modifier = Modifier
        .fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
        onClick = {
            val i = Intent(Intent.ACTION_SEND)
            val emailAddress = arrayOf(receiver)
            i.putExtra(Intent.EXTRA_EMAIL,emailAddress)
            i.putExtra(Intent.EXTRA_SUBJECT,subject)
            i.putExtra(Intent.EXTRA_TEXT,body)
            i.setType("message/rfc822")
            ctx.startActivity(Intent.createChooser(i,"Choose an Email client : "))
        }) {
        Text(
            text = "Send an Email to the Fast Aid Team!",
            modifier = Modifier.padding(10.dp),
            color = Color.White,
            fontSize = 15.sp
        )
    }
}

