package org.wit.supplyco.views.base

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import org.wit.supplyco.R
import org.wit.supplyco.views.login.LoginView
import org.wit.supplyco.views.supplierlist.SupplierListView

/*
 * BaseDrawerActivity is an abstract class to centralise Navigation Drawer setup.
 */
abstract class BaseDrawerActivity : AppCompatActivity() {
    protected lateinit var drawerLayout: DrawerLayout
    protected lateinit var navigationView: NavigationView

    protected fun setupDrawer(layoutResId: Int) {
        setContentView(layoutResId)

        drawerLayout = findViewById(R.id.drawer)
        navigationView = findViewById(R.id.navigationView)

        navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_suppliers -> startActivity(Intent(this, SupplierListView::class.java))
                R.id.nav_logout -> {
                    FirebaseAuth.getInstance().signOut()
                    startActivity(Intent(this, LoginView::class.java))
                    finish()
                }
            }
            drawerLayout.closeDrawers()
            true
        }
    }
}
