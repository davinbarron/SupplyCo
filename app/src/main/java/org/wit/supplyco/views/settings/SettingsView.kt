package org.wit.supplyco.views.settings

import android.os.Bundle
import android.transition.TransitionInflater
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.snackbar.Snackbar
import org.wit.supplyco.R
import org.wit.supplyco.databinding.ActivitySettingsBinding
import org.wit.supplyco.views.base.BaseDrawerActivity

class SettingsView : BaseDrawerActivity() {

    private lateinit var binding: ActivitySettingsBinding
    lateinit var presenter: SettingsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setupDrawer(binding.root, binding.toolbarSettings)

        presenter = SettingsPresenter(this)

        // Decide mode based on intent
        val mode = intent.getStringExtra("settings_mode") ?: "suppliers"
        val supplierId = intent.getStringExtra("supplier_id")

        if (mode == "items" && supplierId != null) {
            binding.buttonDeleteAll.text = getString(R.string.delete_allItems)

            binding.buttonDeleteAll.setOnClickListener {
                presenter.doDeleteAllItems(supplierId)
            }
        } else {
            binding.buttonDeleteAll.text = getString(R.string.delete_allSuppliers)

            binding.buttonDeleteAll.setOnClickListener {
                presenter.doDeleteAllSuppliers()
            }
        }

        // https://developer.android.com/develop/ui/views/theming/darktheme
        val currentMode = AppCompatDelegate.getDefaultNightMode()
        binding.switchNightMode.isChecked = (currentMode == AppCompatDelegate.MODE_NIGHT_YES)

        //https://developer.android.com/reference/android/widget/CompoundButton.OnCheckedChangeListener
        binding.switchNightMode.setOnCheckedChangeListener { _, isChecked ->
            presenter.doToggleNightMode(isChecked)
        }

        if (savedInstanceState == null) animate()
    }

    fun animate() {
        // https://developer.android.com/develop/ui/views/animations/transitions#kotlin
        binding.root.post {
            val parent = binding.buttonDeleteAll.parent as ViewGroup

            val transition = TransitionInflater.from(this).inflateTransition(R.transition.scene_enter)

            transition.addTarget(binding.buttonDeleteAll)
            transition.addTarget(binding.switchNightMode)
            transition.addTarget(binding.toolbarSettings)

            TransitionManager.beginDelayedTransition(parent, transition)

            binding.buttonDeleteAll.visibility = View.VISIBLE
            binding.switchNightMode.visibility = View.VISIBLE
            binding.toolbarSettings.visibility = View.VISIBLE
        }
    }

    // Presenter callbacks
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
