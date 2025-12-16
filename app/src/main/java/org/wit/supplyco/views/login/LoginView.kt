package org.wit.supplyco.views.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import org.wit.supplyco.databinding.ActivityLoginBinding
import org.wit.supplyco.views.signup.SignupView

class LoginView : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = LoginPresenter(this)

        binding.buttonLogin.setOnClickListener {
            presenter.doLogin(
                binding.editUsername.text.toString(),
                binding.editPassword.text.toString()
            )
        }

        binding.buttonRegister.setOnClickListener {
            presenter.doSignup()
        }
    }

    fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun showError(error: String) {
        Snackbar.make(binding.root, error, Snackbar.LENGTH_LONG).show()
    }

    fun closeWithResult(resultCode: Int) {
        setResult(resultCode)
        finish()
    }

    fun navigateToSignup() {
        val intent = Intent(this, SignupView::class.java)
        startActivity(intent)
    }
}
