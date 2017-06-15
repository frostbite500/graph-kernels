package org.graph.classify

import org.graph.Graph
import java.util.*

class KFoldCrossValidationSet(val k: Int, dataSet: MutableList<Any>, labelSet: MutableList<Int>) {

    private var shuffle = 0

    private val data = sliceList(dataSet, Random(1)) as List<List<Graph>>
    private val labels = sliceList(dataSet, Random(1)) as List<List<Int>>

    private fun sliceList(list: MutableList<Any>, seed: Random): List<List<Any>> {
        val out: MutableList<MutableList<Any>> = mutableListOf()

        // Shuffle the indices to make sure the folds are randomized
        val shuffledIndices = IntRange(0, list.size-1).toList()
        Collections.shuffle(shuffledIndices, seed)

        var rest = list.size % k

        for (i in IntRange(0, k-1)) {
            val indices = shuffledIndices.subList(i*k, (i+1)*k)
            out.addAll(i, listOf(indices.map { list[it] } as MutableList<Any>))
        }

        // Go through the rest of the graphs and add them to the folds
        var iter = 0
        while (rest != 0) {
            out[iter % k].add(list[list.size-rest])
            rest--
            iter++
        }
        return out
    }
}

