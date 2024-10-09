package com.bxrbasov.network;

import com.bxrbasov.network.neural.TrainingNeural;
import com.bxrbasov.network.util.creater.NeuralCreator;
import com.bxrbasov.network.util.data.DataReader;
import com.bxrbasov.network.neural.Neural;
import java.util.ArrayList;
import java.util.List;

public class NeuralRunner {
    public static void main(String[] args) {

        List<Neural> inputLayer = new ArrayList<>();
        List<Neural> hiddenLayerOne = new ArrayList<>();
        List<Neural> hiddenLayerTwo = new ArrayList<>();
        List<Neural> outputLayer = new ArrayList<>();
        NeuralCreator.createNeuralNetwork(inputLayer, hiddenLayerOne, hiddenLayerTwo, outputLayer);

        List<List<Double>> queueInput = DataReader.getQueueInput();
        List<Double> queueOutput = DataReader.getQueueOutput();
        TrainingNeural.gymNeuralNetwork(inputLayer, hiddenLayerOne, hiddenLayerTwo, outputLayer, queueInput, queueOutput);

        List<List<Double>> queueCheck = DataReader.getQueueCheck();
        List<Double> result = TrainingNeural.checkResult(inputLayer, hiddenLayerOne, hiddenLayerTwo, outputLayer, queueCheck);
        System.out.println(result.toString());
        List<Double> arr = List.of(1.0, 20.0, 5.0, 0.0);
    }
}
