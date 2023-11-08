package com.poison.taskfusion.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.RealmUUID

class User: RealmObject {
    var id: RealmUUID = RealmUUID.random()
    var fullName: String = ""
    var email: String = ""
    var password: String = ""
    var photoProfile: String = ""
}