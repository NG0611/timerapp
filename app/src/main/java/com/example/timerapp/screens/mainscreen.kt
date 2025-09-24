package com.example.timerapp.screens

import android.R.attr.fontWeight
import android.graphics.drawable.Icon
import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import com.example.timerapp.R


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation.Companion.keyboardOptions
import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay


@Composable
fun mainscreen(modifier: Modifier = Modifier) {
    var Progress by remember {mutableStateOf(1f)}
    var isrunning by remember {mutableStateOf(false)}
    var initialtime by remember {mutableStateOf("")}
    var remainingtime by remember {mutableStateOf(0)}

    fun formatTime(seconds: Int): String {
        val minutes = seconds / 60
        val secs = seconds % 60
        return String.format("%02d:%02d", minutes, secs)
    }
    if (isrunning) {
        LaunchedEffect(key1 = true) {
            while (remainingtime > 0 && isrunning) {
                delay(1000)
                remainingtime--
                val totalTime = initialtime.toFloatOrNull() ?: 1f
                Progress = remainingtime.toFloat() / totalTime
            }
            isrunning = false
        }
    }

    fun refresh(){
        remainingtime=initialtime.toIntOrNull()?:0
        Progress=1f
        isrunning=false


    }

    Column(modifier=Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center)
    {

        Text(text = "Enter the duration in seconds:", fontSize = 20.sp,fontWeight= FontWeight.Bold)
        Spacer(modifier= Modifier.height(20.dp))
        OutlinedTextField(value = initialtime,
                  onValueChange={
                     initialtime=it
                     remainingtime=initialtime.toIntOrNull() ?:0
                  },
                  label={Text(text="Duration")},
                  keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done)
                 )
        Spacer(modifier= Modifier.height(50.dp))

        Box(contentAlignment = Alignment.Center)
        {
            CircularProgressIndicator(
                progress = {Progress},
                strokeWidth = 10.dp,
                color = Color.Cyan,
                trackColor = Color.Gray,
                modifier= Modifier.size(200.dp)
            )
            Text(text = formatTime(remainingtime), fontSize = 32.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier= Modifier.height(15.dp))
        Row()
        {
            IconButton(onClick = {isrunning=!isrunning})
            {
                if(isrunning){
                    Icon(
                        painter = painterResource(id = R.drawable.icons8_pause_50),
                        contentDescription = null,
                        modifier=Modifier.size(23.dp)
                    )

                }
                else{

                    Icon(
                        painter = painterResource(id = R.drawable.icons8_play_50),
                        contentDescription = null,
                        modifier=Modifier.size(23.dp)

                    )
                }


            }
            IconButton(onClick = {refresh()})
            {
                Icon(
                    Icons.Default.Refresh,
                    contentDescription = null
                )
            }

        }
    }




}


@Preview(showBackground = true)
@Composable
fun mainscreenpreview() {
    mainscreen()

}