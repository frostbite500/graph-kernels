package org.graph.kernel

import org.apache.commons.math3.linear.Array2DRowRealMatrix
import org.graph.Graph
import java.util.*
import java.util.concurrent.atomic.AtomicInteger

class WeisfahlerLemanKernel(val graphs: List<Graph>, val h: Int) {
    val compressedAlphabet = HashMap<String, String>()
    val kernel = Array2DRowRealMatrix(graphs.size, graphs.size)
    val labelCounter = AtomicInteger(0)

    fun compute() {
        for (i in 0..graphs.size-1) {
            for (j in 0..graphs.size-1) {
                val gi = graphs[i]
                val gj = graphs[j]

                val giFeatures = WeisfahlerLemanFeatureExtractor(gi, h, compressedAlphabet, labelCounter).getFeatures()
                val gjFeatures = WeisfahlerLemanFeatureExtractor(gj, h, compressedAlphabet, labelCounter).getFeatures()

                var acc = 0
                giFeatures.forEach { kv -> if (gjFeatures.containsKey(kv.key)) acc+= (kv.value * gjFeatures[kv.key]!!) }

                kernel.setEntry(i, j, acc.toDouble())
            }
        }
    }
}

