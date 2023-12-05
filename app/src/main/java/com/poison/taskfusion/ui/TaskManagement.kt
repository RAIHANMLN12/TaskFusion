package com.poison.taskfusion.ui

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.poison.taskfusion.model.Task
import com.poison.taskfusion.viewModel.TaskViewModel
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox
import java.time.LocalDate
import java.time.format.DateTimeFormatter

var taskName = mutableStateOf("")

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskManagementScreen(
    backToPreviousScreen: () -> Unit
) {
    val taskViewModel = TaskViewModel()
    val taskData by taskViewModel.taskData
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "taskScreen"){
        composable("taskScreen"){
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = "Task Management",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal
                            )
                        },
                        navigationIcon = {
                            IconButton(
                                onClick = {
                                    backToPreviousScreen()
                                }
                            ) {
                                Icon(
                                    Icons.Rounded.ArrowBack,
                                    contentDescription = null
                                )
                            }
                        }
                    )
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {
                            navController.navigate("addNewTask")
                        },
                        contentColor = Color.Black,
                        modifier = Modifier
                            .padding(bottom = 90.dp)
                    ) {
                        Icon(
                            Icons.Rounded.Add,
                            contentDescription = "add new task icon",
                            modifier = Modifier
                                .size(24.dp)
                        )
                    }
                }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Spacer(modifier = Modifier.height(50.dp))
                    TaskSearchField()
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Your Task",
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(horizontal = 24.dp)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    if (taskName.value.isNotEmpty()) {
                        if (
                            taskData.isEmpty()
                        )Text(
                            text = "no match",
                            modifier = Modifier
                                .padding(horizontal = 24.dp)
                        )
                    }
                    if (taskData.isEmpty()){
                        Text(
                            text = "You don't have any task right now",
                            modifier = Modifier
                                .padding(horizontal = 24.dp)
                        )
                    }else {
                        TaskList(
                            taskList = taskData,
                            taskViewModel = taskViewModel,
                            navController = navController
                        )
                    }
                }
            }
        }
        composable("addNewTask") {
            AddNewTask(taskViewModel = taskViewModel) {
                navController.navigate("taskScreen")
            }
        }
    }
}

@Composable
fun TaskItemCard(
    taskViewModel: TaskViewModel,
    task: Task
) {
    var isClicked by remember {
        mutableStateOf(false)
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Surface(
                    shape = RoundedCornerShape(15.dp)
                ) {
                    Text(
                        text = task.taskPriority,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .padding(horizontal = 20.dp, vertical = 10.dp)
                    )
                }
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(shape = CircleShape)
                        .background(Color.White)
                        .clickable(
                            onClick = {
                                isClicked = !isClicked
                                if (isClicked) {
                                    taskViewModel.editTaskStatus(task, "done")
                                } else {
                                    taskViewModel.editTaskStatus(task, "")
                                }
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (task.taskStatus == "done") {
                        Icon(
                            Icons.Rounded.Check,
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier
                                .size(25.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = task.taskTitle,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(
                    Icons.Rounded.CalendarMonth,
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    text = task.taskDate,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light
                )
            }
        }
    }
}

@Composable
fun TaskList(
    taskList: List<Task>,
    taskViewModel: TaskViewModel,
    navController: NavController
){
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        contentPadding = PaddingValues(vertical = 24.dp)
    ) {
        if (taskList.isNotEmpty()) {
            items(taskList) {task ->
                val delete = SwipeAction(
                    onSwipe = {
                        taskViewModel.deleteTask(task)
                    },
                    icon = {
                        Icon(
                            Icons.Rounded.Delete,
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier
                                .size(30.dp)
                        )
                    },
                    background = Color.Transparent
                )
                SwipeableActionsBox(
                    endActions = listOf(delete)
                ) {
                    TaskItemCard(
                        taskViewModel = taskViewModel,
                        task = task
                    )
                }
            }
        }else {
            item {
                Text(
                    text = "You don't have any task right now",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun TaskSearchField() {
    val keyboardController = LocalSoftwareKeyboardController.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 20.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        OutlinedTextField(
            value = taskName.value,
            onValueChange = {
                taskName.value = it
            },
            trailingIcon = {
                Icon(
                    Icons.Rounded.Search,
                    contentDescription = "search icon",
                    tint = Color.Black
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black
            ),
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            ),
            placeholder = {
                Text(text = "Search Task")
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun AddNewTask(
    taskViewModel: TaskViewModel,
    backToPreviousScreen: () -> Unit
) {
    var newTaskName by remember {
        mutableStateOf("")
    }
    var newTaskDescription by remember {
        mutableStateOf("")
    }
    var newTaskDate by remember {
        mutableStateOf(LocalDate.now())
    }
    var newTaskPriority by remember {
        mutableStateOf("")
    }
    var newTaskCategory by remember {
        mutableStateOf("")
    }
    val taskPriorityOption = listOf(
        "Low", "Medium", "High"
    )
    var selectedIndex by remember {
        mutableStateOf(0)
    }
    val formattedDate by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("dd MMM yyyy")
                .format(newTaskDate)
        }
    }
    val dateDialogState = rememberMaterialDialogState()
    val categoryOption = listOf(
        "Work", "Personal", "Project", "Home", "Goal"
    )
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        // top bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            TextButton(
                onClick = {
                    backToPreviousScreen()
                }
            ) {
                Text(
                    text = "Cancel",
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Normal
                )
            }
            Text(
                text = "New Task",
                fontSize = 16.sp,
                color = Color.Black
            )
            TextButton(
                onClick = {
                    if(
                        newTaskName.isNotEmpty() &&
                        newTaskDescription.isNotEmpty() &&
                        newTaskCategory.isNotEmpty() &&
                        newTaskPriority.isNotEmpty()
                    ){
                        taskViewModel.createNewTask(
                            inputTaskTitle = newTaskName,
                            inputTaskDesc = newTaskDescription,
                            inputTaskCategory = newTaskCategory,
                            inputTaskDate = formattedDate,
                            inputTaskPriority = newTaskPriority
                        )
                        newTaskName = ""
                        newTaskDescription = ""
                        newTaskCategory = ""
                        newTaskPriority = ""
                        backToPreviousScreen()
                        Toast.makeText(context, "New task has created", Toast.LENGTH_LONG).show()
                    }else {
                        Toast.makeText(context, "Please fill the form", Toast.LENGTH_LONG).show()
                    }
                }
            ) {
                Text(
                    text = "Done",
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Normal
                )
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        // title input field
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            Text(
                text = "Title",
                fontSize = 16.sp,
                fontWeight = FontWeight.Light
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = newTaskName,
                onValueChange = {
                    newTaskName = it
                },
                placeholder = {
                    Text(text = "input task title")
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                    }
                ),
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier
                    .fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black
                )
            )
        }

        Spacer(modifier = Modifier.height(25.dp))

        // description input field
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            Text(
                text = "Description",
                fontSize = 16.sp,
                fontWeight = FontWeight.Light
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = newTaskDescription,
                onValueChange = {
                    newTaskDescription = it
                },
                placeholder = {
                    Text(text = "input task Description")
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                    }
                ),
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black
                )
            )
        }

        Spacer(modifier = Modifier.height(25.dp))

        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
        ) {
            Text(
                text = "Due Date",
                fontSize = 16.sp,
                fontWeight = FontWeight.Light
            )
            Spacer(modifier = Modifier.height(10.dp))
            Surface(
                modifier = Modifier
                    .fillMaxWidth(),
                border = BorderStroke(width = 1.dp, color = Color.Black),
                shape = RoundedCornerShape(10.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                ) {
                    IconButton(
                        onClick = {
                            dateDialogState.show()
                        }
                    ) {
                        Icon(
                            Icons.Rounded.CalendarMonth,
                            contentDescription = null,
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = formattedDate,
                        fontSize = 16.sp
                    )
                    MaterialDialog(
                        dialogState = dateDialogState,
                        buttons = {
                            positiveButton(text = "Ok")
                            negativeButton(text = "Cancel")
                        }
                    ){
                        datepicker(
                            initialDate = LocalDate.now(),
                            title = "Pick a date"
                        ) {
                            newTaskDate = it
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
        ) {
            Text(
                text = "Category",
                fontSize = 16.sp,
                fontWeight = FontWeight.Light
            )
            Spacer(modifier = Modifier.height(10.dp))
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(15.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                items(categoryOption) {
                    Card(
                        modifier = Modifier
                            .size(width = 70.dp, height = 50.dp)
                            .clickable {
                                newTaskCategory = it
                            }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(text = it)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        TabRow(
            selectedTabIndex = selectedIndex,
            containerColor = Color(0xff1E76DA),
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .clip(RoundedCornerShape(10.dp)),
            indicator = {},
            divider = {}
        ) {
            taskPriorityOption.forEachIndexed { index, text ->
                val selected = selectedIndex == index
                Tab(
                    modifier = if (selected) Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(
                            Color.White
                        )
                        .height(50.dp)
                    else Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(
                            Color(0xff1E76DA)
                        )
                        .height(50.dp),
                    selected = selected,
                    onClick = {
                        selectedIndex = index
                        newTaskPriority = text
                        Log.v("testing", "$newTaskPriority dipilih")
                    },
                    text = { Text(text = text, color = Color.Black) }
                )
            }
        }
    }
}



@Preview
@Composable
fun TaskManagementScreenPreview() {
    TaskManagementScreen(
        backToPreviousScreen = {}
    )
}