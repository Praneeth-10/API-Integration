package com.example.nycschools.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.example.nycschools.utils.Utils

@Preview
@Composable
fun Header(){
    Row(horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth(1f)
            .fillMaxHeight(0.06f)
            .background(Color.Blue)) {

        Text(text = Utils.APP_NAME,
            fontStyle = FontStyle.Italic,
            modifier = Modifier.align(Alignment.CenterVertically),

            color = Color.White)
    }
}


fun annotatedStringBuilder(normalText:String, boldText:String): AnnotatedString {
    return buildAnnotatedString {
        append(normalText)
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold,fontStyle = FontStyle.Italic)) {
            append(boldText)
        }
    }
}