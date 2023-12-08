package com.poison.taskfusion.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.poison.taskfusion.database.Database
import com.poison.taskfusion.model.Goal
import com.poison.taskfusion.model.Task
import com.poison.taskfusion.ui.goalName
import com.poison.taskfusion.ui.tanggalDipilih
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class GoalViewModel: ViewModel() {

    var goalData = mutableStateOf(emptyList<Goal>())

    init {
        viewModelScope.launch {
            fetchingGoalData(goalName.value).collect{
                goalData.value = it
            }
        }
    }

    fun createNewGoal(
        inputGoalTitle: String,
        inputGoalDescription: String
    ){
        Database.realm.writeBlocking {
            this.copyToRealm(
                Goal().apply {
                    goalTitle = inputGoalTitle
                    goalDescription = inputGoalDescription
                }
            )
        }
    }

    private fun fetchingGoalData(goalName: String): Flow<List<Goal>> {
        return if(goalName.isEmpty()){
            Database.realm.query<Goal>().asFlow().map { it.list }
        } else {
            Database.realm.query<Goal>("goalTitle == $0", goalName).asFlow().map { it.list }
        }
    }


    fun deleteGoal(goal: Goal) {
        Database.realm.writeBlocking {
            val goalToDelete: Goal = this.query<Goal>("id == $0", goal.id).find().first()
            delete(goalToDelete)
        }
    }

    fun editGoalStatus(goal: Goal, statusValue: String) {
        Database.realm.writeBlocking {
            val updateGoalStatus: Goal? = this.query<Goal>("id == $0", goal.id).find().first()
            updateGoalStatus?.goalStatus = statusValue
        }
    }
}