package org.wit.supplyco.models

import org.junit.Assert.*
import org.junit.Test

class SupplierModelTest {

    @Test
    fun copy() {
        val original = SupplierModel(id = 1, name = "Original")
        val copy = original.copy(name = "Copy")
        assertEquals("Original", original.name)
        assertEquals("Copy", copy.name)
        assertNotEquals(original, copy)
    }

    @Test
    fun equals() {
        val s1 = SupplierModel(id = 1, name = "Test")
        val s2 = SupplierModel(id = 1, name = "Test")
        assertEquals(s1, s2)
    }

    @Test
    fun toStringTest() {
        val supplier = SupplierModel(id = 3, name = "Test")
        val output = supplier.toString()
        assertTrue(output.contains("Test"))
        assertTrue(output.contains("id=3"))
    }
}