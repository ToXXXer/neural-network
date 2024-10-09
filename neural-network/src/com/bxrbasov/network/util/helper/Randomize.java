package com.bxrbasov.network.util.helper;

import lombok.experimental.UtilityClass;
import java.util.Random;

@UtilityClass
public class Randomize {
    public Double nextWeight() {
        Random rand = new Random();
        Double max = 0.5;
        Double min = -0.5;
        return (rand.nextDouble() * (max - min) + min);
    }
}
