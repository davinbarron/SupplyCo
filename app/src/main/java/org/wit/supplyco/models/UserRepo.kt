package org.wit.supplyco.models

interface UserRepo {
    fun registerUser(user: UserModel): Boolean
}