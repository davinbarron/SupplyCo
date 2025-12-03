package org.wit.supplyco.models

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import timber.log.Timber.i

class SupplierFirestoreRepo : SupplierRepo {
    private val db: FirebaseFirestore = Firebase.firestore
    private val collection = db.collection("suppliers")

    override fun findAll(): List<SupplierModel> {
        return emptyList() // Testing for now
    }

    override fun findSupplier(query: String): List<SupplierModel> {
        return emptyList() // Testing for now
    }

    override fun create(supplier: SupplierModel) {
        collection.add(supplier)
            .addOnSuccessListener { docRef ->
                i("Supplier added with ID: ${docRef.id}")
            }
            .addOnFailureListener { e ->
                i("Error adding supplier: $e")
            }
    }

    override fun update(supplier: SupplierModel) {
    }

    override fun delete(supplier: SupplierModel) {
    }

    override fun deleteAll() {
    }
}