package org.graph.classify

import libsvm.*
import org.graph.Graph
import org.graph.kernels.WeisfahlerLemanKernel

class SVMClassifier(val dataSet: KFoldCrossValidationSet) {

    var model: svm_model? = null
    val h = 4

    fun train() {

        val xTrain = computeKernel(dataSet.getTrainingData())
        val yTrain = dataSet.getTrainingLabels().map { it.toDouble() }.toDoubleArray()

        val svmProblem = svm_problem()

        val svmNodes = toSVMNodes(xTrain)

        svmProblem.x = svmNodes
        svmProblem.y = yTrain
        svmProblem.l = yTrain.size

        val parameters = svm_parameter()
        parameters.kernel_type = svm_parameter.PRECOMPUTED
        parameters.svm_type = svm_parameter.C_SVC

        model = svm.svm_train(svmProblem, parameters)
    }

    fun test() {
        if (model == null) {
            throw IllegalStateException("Model is not trained yet")
        }

        val xTest = computeKernel(dataSet.getValidationData())
        val yTest = dataSet.getTrainingLabels().map { it.toDouble() }.toDoubleArray()

        val svmNodes = toSVMNodes(xTest)

        

    }

    private fun toSVMNodes(x: Array<out DoubleArray>?): Array<Array<svm_node>> {
        val svmNodes = arrayOf(arrayOf<svm_node>())
        for (r in x?.indices!!) {
            val row = x[r]
            for (c in row.indices) {
                val node = svm_node()
                node.value = x[r][c]
                // node.index should not be needed if kernel is precomputed
                svmNodes[r][c] = node
            }
        }
        return svmNodes
    }

    fun computeKernel(graphs: List<Graph>): Array<out DoubleArray>? {
        val kernel = WeisfahlerLemanKernel(graphs, h)
        kernel.compute()
        val kernelAsArray = kernel.kernel.data

        // First column should be the indexes
        // see https://stackoverflow.com/questions/9891345/using-precomputed-kernel-in-libsvm-causes-it-to-get-stuck
        for (i in kernelAsArray.indices) {
            kernelAsArray[i] = doubleArrayOf(i+1.0) + kernelAsArray[i]
        }
        return kernelAsArray
    }

}