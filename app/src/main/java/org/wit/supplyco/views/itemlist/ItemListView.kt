package org.wit.supplyco.views.itemlist

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
import org.wit.supplyco.adapters.ItemAdapter
import org.wit.supplyco.adapters.ItemListener
import org.wit.supplyco.databinding.ActivityItemListBinding
import org.wit.supplyco.models.ItemModel
import org.wit.supplyco.models.SupplierModel
import org.wit.supplyco.views.base.BaseDrawerActivity

class ItemListView : BaseDrawerActivity(), ItemListener {

    private lateinit var binding: ActivityItemListBinding
    lateinit var presenter: ItemListPresenter
    private lateinit var supplier: SupplierModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemListBinding.inflate(layoutInflater)
        setupDrawer(binding.root, binding.toolbarItemList)

        supplier = intent.getParcelableExtra("supplier")!!

        presenter = ItemListPresenter(this, supplier)

        binding.recyclerViewItems.layoutManager = LinearLayoutManager(this)
        presenter.startListening()

        //https://developer.android.com/reference/kotlin/android/widget/SearchView.OnQueryTextListener
        binding.searchViewItems.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false
            override fun onQueryTextChange(newText: String?): Boolean {
                presenter.loadItems(newText ?: "")
                return true
            }
        })

        if (savedInstanceState == null) {
            animate()
        } else {
            showUiElements()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_list, menu)

        menu.findItem(R.id.item_add)?.isVisible = true
        menu.findItem(R.id.item_edit_supplier)?.isVisible = true

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> presenter.doAddItem()
            R.id.item_edit_supplier -> presenter.doEditSupplier()
            R.id.item_settings -> presenter.doOpenSettings()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemClick(item: ItemModel) {
        presenter.doEditItem(item)
    }

    fun showItems(items: List<ItemModel>) {
        binding.recyclerViewItems.adapter = ItemAdapter(items, this)
    }

    private fun showUiElements() {
        binding.toolbarItemList.visibility = View.VISIBLE
        binding.recyclerViewItems.visibility = View.VISIBLE
        binding.searchViewItems.visibility = View.VISIBLE
    }

    fun animate() {
        binding.root.post {
            val parent = binding.recyclerViewItems.parent as ViewGroup
            val transition = TransitionInflater.from(this).inflateTransition(R.transition.scene_enter)

            transition.addTarget(binding.toolbarItemList)
            transition.addTarget(binding.recyclerViewItems)
            transition.addTarget(binding.searchViewItems)

            TransitionManager.beginDelayedTransition(parent, transition)

            showUiElements()
        }
    }
}
