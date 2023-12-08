package com.poison.taskfusion.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Flag
import androidx.compose.material.icons.outlined.TaskAlt
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.poison.taskfusion.R
import com.poison.taskfusion.model.Goal
import com.poison.taskfusion.model.Task
import com.poison.taskfusion.viewModel.GoalViewModel
import com.poison.taskfusion.viewModel.TaskViewModel
import com.poison.taskfusion.viewModel.UserViewModel

@Composable
fun HomeScreenHeader(
    fullName: String
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.user1),
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
        )
        Spacer(modifier = Modifier.width(15.dp))
        Column {
            Text(
                text = "Hello, $fullName",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF000000)
            )
            Text(
                text = "Let's get productive",
                fontSize = 14.sp,
                fontWeight = FontWeight.Light,
                color = Color(0xFF000000)
            )
        }
    }
}

@Composable
fun Menu(
    navigateToTaskManagement: () -> Unit,
    navigateToGoalSetting: () -> Unit,
    navigateToTimeTracking: () -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFFFCA311)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {

        // task management menu
        Box(
            modifier = Modifier
                .padding(10.dp)
                .size(width = 60.dp, height = 60.dp)
                .clip(
                    RoundedCornerShape(15.dp)
                )
                .clickable {
                    navigateToTaskManagement()
                },
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    Icons.Outlined.Flag,
                    contentDescription = null,
                    tint = Color(0xFF14213D)
                )
                Text(
                    text = "Task",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF000000)
                )
            }
        }

        // goal setting menu
        Box(
            modifier = Modifier
                .padding(10.dp)
                .size(width = 60.dp, height = 60.dp)
                .clip(
                    RoundedCornerShape(15.dp)
                )
                .clickable {
                    navigateToGoalSetting()
                },
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    Icons.Outlined.TaskAlt,
                    contentDescription = null,
                    tint = Color(0xFF14213D)
                )
                Text(
                    text = "Goal",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF000000)
                )
            }
        }

        // timer menu
        Box(
            modifier = Modifier
                .padding(10.dp)
                .size(width = 60.dp, height = 60.dp)
                .clip(
                    RoundedCornerShape(15.dp)
                )
                .clickable {
                    navigateToTimeTracking()
                },
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    Icons.Outlined.Timer,
                    contentDescription = null,
                    tint = Color(0xFF14213D)
                )
                Text(
                    text = "Timer",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF000000)
                )
            }
        }
    }
}

@Composable
fun TaskOverView(
    taskList: List<Task>,
    navigateToTaskManagement: () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 24.dp)
    ) {
        Text(
            text = "Your Task",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF000000)
        )
        if(taskList.isNotEmpty()){
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(15.dp)
            ){
                items(taskList){task ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 24.dp),
                        shape = RoundedCornerShape(15.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFE5E5E5)
                        )
                    ){
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp)
                        ) {
                            Text(
                                text = task.taskTitle,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF000000)
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Surface(
                                shape = RoundedCornerShape(15.dp),
                                color = Color(0xFFFFFFFF)
                            ) {
                                Text(
                                    text = task.taskPriority,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 14.sp,
                                    modifier = Modifier
                                        .padding(horizontal = 20.dp, vertical = 10.dp),
                                    color = Color(0xFF000000)
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Outlined.CalendarMonth,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.secondary
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = task.taskDate,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 14.sp,
                                    color = Color(0xFF000000)
                                )
                            }
                        }
                    }
                }
            }
        }else {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                TextButton(
                    onClick = {
                        navigateToTaskManagement()
                    }
                ) {
                    Text(
                        text = "Create new task",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFF000000)
                    )
                }
            }
        }
    }
}

@Composable
fun GoalOverView(
    goalList: List<Goal>,
    navigateToGoalSetting: () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 24.dp)
    ) {
        Text(
            text = "Your Goal",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF000000)
        )
        Spacer(modifier = Modifier.height(15.dp))
        if (goalList.isNotEmpty()){
            LazyRow {
                items(goalList){ goal ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 24.dp),
                        shape = RoundedCornerShape(15.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFE5E5E5)
                        )
                    ){
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp)
                        ) {
                            Text(
                                text = goal.goalTitle,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF000000)
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = goal.goalDescription,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Light,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                color = Color(0xFF000000)
                            )
                        }
                    }
                }
            }
        }else {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                TextButton(
                    onClick = {
                        navigateToGoalSetting()
                    }
                ) {
                    Text(
                        text = "Create new goal",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFF000000)
                    )
                }
            }
        }
    }
}

@Composable
fun HomeScreen(){
    val taskViewModel = TaskViewModel()
    val goalViewModel = GoalViewModel()
    val userViewModel = UserViewModel()
    val taskData by taskViewModel.taskData
    val goalData by goalViewModel.goalData
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "homeScreen"){
        composable("homeScreen"){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFFFFFFF))
            ) {
                HomeScreenHeader(
                    fullName = userViewModel.getUserFullName()
                )
                Menu(
                    navigateToTaskManagement = {
                        navController.navigate("taskManagementScreen")
                    },
                    navigateToGoalSetting = {
                        navController.navigate("goalSettingScreen")
                    },
                    navigateToTimeTracking = {
                        navController.navigate("timeTrackerScreen")
                    }
                )
                TaskOverView(
                    taskList = taskData,
                    navigateToTaskManagement = {
                        navController.navigate("taskManagementScreen")
                    }
                )
                GoalOverView(
                    goalList = goalData,
                    navigateToGoalSetting = {
                        navController.navigate("goalSettingScreen")
                    }
                )
            }
        }
        composable("taskManagementScreen"){
            TaskManagementScreen {
                navController.navigate("homeScreen")
            }
        }
        composable("goalSettingScreen"){
            GoalSetting {
                navController.navigate("homeScreen")
            }
        }
        composable("timeTrackerScreen"){
            TimeTrackerScreen()
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview(){
    HomeScreen()
}