package org.wit.supplyco.models

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import timber.log.Timber.e
import timber.log.Timber.i

/*
 * Firebase has some really good documentation on adding, updating, deleting and retrieving data
 * Listed here for reference and convenience:
 * Add: https://firebase.google.com/docs/firestore/manage-data/add-data#add_a_document
 * Update: https://firebase.google.com/docs/firestore/manage-data/add-data#update-data
 * Delete: https://firebase.google.com/docs/firestore/manage-data/delete-data#delete_documents
 * Read: https://firebase.google.com/docs/firestore/query-data/get-data#custom_objects
 */

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
        val docRef = collection.document()   // generate Firestore doc ID
        supplier.id = docRef.id           // store Firestore ID in model

        docRef.set(supplier)
            .addOnSuccessListener {
                i("Supplier created with Firestore ID: ${docRef.id}")
            }
            .addOnFailureListener { e ->
                e("Error creating supplier: $e")
            }
    }

    override fun update(supplier: SupplierModel) {
    }

    override fun delete(supplier: SupplierModel) {
    }

    override fun deleteAll() {
    }
}