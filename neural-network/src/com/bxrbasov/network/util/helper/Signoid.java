package com.bxrbasov.network.util.helper;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Signoid {
    public static Double getSignoid(Double x) {
        return 1 / (1 + Math.exp(-x));
    }
    public static Double getGradient(Double x) {return getSignoid(x) * (1 - getSignoid(x));}
}
