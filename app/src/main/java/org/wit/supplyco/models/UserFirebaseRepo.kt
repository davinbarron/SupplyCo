package org.wit.supplyco.models

import com.google.firebase.auth.FirebaseAuth

// Firebase has some great easy to follow documentation on how to setup firebase authentication
// https://firebase.google.com/docs/auth/android/start

class UserFirebaseRepo(private val auth: FirebaseAuth) : UserRepo {

    override fun registerUser(user: UserModel, callback: (Boolean, String?) -> Unit) {
        auth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true, null)
                } else {
                    callback(false, task.exception?.message)
                }
            }
    }

    override fun loginUser(username: String, password: String, callback: (Boolean, String?) -> Unit) {
        auth.signInWithEmailAndPassword(username, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true, null)
                } else {
                    callback(false, task.exception?.message)
                }
            }
    }
}
