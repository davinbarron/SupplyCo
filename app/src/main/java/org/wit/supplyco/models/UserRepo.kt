package org.wit.supplyco.models

interface UserRepo {
    fun registerUser(user: UserModel, callback: (Boolean, String?) -> Unit)
    fun loginUser(username: String, password: String, callback: (Boolean, String?) -> Unit)
}