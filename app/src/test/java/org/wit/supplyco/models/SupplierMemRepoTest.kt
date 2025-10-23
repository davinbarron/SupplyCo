package org.wit.supplyco.models

import org.junit.Assert
import org.junit.Before
import org.junit.Test

/*
    Using JUnit to test the functionality of SupplierMemRep
    Resources:
    https://bugfender.com/blog/kotlin-unit-testing/
 */

class SupplierMemRepoTest {

    private lateinit var repo: SupplierMemRepo

    @Before
    fun setup() {
        repo = SupplierMemRepo()
    }

    @Test
    fun create() {
        val supplier = SupplierModel(name = "New Supplier")
        repo.create(supplier)
        Assert.assertTrue(repo.findAll().contains(supplier))
        Assert.assertEquals(0, supplier.id)
    }

    @Test
    fun update() {
        val supplier = SupplierModel(name = "Original Name", email = "original@example.com")
        repo.create(supplier)

        supplier.name = "Updated Name"
        supplier.email = "updated@example.com"
        repo.update(supplier)

        val updatedSupplier = repo.findAll().first()
        Assert.assertEquals("Updated Name", updatedSupplier.name)
        Assert.assertEquals("updated@example.com", updatedSupplier.email)
    }

    @Test
    fun delete() {
        val supplier = SupplierModel(name = "To Be Deleted")
        repo.create(supplier)
        repo.delete(supplier)
        Assert.assertFalse(repo.findAll().contains(supplier))
    }

    @Test
    fun deleteAll() {
        val supplier1 = SupplierModel(name = "Supplier One")
        val supplier2 = SupplierModel(name = "Supplier Two")
        repo.create(supplier1)
        repo.create(supplier2)

        repo.deleteAll()
        Assert.assertEquals(0, repo.findAll().size)
    }

    @Test
    fun findAll() {
        val supplier1 = SupplierModel(name = "Alpha")
        val supplier2 = SupplierModel(name = "Beta")
        repo.create(supplier1)
        repo.create(supplier2)

        val allSuppliers = repo.findAll()
        Assert.assertEquals(2, allSuppliers.size)
        Assert.assertTrue(allSuppliers.contains(supplier1))
        Assert.assertTrue(allSuppliers.contains(supplier2))
    }

    // Updates a supplier which does not exist
    @Test
    fun updateFakeSupplier() {
        val ghost = SupplierModel(id = 999, name = "Ghost Supplier")
        repo.update(ghost)
        Assert.assertEquals(0, repo.findAll().size)
    }
}