package org.wit.supplyco.models

import org.junit.Assert.*
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
        assertTrue(repo.findAll().contains(supplier))
        assertEquals(0, supplier.id)
    }

    @Test
    fun update() {
        val supplier = SupplierModel(name = "Original Name", email = "original@example.com")
        repo.create(supplier)

        supplier.name = "Updated Name"
        supplier.email = "updated@example.com"
        repo.update(supplier)

        val updatedSupplier = repo.findAll().first()
        assertEquals("Updated Name", updatedSupplier.name)
        assertEquals("updated@example.com", updatedSupplier.email)
    }

    @Test
    fun delete() {
        val supplier = SupplierModel(name = "To Be Deleted")
        repo.create(supplier)
        repo.delete(supplier)
        assertFalse(repo.findAll().contains(supplier))
    }

    @Test
    fun deleteAll() {
        val supplier1 = SupplierModel(name = "Supplier One")
        val supplier2 = SupplierModel(name = "Supplier Two")
        repo.create(supplier1)
        repo.create(supplier2)

        repo.deleteAll()
        assertEquals(0, repo.findAll().size)
    }

    @Test
    fun findAll() {
        val supplier1 = SupplierModel(name = "Alpha")
        val supplier2 = SupplierModel(name = "Beta")
        repo.create(supplier1)
        repo.create(supplier2)

        val allSuppliers = repo.findAll()
        assertEquals(2, allSuppliers.size)
        assertTrue(allSuppliers.contains(supplier1))
        assertTrue(allSuppliers.contains(supplier2))
    }

    @Test
    fun findSupplier() {
        val supplier1 = SupplierModel(name = "Steel")
        repo.create(supplier1)

        val resultsByName = repo.findSupplier("Steel")
        assertEquals(1, resultsByName.size)
        assertTrue(resultsByName.contains(supplier1))
    }

    // Updates a supplier which does not exist
    @Test
    fun updateFakeSupplier() {
        val ghost = SupplierModel(id = 999, name = "Ghost Supplier")
        repo.update(ghost)
        assertEquals(0, repo.findAll().size)
    }
}