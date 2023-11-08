package com.poison.taskfusion.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.RealmUUID

class Goal: RealmObject {
    var id: RealmUUID = RealmUUID.random()
    var goalTitle: String = ""
    var goalDescription: String = ""
    var goalStatus: String = ""
}