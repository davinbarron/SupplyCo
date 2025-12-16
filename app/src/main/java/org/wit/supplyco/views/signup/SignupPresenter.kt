package org.wit.supplyco.views.signup

import android.app.Activity
import android.content.Intent
import org.wit.supplyco.main.MainApp
import org.wit.supplyco.models.UserModel
import org.wit.supplyco.models.UserRepo
import org.wit.supplyco.views.login.LoginView

class SignupPresenter(private val view: SignupView) {

    private val app: MainApp = view.application as MainApp
    private val repo: UserRepo = app.users

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

        repo.registerUser(user) { success, error ->
            if (success) {
                view.showMessage("You are now registered!!")
                // Navigate back to login after successful signup
                val intent = Intent(view, LoginView::class.java)
                view.startActivity(intent)
                view.finish()
            } else {
                view.showError(error ?: "Registration failed — user may already exist.")
            }
        }
    }

    fun doCancel() {
        view.closeWithResult(Activity.RESULT_CANCELED)
    }
}
