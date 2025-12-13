package org.wit.supplyco.models

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import timber.log.Timber.i
import timber.log.Timber.e

class ItemFirestoreRepo : ItemRepo {
    private val db: FirebaseFirestore = Firebase.firestore

    override fun findAllForSupplier(supplierId: String, callback: (List<ItemModel>) -> Unit) {
    }

    override fun create(supplierId: String, item: ItemModel) {
    }

    override fun update(supplierId: String, item: ItemModel) {
    }

    override fun delete(supplierId: String, item: ItemModel) {
    }
}
