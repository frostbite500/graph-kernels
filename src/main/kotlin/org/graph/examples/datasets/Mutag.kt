package org.graph.examples.datasets

import com.beust.klaxon.*
import org.graph.Graph
import org.graph.LabeledVertex

object Mutag {

    var graphs: List<Graph>?
    var labels: List<Int?>?

    init {
        val obj = Parser::class.java.getResourceAsStream("/MUTAG.json")
                .let { inputStream -> Parser().parse(inputStream) as JsonObject }
        graphs = obj.array<JsonObject>("graphs")?.map { jsonObject -> makeGraph(jsonObject) }
        labels = obj.array<JsonObject>("graphs")?.map { jsonObject -> jsonObject.int("gl") }
    }

private fun makeGraph(jsonObject: JsonObject): Graph {
    val out = Graph()
    val al = jsonObject.array<List<Int>>("al")
    if (al != null) {
        for (i in al.indices) {
            val neighbors = al[i]
            neighbors.forEach { out.addUndirectedEdge(LabeledVertex(i.toString()), LabeledVertex(it.toString())) }
        }
    }
    return out
}


}