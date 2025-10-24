package org.wit.supplyco.models

import android.content.Context
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.wit.supplyco.helper.exists
import org.wit.supplyco.helper.read
import org.wit.supplyco.helper.write

/*
    SupplierJSONRepo handles persistent storage of suppliers using JSON serialization.
    Resources:
    https://www.dhiwise.com/post/kotlin-gson-for-effective-json-handling#creating-kotlin-data-classes-for-json
 */

class SupplierJSONRepo(private val context: Context) : SupplierRepo {

    private val memRepo = SupplierMemRepo()
    private val gson = GsonBuilder().setPrettyPrinting().create()
    private val listType = object : TypeToken<ArrayList<SupplierModel>>() {}.type
    private val fileName = "suppliers.json"

    init {
        if (exists(context, fileName)) {
            deserialize()
        }
    }

    override fun findAll(): List<SupplierModel> = memRepo.findAll()

    override fun findSupplier(query: String): List<SupplierModel> = memRepo.findSupplier(query)

    override fun create(supplier: SupplierModel) {
        memRepo.create(supplier)
        serialize()
    }

    override fun update(supplier: SupplierModel) {
        memRepo.update(supplier)
        serialize()
    }

    override fun delete(supplier: SupplierModel) {
        memRepo.delete(supplier)
        serialize()
    }

    override fun deleteAll() {
        memRepo.deleteAll()
        serialize()
    }

    private fun serialize() {
        val json = gson.toJson(memRepo.findAll(), listType)
        write(context, fileName, json)
    }

    private fun deserialize() {
        val json = read(context, fileName)
        val loaded = gson.fromJson<List<SupplierModel>>(json, listType)
        loaded.forEach { memRepo.create(it) }
    }
}