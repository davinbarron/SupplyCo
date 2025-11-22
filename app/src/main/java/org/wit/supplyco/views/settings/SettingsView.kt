package org.wit.supplyco.views.settings

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import org.wit.supplyco.databinding.ActivitySettingsBinding

class SettingsView : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var presenter: SettingsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarSettings)

        presenter = SettingsPresenter(this)

        binding.buttonDeleteAllSuppliers.setOnClickListener {
            presenter.doDeleteAllSuppliers()
        }
    }

    // Methods the presenter can call back into
    fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun showError(error: String) {
        Snackbar.make(binding.root, error, Snackbar.LENGTH_LONG).show()
    }

    fun close() {
        setResult(RESULT_OK)
        finish()
    }
}