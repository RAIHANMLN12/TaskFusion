package com.poison.taskfusion.ui

import android.annotation.SuppressLint
import android.widget.Toast
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowBack
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.poison.taskfusion.model.Goal
import com.poison.taskfusion.viewModel.GoalViewModel
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox


var goalName = mutableStateOf("")

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoalSetting(
    backToPreviousScreen: () -> Unit
){
    val goalViewModel = GoalViewModel()
    val goalData by goalViewModel.goalData
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "goalScreen"){
        composable("goalScreen"){
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = "Goal Setting",
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
                            navController.navigate("addNewGoal")
                        },
                        contentColor = Color.Black
                    ) {
                        Icon(
                            Icons.Rounded.Add,
                            contentDescription = "add new goal icon",
                            modifier = Modifier
                                .size(24.dp)
                        )
                    }
                }
            ){
                Column {
                    Spacer(modifier = Modifier.height(50.dp))
                    GoalSearchField()
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Your Goal",
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(horizontal = 24.dp)
                    )
                    if (goalName.value.isNotEmpty() && goalData.isEmpty()) {
                        Text(
                            text = "no match",
                            modifier = Modifier
                                .padding(horizontal = 24.dp)
                        )
                    }
                    GoalListItem(
                        goalList = goalData,
                        goalViewModel = goalViewModel,
                        navController = navController
                    )
                }
            }
        }
        composable("goalDetail/{id}") { backStackEntry ->
            val goalTitle = backStackEntry.arguments?.getString("id")
            val goal = goalData.firstOrNull { it.id.toString() == goalTitle }
            if (goal != null) {
                GoalDetail(goal = goal) {
                    navController.navigate("goalScreen")
                }
            } else {
                Text(text = "There Is No Task Here")
            }
        }
        composable("addNewGoal"){
            AddNewGoal(goalViewModel = goalViewModel) {
                navController.popBackStack()
            }
        }
    }
}

@Composable
fun GoalCardItem(
    goal: Goal,
    goalViewModel: GoalViewModel,
    navController: NavController
){
    var isClicked by remember {
        mutableStateOf(false)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate("goalDetail/${goal.id}")
            },
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
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = goal.goalTitle,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                        .background(if (isClicked) Color.Green else Color.White)
                        .clickable {
                            isClicked = !isClicked
                            if (isClicked) {
                                goalViewModel.editGoalStatus(goal, "done")
                            } else {
                                goalViewModel.editGoalStatus(goal, "")
                            }
                        },
                    contentAlignment = Alignment.Center
                ){
                    if (isClicked) {
                        Icon(
                            Icons.Rounded.Check,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = goal.goalDescription,
                fontSize = 14.sp,
                fontWeight = FontWeight.Light,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun GoalListItem(
    goalList: List<Goal>,
    goalViewModel: GoalViewModel,
    navController: NavController
){
    val context = LocalContext.current

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        contentPadding = PaddingValues(vertical = 24.dp, horizontal = 20.dp)
    ) {
        if (goalList.isNotEmpty()){
            items(goalList){
                val delete = SwipeAction(
                    onSwipe = {
                        goalViewModel.deleteGoal(it)
                        Toast.makeText(context, "Deleted", Toast.LENGTH_LONG).show()
                    },
                    icon = {
                        Icon(
                            Icons.Rounded.Delete,
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier
                                .padding(horizontal = 15.dp)
                        )
                    },
                    background = Color.Transparent
                )
                SwipeableActionsBox(
                    endActions = listOf(delete)
                ) {
                    GoalCardItem(
                        goal = it,
                        goalViewModel = goalViewModel,
                        navController = navController
                    )
                }
            }
        }else {
            item {
                Text(
                    text = "You don't have any goal right now",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AddNewGoal(
    goalViewModel: GoalViewModel,
    backToPreviousScreen:() -> Unit
){
    var newGoalTitle by remember {
        mutableStateOf("")
    }
    var newGoalDescription by remember {
        mutableStateOf("")
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        // Top Bar App
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
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
                text = "New Goal",
                fontSize = 16.sp,
                color = Color.Black
            )
            TextButton(
                onClick = {
                    if (newGoalTitle.isNotEmpty() && newGoalDescription.isNotEmpty()){
                        goalViewModel.createNewGoal(
                            inputGoalTitle = newGoalTitle,
                            inputGoalDescription =  newGoalDescription
                        )
                        newGoalTitle = ""
                        newGoalDescription = ""
                        backToPreviousScreen()
                        Toast.makeText(context, "New goal has created", Toast.LENGTH_LONG)
                            .show()
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
        
        // goal title input field
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
                value = newGoalTitle,
                onValueChange = {
                    newGoalTitle = it
                },
                placeholder = {
                    Text("input goal title")
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
        
        // GOAL DESCRIPTION INPUT FIELD
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
                value = newGoalDescription,
                onValueChange = {
                    newGoalDescription = it
                },
                placeholder = {
                    Text(text = "input Goal Description")
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
    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun GoalSearchField(){
    val keyboardController = LocalSoftwareKeyboardController.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 20.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        OutlinedTextField(
            value = goalName.value,
            onValueChange = {
                goalName.value = it
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
                Text(text = "Search Goal")
            }
        )
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoalDetail(
    goal: Goal,
    backToPreviousScreen: () -> Unit
){
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Project Detail")
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
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(60.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
                Text(
                    text = goal.goalTitle,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
            ){
                Text(
                    text = goal.goalDescription,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}



@Preview
@Composable
fun GoaLSettingScreenPreview() {
    GoalSetting(
        backToPreviousScreen = {}
    )
}