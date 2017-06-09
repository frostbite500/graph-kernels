package org.graph


fun testGraphFrist(): Graph {
    val g = Graph()

    val l1 = LabeledVertex("1")
    val l11 = LabeledVertex("1")


    val l2 = LabeledVertex("2")
    val l3 = LabeledVertex("3")
    val l4 = LabeledVertex("4")
    val l5 = LabeledVertex("5")

    g.addVertex(l1)
    g.addVertex(l11)
    g.addVertex(l2)
    g.addVertex(l3)
    g.addVertex(l4)
    g.addVertex(l5)

    g.addUndirectedEdge(l1, l4, "14")
    g.addUndirectedEdge(l11, l4, "114")
    g.addUndirectedEdge(l4, l5, "45")
    g.addUndirectedEdge(l4, l3, "43")
    g.addUndirectedEdge(l5, l3, "53")
    g.addUndirectedEdge(l5, l2, "52")
    g.addUndirectedEdge(l2, l3, "23")

    return g
}


fun testGraphSecond(): Graph {
    val g = Graph()

    val l1 = LabeledVertex("1")
    val l2 = LabeledVertex("2")
    val l22 = LabeledVertex("2")
    val l3 = LabeledVertex("3")
    val l4 = LabeledVertex("4")
    val l5 = LabeledVertex("5")

    g.addVertex(l1)
    g.addVertex(l2)
    g.addVertex(l22)
    g.addVertex(l3)
    g.addVertex(l4)
    g.addVertex(l5)

    g.addUndirectedEdge(l1, l4, "14")
    g.addUndirectedEdge(l4, l2, "42")
    g.addUndirectedEdge(l4, l3, "43")
    g.addUndirectedEdge(l4, l5, "45")
    g.addUndirectedEdge(l2, l5, "25")
    g.addUndirectedEdge(l5, l3, "53")
    g.addUndirectedEdge(l3, l2, "32")

    return g
}