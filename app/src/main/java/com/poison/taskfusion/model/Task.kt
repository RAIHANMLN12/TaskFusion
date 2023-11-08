package com.poison.taskfusion.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.RealmUUID

class Task: RealmObject {
    var id: RealmUUID = RealmUUID.random()
    var taskTitle: String = ""
    var taskDescription: String = ""
    var taskDate: String = ""
    var taskPriority: String = ""
    var taskCategory: String = ""
    var taskStatus: String = ""
}