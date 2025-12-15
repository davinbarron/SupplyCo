package org.wit.supplyco.models

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import timber.log.Timber.i
import timber.log.Timber.e

class ItemFirestoreRepo : ItemRepo {
    private val db: FirebaseFirestore = Firebase.firestore

    override fun findAllForSupplier(supplierId: String, callback: (List<ItemModel>) -> Unit) {
        db.collection("suppliers").document(supplierId).collection("items")
            .get()
            .addOnSuccessListener { result ->
                val items = result.documents.mapNotNull { doc ->
                    val item = doc.toObject(ItemModel::class.java)
                    item?.apply { id = doc.id }
                }
                callback(items)
            }
            .addOnFailureListener { e ->
                e("Error fetching items: $e")
                callback(emptyList())
            }
    }

    override fun create(supplierId: String, item: ItemModel) {
        val docRef = db.collection("suppliers").document(supplierId)
            .collection("items").document()
        item.id = docRef.id
        docRef.set(item)
            .addOnSuccessListener { i("Item created with Firestore ID: ${docRef.id}") }
            .addOnFailureListener { e ->
                e("Error creating item: $e")
            }
    }

    override fun update(supplierId: String, item: ItemModel) {
        val id = item.id
        if (id != null) {
            db.collection("suppliers").document(supplierId)
                .collection("items").document(id).set(item)
                .addOnSuccessListener { i("Item updated with Firestore ID: $id") }
                .addOnFailureListener { e ->
                    e("Error updating item: $e")
                }
        } else {
            e("Cannot update item: missing Firestore ID")
        }
    }

    override fun delete(supplierId: String, item: ItemModel) {
        val id = item.id
        if (id != null) {
            db.collection("suppliers").document(supplierId)
                .collection("items").document(id).delete()
                .addOnSuccessListener { i("Item deleted with Firestore ID: $id") }
                .addOnFailureListener { e ->
                    e("Error deleting item: $e")
                }
        } else {
            e("Cannot delete item: missing Firestore ID")
        }
    }

    override fun deleteAllForSupplier(supplierId: String) {
        db.collection("suppliers").document(supplierId).collection("items")
            .get()
            .addOnSuccessListener { result ->
                for (doc in result) {
                    doc.reference.delete()
                }
                i("All items deleted for supplier $supplierId")
            }
            .addOnFailureListener { e ->
                e("Error deleting all items: $e")
            }
    }

    override fun listenAllForSupplier(supplierId: String, callback: (List<ItemModel>) -> Unit) {
        db.collection("suppliers").document(supplierId).collection("items")
            .addSnapshotListener { snapshot, error ->

                if (error != null) {
                    e("Listen failed: $error")
                    return@addSnapshotListener
                }

                val items = snapshot?.documents?.mapNotNull { doc ->
                    val item = doc.toObject(ItemModel::class.java)
                    item?.apply { id = doc.id }
                } ?: emptyList()

                callback(items)
            }
    }
}
