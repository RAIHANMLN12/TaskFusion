package com.poison.taskfusion.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.poison.taskfusion.model.Task
import com.poison.taskfusion.viewModel.TaskViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar

var tanggalDipilih = mutableStateOf(LocalDate.now().toString())
@SuppressLint("SimpleDateFormat")
@Composable
fun CalendarView() {
    Column {
        AndroidView(
            factory = {
                android.widget.CalendarView(it)
            },
            modifier = Modifier.wrapContentWidth(),
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
fun ListData(
    listData: List<Task>,
    taskViewModel: TaskViewModel
){
    LazyColumn {
        items(listData) {
            TaskItemCard(
                taskViewModel = taskViewModel,
                task = it
            )
        }
    }
}

@Composable
fun CalendarScreen(){
    val taskViewModel = TaskViewModel()
    val data by taskViewModel.taskDataCalendar

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        CalendarView()
        if(data.isEmpty()){
            Text(
                text = "You don't have any task at that time",
                modifier = Modifier
                    .padding(horizontal = 24.dp)
            )
        }else {
            ListData(listData = data, taskViewModel = taskViewModel)
        }
    }
}

@Preview
@Composable
fun CalendarScreenPreview(){
    CalendarScreen()
}