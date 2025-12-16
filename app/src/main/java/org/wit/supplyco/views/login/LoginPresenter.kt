package org.wit.supplyco.views.login

import android.app.Activity
import org.wit.supplyco.main.MainApp
import org.wit.supplyco.models.UserRepo

class LoginPresenter(private val view: LoginView) {

    private val app: MainApp = view.application as MainApp
    private val repo: UserRepo = app.users

    fun doLogin(username: String, password: String) {
        if (username.isBlank() || password.isBlank()) {
            view.showError("Please enter both username and password!")
            return
        }

        val success = repo.loginUser(username, password)

        if (success) {
            view.showMessage("Login successful!")
            view.closeWithResult(Activity.RESULT_OK)
        } else {
            view.showError("Invalid credentials!")
        }
    }

    fun doSignup() {
        view.navigateToSignup()
    }

    fun doCancel() {
        view.closeWithResult(Activity.RESULT_CANCELED)
    }
}
