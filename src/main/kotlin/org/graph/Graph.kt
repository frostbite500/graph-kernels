package org.graph

import java.util.*

data class Graph(val adjList: MutableMap<LabeledVertex, MutableList<VertexAndEdgeLabel>> = HashMap()) {

    fun addVertex(vertex: LabeledVertex){
        if (adjList.containsKey(vertex))
            throw IllegalArgumentException("Vertex ${vertex} already exists in graph")
        adjList.put(vertex, mutableListOf())
    }

    fun addDirectedEdge(from: LabeledVertex, to: LabeledVertex, label: String = "") {
        if (adjList[from]?.contains(VertexAndEdgeLabel(to, label))?: false)
            throw IllegalArgumentException("An edge with label $label already exists from $from to $to")
        adjList[from]?.add(VertexAndEdgeLabel(to, label))
    }

    fun addUndirectedEdge(from: LabeledVertex, to: LabeledVertex, label: String = "") {
        if (adjList[from]?.contains(VertexAndEdgeLabel(to, label))?: false)
            throw IllegalArgumentException("An edge with label $label already exists from $from to $to")
        adjList[from]?.add(VertexAndEdgeLabel(to, label))

        if (adjList[to]?.contains(VertexAndEdgeLabel(from, label))?: false)
            throw IllegalArgumentException("An edge with label $label already exists from $to to $from")
        adjList[to]?.add(VertexAndEdgeLabel(from, label))
    }



    fun getNeigborsOf(vertex: LabeledVertex): List<VertexAndEdgeLabel> {
        if (!adjList.containsKey(vertex))
            throw IllegalArgumentException("Vertex $vertex does not exist in graph")
        else
            return adjList[vertex]!!.toList()
    }
}

data class LabeledVertex(var label: String) : Comparable<LabeledVertex> {

    override fun compareTo(other: LabeledVertex): Int {
        return label.compareTo(other.label)
    }

    override fun hashCode(): Int {
        return label.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return this === other

    }
}

data class VertexAndEdgeLabel(val vertex: LabeledVertex, val edgeLabel: String)
