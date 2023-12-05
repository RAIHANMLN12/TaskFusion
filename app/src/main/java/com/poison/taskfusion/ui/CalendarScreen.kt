package com.poison.taskfusion.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.compose.rememberNavController
import com.poison.taskfusion.viewModel.TaskViewModel
import java.text.SimpleDateFormat
import java.util.Calendar

var tanggalDipilih = mutableStateOf("")

@SuppressLint("SimpleDateFormat")
@Composable
fun CalendarView() {
    Column(
        modifier = Modifier
            .padding(20.dp)
    ) {
        AndroidView(
            factory = {
                android.widget.CalendarView(it)
            },
            modifier = Modifier.fillMaxWidth(),
            update = { calendarView ->
                calendarView.setDate(Calendar.getInstance().timeInMillis, true, true)
                calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
                    val selectedDate = Calendar.getInstance().apply {
                        set(year, month, dayOfMonth)
                    }
                    tanggalDipilih.value = SimpleDateFormat("dd MMM yyyy").format(selectedDate.time)
                }
            }
        )
    }
}

@Composable
fun CalendarScreen(){
    val taskViewModel = TaskViewModel()
    val navController = rememberNavController()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        CalendarView()
        TaskList(
            taskList = taskViewModel.fetchingTaskDataByDate(),
            taskViewModel = taskViewModel,
            navController = navController
        )
    }
}

@Preview
@Composable
fun CalendarScreenPreview(){
    CalendarScreen()
}