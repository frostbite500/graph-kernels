package org.graph.kernels

import org.junit.Assert.*
import org.junit.Test
import org.graph.testGraphFrist
import org.graph.testGraphSecond

class WeisfahlerLemanKernelTest {

    @Test
    fun `computes kernel correctly at first level`() {
        val graphs = listOf(testGraphFrist(), testGraphSecond())

        val kernel = WeisfahlerLemanKernel(graphs, 1)
        kernel.compute()
        val kernelMatrix = kernel.kernel

        assertEquals(8, kernelMatrix.getEntry(0,0).toInt())
        assertEquals(7, kernelMatrix.getEntry(0,1).toInt())
        assertEquals(7, kernelMatrix.getEntry(1,0).toInt())
        assertEquals(8, kernelMatrix.getEntry(1,1).toInt())

    }

    @Test
    fun `computes kernel correctly at second level`() {
        val graphs = listOf(testGraphFrist(), testGraphSecond())

        val kernel = WeisfahlerLemanKernel(graphs, 2)
        kernel.compute()
        val kernelMatrix = kernel.kernel

        assertEquals(16, kernelMatrix.getEntry(0,0).toInt())
        assertEquals(11, kernelMatrix.getEntry(0,1).toInt())
        assertEquals(11, kernelMatrix.getEntry(1,0).toInt())
        assertEquals(14, kernelMatrix.getEntry(1,1).toInt())
    }


}