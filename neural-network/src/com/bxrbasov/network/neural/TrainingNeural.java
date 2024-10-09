package com.bxrbasov.network.neural;

import com.bxrbasov.network.util.constant.NeuralConstant;
import com.bxrbasov.network.util.helper.Signoid;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class TrainingNeural {

    private static int numberEpoh = 1000;
    private static final double STEP = 0.01;

    public static void gymNeuralNetwork(List<Neural> inputLayer, List<Neural> hiddenLayerOne, List<Neural> hiddenLayerTwo,
                                        List<Neural> outputLayer, List<List<Double>> queueInputs, List<Double> queueOutputs) {
        while (numberEpoh > 0) {
            for(int i = 0; i < queueInputs.size(); i++) {
                List<Double> queueInput = queueInputs.get(i);
                double d = queueOutputs.get(i);
                getGym(inputLayer, hiddenLayerOne, hiddenLayerTwo, outputLayer, queueInput, d);
            }
            numberEpoh--;
        }
    }

    public static List<Double> checkResult(List<Neural> inputLayer, List<Neural> hiddenLayerOne, List<Neural> hiddenLayerTwo,
                                   List<Neural> outputLayer, List<List<Double>> queueInputs) {
        List<Double> result = new ArrayList<>();
        for(int i = 0; i < queueInputs.size(); i++) {
            List<Double> queueInput = queueInputs.get(i);
            result.add(getOut(inputLayer, hiddenLayerOne, hiddenLayerTwo, outputLayer, queueInput));
        }
        return result;
    }

    private static void getGym(List<Neural> inputLayer, List<Neural> hiddenLayerOne, List<Neural> hiddenLayerTwo,
                                 List<Neural> outputLayer, List<Double> queueInput, double d) {
        double delta = getDelta(d, getOut(inputLayer, hiddenLayerOne, hiddenLayerTwo, outputLayer, queueInput));
        for(int j = 0; j < outputLayer.size(); j++) {
            Neural neural = outputLayer.get(j);
            List<Double> weights = neural.getWeights();
            for(int k = 0; k < weights.size(); k++) {
                weights.set(k, weights.get(k) - delta*STEP*neural.getLinksLast().get(k).getOutput());
            }
        }

        for (int j = 0; j < hiddenLayerTwo.size(); j++) {
            Neural neural = hiddenLayerTwo.get(j);
            double newDelta = 0.0;
            for(int k = 0; k < outputLayer.size(); k++) {
                 newDelta += delta*outputLayer.get(k).getWeights().get(j)*Signoid.getGradient(neural.getOutput());
            }
            List<Double> weights = neural.getWeights();
            for(int l = 0; l < weights.size(); l++) {
                weights.set(l, weights.get(l) - newDelta*STEP*neural.getLinksLast().get(l).getOutput());
            }
        }

        for (int j = 0; j < hiddenLayerOne.size(); j++) {
            Neural neural = hiddenLayerOne.get(j);
            double doubleNewDelta = 0.0;
            for(int k = 0; k < hiddenLayerTwo.size(); k++) {
                Neural neural1 = hiddenLayerTwo.get(k);
                double newDelta = 0.0;
                for(int l = 0; l < outputLayer.size(); l++) {
                    newDelta += delta*outputLayer.get(l).getWeights().get(k)*Signoid.getGradient(neural1.getOutput());
                }
                doubleNewDelta += newDelta * neural1.getWeights().get(j);
            }
            doubleNewDelta *= Signoid.getGradient(neural.getOutput());
            List<Double> weights = neural.getWeights();
            for(int l = 0; l < weights.size(); l++) {
                weights.set(l, weights.get(l) - doubleNewDelta*STEP*neural.getLinksLast().get(l).getOutput());
            }
        }
    }

    private static double getDelta(double d, double out) {return (out - d) * Signoid.getGradient(out);}

    private static double getOut(List<Neural> inputLayer, List<Neural> hiddenLayerOne, List<Neural> hiddenLayerTwo,
                                 List<Neural> outputLayer, List<Double> queueInput) {
        List<Double> first = new ArrayList<>();
        for(int j = 0; j < inputLayer.size(); j++) {
            Neural neural = inputLayer.get(j);
            neural.setOutput(queueInput);
            first.add(neural.getOutput());
        }
        System.out.println(first.toString());

        List<Double> second = new ArrayList<>();
        for (int j = 0; j < hiddenLayerOne.size(); j++) {
            Neural neural = hiddenLayerOne.get(j);
            neural.setOutput(first);
            second.add(neural.getOutput());
        }
        System.out.println(second.toString());

        List<Double> third = new ArrayList<>();
        for (int j = 0; j < hiddenLayerTwo.size(); j++) {
            Neural neural = hiddenLayerTwo.get(j);
            neural.setOutput(second);
            third.add(neural.getOutput());
        }
        System.out.println(third.toString());

        List<Double> forth = new ArrayList<>();
        for (int j = 0; j < outputLayer.size(); j++) {
            Neural neural = outputLayer.get(j);
            neural.setOutput(third);
            forth.add(neural.getOutput());
        }
        System.out.println(forth.toString());
        return forth.get(NeuralConstant.NUMBER_OUT_NEURONS - 1);
    }

}
