package org.wit.supplyco.views.signup

import android.app.Activity
import org.wit.supplyco.main.MainApp
import org.wit.supplyco.models.UserModel
import org.wit.supplyco.models.UserRepository

class SignupPresenter(private val view: SignupView) {

    private val app: MainApp = view.application as MainApp
    private val repo: UserRepository = app.users

    fun doRegister(username: String, email: String, password: String, confirmPassword: String) {
        if (username.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
            view.showError("Please fill in all required fields!")
            return
        }

        if (password != confirmPassword) {
            view.showError("Passwords do not match!")
            return
        }

        val user = UserModel(username, email, password)
        val success = repo.registerUser(user)

        if (success) {
            view.showMessage("You are now registered!!")
            view.closeWithResult(Activity.RESULT_OK)
        } else {
            view.showError("Registration failed — user may already exist.")
        }
    }

    fun doCancel() {
        view.closeWithResult(Activity.RESULT_CANCELED)
    }
}
