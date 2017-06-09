package org.graph.kernel

import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Test
import org.graph.Graph
import org.graph.kernel.WeisfahlerLemanFeatureExtractor
import org.graph.testGraphFrist
import org.graph.testGraphSecond
import java.util.*
import java.util.concurrent.atomic.AtomicInteger

class WeisfahlerLemanExtractorTest {

    @Test
    fun `one level nesting`() {
        val g = testGraphFrist()
        val features = getFeatures(g, 1)

        assertArrayEquals(arrayOf(1,1,1,1,2), features.values.sorted().toTypedArray())
    }

    @Test
    fun `two level nesting`() {
        val g = testGraphFrist()
        val features = getFeatures(g, 2)

        assertArrayEquals(arrayOf(1,1,1,1,1,1,1,1,2,2), features.values.sorted().toTypedArray())
    }

    @Test
    fun `another graph at two level nesting`() {
        val g = testGraphSecond()
        val features = getFeatures(g, 2)

        assertArrayEquals(arrayOf(1,1,1,1,1,1,1,1,1,1,2), features.values.sorted().toTypedArray())
    }

    @Test
    fun `output is the same each time`() {
        val g = testGraphSecond()
        val f1 = getFeatures(g, 2)
        val f2 = getFeatures(g, 2)

        assertEquals(f1, f2)
    }


    private fun getFeatures(g: Graph, h: Int): Map<String, Int> {
        val alphabet = HashMap<String, String>()
        val labelCounter = AtomicInteger()

        val gExtractor = WeisfahlerLemanFeatureExtractor(g, h, alphabet, labelCounter)

        return gExtractor.getFeatures()
    }
}