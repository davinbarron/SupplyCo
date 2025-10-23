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
}