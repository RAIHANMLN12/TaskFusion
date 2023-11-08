package com.poison.taskfusion.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TimeTrackerScreen(){
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(text = "Time Tracker Screen")
    }
}