package com.poison.taskfusion.viewModel

import androidx.lifecycle.ViewModel
import com.poison.taskfusion.database.Database
import com.poison.taskfusion.model.User
import io.realm.kotlin.ext.query

class UserViewModel: ViewModel() {

    fun createAccount(
        inputFullName: String,
        inputEmail: String,
        inputPassword: String
    ) {
        Database.realm.writeBlocking {
            this.copyToRealm(
                User().apply {
                    fullName = inputFullName
                    email = inputEmail
                    password = inputPassword
                }
            )
        }
    }

    fun loginAccount(
        email: String,
        password: String
    ): Boolean {
        val user: User? = Database.realm.query<User>().first().find()
        return user?.email == email && user.password == password
    }

    fun getUserFullName(): String {
        val user = Database.realm.query<User>().first().find()
        return user?.fullName.toString()
    }
}