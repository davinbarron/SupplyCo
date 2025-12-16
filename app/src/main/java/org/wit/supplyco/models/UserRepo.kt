package org.wit.supplyco.models

interface UserRepo {
    fun registerUser(user: UserModel): Boolean
    fun loginUser(username: String, password: String): Boolean
}