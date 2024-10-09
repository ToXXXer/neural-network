package com.bxrbasov.network.util.creater;

import com.bxrbasov.network.neural.Neural;
import com.bxrbasov.network.util.constant.NeuralConstant;
import com.bxrbasov.network.util.helper.Randomize;
import lombok.experimental.UtilityClass;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class NeuralCreator {

    private static int atribut = 0;

    public static void createNeuralNetwork(List<Neural> inputNeurals, List<Neural> hiddenNeuralsOne, List<Neural> hiddenNeuralsTwo, List<Neural> outputNeurals) {
        createInputNeurals(inputNeurals, NeuralConstant.NUMBER_ATRIBUTS, NeuralConstant.NUMBER_ATRIBUTS);
        createHiddenNeurals(hiddenNeuralsOne, inputNeurals.size() * 2, inputNeurals.size());
        createHiddenNeurals(hiddenNeuralsTwo, hiddenNeuralsOne.size() / 4, hiddenNeuralsOne.size());
        createOutputNeural(outputNeurals, NeuralConstant.NUMBER_OUT_NEURONS, hiddenNeuralsTwo.size());
        createLinks(inputNeurals, hiddenNeuralsOne, hiddenNeuralsTwo, outputNeurals);
    }

    private static void createLinks(List<Neural> inputNeurals, List<Neural> hiddenNeuralsOne, List<Neural> hiddenNeuralsTwo, List<Neural> outputNeurals) {

        for (int i = 0; i < inputNeurals.size(); i++) {
            Neural neural = inputNeurals.get(i);
            for (int j = 0; j < hiddenNeuralsOne.size(); j++) {
                neural.addNextLink(hiddenNeuralsOne.get(j));
                hiddenNeuralsOne.get(j).addLastLink(neural);
            }
        }

        for (int i = 0; i < hiddenNeuralsOne.size(); i++) {
            Neural neural = hiddenNeuralsOne.get(i);
            for (int j = 0; j < hiddenNeuralsTwo.size(); j++) {
                neural.addNextLink(hiddenNeuralsTwo.get(j));
                hiddenNeuralsTwo.get(j).addLastLink(neural);
            }
        }

        for (int i = 0; i < hiddenNeuralsTwo.size(); i++) {
            Neural neural = hiddenNeuralsTwo.get(i);
            for (int j = 0; j < outputNeurals.size(); j++) {
                neural.addNextLink(outputNeurals.get(j));
                outputNeurals.get(j).addLastLink(neural);
            }
        }

    }

    private static void createInputNeurals(List<Neural> list, int numberNeurons, int numberLinks) {
        for(int i = 0; i < numberNeurons; i++) {
            List<Double> weights = new ArrayList<>();
            for(int j = 0; j < numberLinks; j++) {
                if(atribut == j) {
                    weights.add(1.0);
                } else {
                    weights.add(0.0);
                }
            }
            List<Double> biases = new ArrayList<>();
            for(int j = 0; j < numberLinks; j++) {
                biases.add(0.0);
            }
            list.add(new Neural(weights, biases));
            atribut++;
        }
    }

     private static void createHiddenNeurals(List<Neural> list, int numberNeurons, int numberLinks) {
        for(int i = 0; i < numberNeurons; i++) {
            List<Double> weights = new ArrayList<>();
            for(int j = 0; j < numberLinks; j++) {
                weights.add(Randomize.nextWeight());
            }
            List<Double> biases = new ArrayList<>();
            for(int j = 0; j < numberLinks; j++) {
                biases.add(0.0);
            }
            list.add(new Neural(weights, biases));
        }
    }

    private static void createOutputNeural(List<Neural> list, int numberNeurons,  int numberLinks) {
        List<Double> weights = new ArrayList<>();
        for(int i = 0; i < numberNeurons; i++) {
            for(int j = 0; j < numberLinks; j++) {
                weights.add(Randomize.nextWeight());
            }
            List<Double> biases = new ArrayList<>();
            for(int j = 0; j < numberLinks; j++) {
                biases.add(0.0);
            }
            list.add(new Neural(weights, biases));
        }
    }
}
