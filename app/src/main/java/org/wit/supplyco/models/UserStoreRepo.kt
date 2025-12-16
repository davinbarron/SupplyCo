package org.wit.supplyco.models

class UserStoreRepo : UserRepo {

    private val users = mutableListOf<UserModel>()

    override fun registerUser(user: UserModel): Boolean {
        if (users.any { it.username == user.username }) return false
        users.add(user)
        return true
    }
}
