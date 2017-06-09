package org.graph.kernel

import org.graph.Graph
import org.graph.LabeledVertex
import java.util.*
import java.util.concurrent.atomic.AtomicInteger


class WeisfahlerLemanFeatureExtractor(val G: Graph, val h: Int, val compressedAlphabet: HashMap<String, String>, val labelCounter: AtomicInteger) {
    val featuresMap: MutableMap<String, Int> = HashMap()

    init {
        G.adjList.keys.forEach {
            if (!this.compressedAlphabet.containsKey(it.label))
                this.compressedAlphabet[it.label] = labelCounter.incrementAndGet().toString()
        }
        G.adjList.keys.forEach { updateFeatureMap(it) }
    }

    fun getFeatures(): MutableMap<String, Int> {
        var graph = G
        for (i in 2..h) {
            graph = iterate(graph)
        }
        return featuresMap
    }

    private fun updateFeatureMap(label: LabeledVertex) {
        val numLabels = featuresMap.getOrElse(label.label, { 0 })
        featuresMap.put(label.label, numLabels + 1)
    }

    private fun multisetLabelDeterminationStep(graph: Graph, vertex: LabeledVertex): LabeledVertex {
        val neighbors = graph.getNeigborsOf(vertex)
        val newLabel = neighbors
                .sortedBy { it.vertex.label }
                .foldRight("${vertex.label},") { v, acc -> acc.plus(v.vertex.label) }
                .toString()
        return LabeledVertex(newLabel)
    }

    private fun labelCompressionStep(vertex: LabeledVertex): LabeledVertex {
        val newLabel =
                if (this.compressedAlphabet.containsKey(vertex.label))
                    compressedAlphabet[vertex.label]!!
                else {
                    this.compressedAlphabet[vertex.label] = labelCounter.incrementAndGet().toString()
                    labelCounter.toString()
                }
        return LabeledVertex(newLabel)
    }

    private fun iterate(graph: Graph): Graph {

        val newMappings = graph.adjList.keys
                .map { it to multisetLabelDeterminationStep(graph, it) }
                .map { it.first to labelCompressionStep(it.second) }
                .map { updateFeatureMap(it.second); it }

        return newGraph(graph, newMappings.toMap())
    }

    private fun newGraph(graph: Graph, newMappings: Map<LabeledVertex, LabeledVertex>): Graph {
        val newGraph = Graph()
        graph.adjList.forEach {
            val updatedLabelFrom = newMappings[it.key]!!
            newGraph.addVertex(updatedLabelFrom)

            it.value.forEach {
                val updatedLabelTo = newMappings[it.vertex]!!
                newGraph.addDirectedEdge(updatedLabelFrom, updatedLabelTo, it.edgeLabel)
            }
        }
        return newGraph
    }
}