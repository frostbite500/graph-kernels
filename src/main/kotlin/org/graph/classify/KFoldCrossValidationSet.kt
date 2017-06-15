package org.graph.classify

import org.graph.Graph
import java.util.*

class KFoldCrossValidationSet(val k: Int, dataSet: MutableList<Any>, labelSet: MutableList<Any>) {

    private var validationSetId = 0

    // Make sure the seed is the same so that the data and labels are identically randomized
    private val data = sliceList(dataSet, Random(1)) as List<List<Graph>>
    private val labels = sliceList(labelSet, Random(1)) as List<List<Int>>

    private var trainingData = excludeIndex(data, validationSetId) as List<List<Graph>>
    private var validationData = data[validationSetId]
    private var trainingLabels = excludeIndex(labels, validationSetId) as List<List<Int>>
    private var validationLabels = labels[validationSetId]

    fun getTrainingData(): List<Graph> {
        return trainingData.foldRight(listOf(), {l, acc -> acc + l})
    }

    fun getValidationData(): List<Graph> {
        return validationData
    }

    fun getTrainingLabels(): List<Int> {
        return trainingLabels.foldRight(listOf(), {l, acc -> acc + l})
    }

    fun getValidationLabels(): List<Int> {
        return validationLabels
    }

    fun nextFold() {
        if (validationSetId == k) {
            throw IllegalStateException("All folds have already been visited")
        }
        trainingData = excludeIndex(data, validationSetId) as List<List<Graph>>
        validationData = data[validationSetId]
        trainingLabels = excludeIndex(labels, validationSetId) as List<List<Int>>
        validationLabels = labels[validationSetId]
        validationSetId++
    }

    private fun excludeIndex(list: List<Any>, index: Int): List<Any> {
        if (index == 0) {
            return list.subList(1, list.size)
        } else if (index == k - 1) {
            return list.subList(0, list.size - 1)
        }

        val l1 = list.subList(0, index)
        val l2 = list.subList(index+1, list.size)

        return l1 + l2
    }

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

