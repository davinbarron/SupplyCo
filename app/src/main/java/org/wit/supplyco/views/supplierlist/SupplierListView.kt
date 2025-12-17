package org.wit.supplyco.views.supplierlist

import android.os.Bundle
import android.transition.TransitionInflater
import android.transition.TransitionManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.supplyco.R
import org.wit.supplyco.adapters.SupplierAdapter
import org.wit.supplyco.adapters.SupplierListener
import org.wit.supplyco.databinding.ActivitySupplierListBinding
import org.wit.supplyco.models.SupplierModel
import org.wit.supplyco.views.base.BaseDrawerActivity

class SupplierListView : BaseDrawerActivity(), SupplierListener {

    private lateinit var binding: ActivitySupplierListBinding
    lateinit var presenter: SupplierListPresenter
    private var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySupplierListBinding.inflate(layoutInflater)
        setupDrawer(binding.root, binding.toolbar)

        presenter = SupplierListPresenter(this)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        presenter.startListening()

        //https://developer.android.com/reference/kotlin/android/widget/SearchView.OnQueryTextListener
        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean = false
            override fun onQueryTextChange(newText: String): Boolean {
                presenter.loadSuppliers(newText)
                return true
            }
        })

        if (savedInstanceState == null) animate()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_list, menu)

        menu.findItem(R.id.item_add)?.isVisible = true
        menu.findItem(R.id.item_edit_supplier)?.isVisible = false

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> presenter.doAddSupplier()
            R.id.item_settings -> presenter.doOpenSettings()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        presenter.startListening()
    }

    override fun onSupplierClick(supplier: SupplierModel) {
        presenter.doOpenItemList(supplier)
    }

    fun showSuppliers(suppliers: List<SupplierModel>) {
        binding.recyclerView.adapter = SupplierAdapter(suppliers, this)
    }

    fun onDelete(position: Int) {
        binding.recyclerView.adapter?.notifyItemRemoved(position)
    }

    fun animate() {
        binding.root.post {
            val parent = binding.recyclerView.parent as ViewGroup
            val transition = TransitionInflater.from(this).inflateTransition(R.transition.scene_enter)

            transition.addTarget(binding.toolbar)
            transition.addTarget(binding.recyclerView)
            transition.addTarget(binding.searchView)

            TransitionManager.beginDelayedTransition(parent, transition)

            binding.toolbar.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.VISIBLE
            binding.searchView.visibility = View.VISIBLE
        }
    }
}