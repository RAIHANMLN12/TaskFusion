package com.poison.taskfusion.database

import com.poison.taskfusion.model.Goal
import com.poison.taskfusion.model.Task
import com.poison.taskfusion.model.User
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

object Database {
    private val config = RealmConfiguration.create(
        schema = setOf(
            User::class,
            Task::class,
            Goal::class
        )
    )
    val realm: Realm = Realm.open(config)
}