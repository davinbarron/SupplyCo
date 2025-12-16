package org.wit.supplyco.views.base

import android.content.Intent
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
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
    private lateinit var drawerToggle: ActionBarDrawerToggle

    protected fun setupDrawer(contentView: View, toolbar: Toolbar) {
        setContentView(contentView)

        drawerLayout = findViewById(R.id.drawer)
        navigationView = findViewById(R.id.navigationView)

        setSupportActionBar(toolbar)

        drawerToggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
