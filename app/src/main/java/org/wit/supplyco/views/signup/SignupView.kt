package org.wit.supplyco.views.signup

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import org.wit.supplyco.databinding.ActivityRegistrationBinding
import org.wit.supplyco.views.login.LoginView

class SignupView : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding
    lateinit var presenter: SignupPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = SignupPresenter(this)

        binding.buttonRegisterSubmit.setOnClickListener {
            presenter.doRegister(
                binding.editRegisterUsername.text.toString(),
                binding.editRegisterEmail.text.toString(),
                binding.editRegisterPassword.text.toString(),
                binding.editRegisterConfirmPassword.text.toString()
            )
        }

        binding.buttonBackToLogin.setOnClickListener {
            val intent = Intent(this, LoginView::class.java)
            startActivity(intent)
            finish()
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
}
