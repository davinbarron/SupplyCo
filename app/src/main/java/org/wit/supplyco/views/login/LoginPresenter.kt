package org.wit.supplyco.views.login

import android.app.Activity
import android.content.Intent
import org.wit.supplyco.main.MainApp
import org.wit.supplyco.models.UserRepo
import org.wit.supplyco.views.signup.SignupView
import org.wit.supplyco.views.supplierlist.SupplierListView

class LoginPresenter(private val view: LoginView) {

    private val app: MainApp = view.application as MainApp
    private val repo: UserRepo = app.users

    fun doLogin(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            view.showError("Please enter both email and password!")
            return
        }

        repo.loginUser(email, password) { success, error ->
            if (success) {
                view.showMessage("Login successful!")
                val intent = Intent(view, SupplierListView::class.java)
                view.startActivity(intent)
                view.finish()
            } else {
                view.showError(error ?: "Invalid credentials!")
            }
        }
    }


    fun doSignup() {
        val intent = Intent(view, SignupView::class.java)
        view.startActivity(intent)
    }

    fun doCancel() {
        view.closeWithResult(Activity.RESULT_CANCELED)
    }
}
