package com.bxrbasov.network.neural;

import com.bxrbasov.network.util.helper.Signoid;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@EqualsAndHashCode
public class Neural {

    private List<Neural> linksNext = new ArrayList<>();
    private List<Neural> linksLast = new ArrayList<>();
    private List<Double> weights;
    private List<Double> biases;
    private Double output = 0.0;

    public Neural(List<Double> weights, List<Double> biases) {
        this.weights = weights;
        this.biases = biases;
    }

    public void setWeights(List<Double> weights) {
        this.weights = weights;
    }

    public void setBiases(List<Double> biases) {
        this.biases = biases;
    }

    public void setOutput(List<Double> inputs) {
        Double result = 0.0;
        for (int i = 0; i < inputs.size(); i++) {
            result += inputs.get(i) * weights.get(i) + biases.get(i);
        }
        this.output = result;
    }

    public void addNextLink(Neural neural) {
        linksNext.add(neural);
    }

    public void addLastLink(Neural neural) {
        linksLast.add(neural);
    }

    public String toString() {
        return "Neural(weights=" + this.getWeights().toString() + ", biases=" + this.getBiases().toString() + ", output=" + this.getOutput() + ")";
    }
}
