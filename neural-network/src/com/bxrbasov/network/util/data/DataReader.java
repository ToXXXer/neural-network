package com.bxrbasov.network.util.data;

import com.bxrbasov.network.util.constant.NeuralConstant;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.*;

@UtilityClass
public class DataReader {

    private static List<List<Double>> queueInput = new ArrayList<>();
    private static List<Double> queueOutput = new ArrayList<>();
    private static List<List<Double>> queueCheck = new ArrayList<>();

    private static final Path PATH_GYM = Path.of("C:\\Users\\admin\\OneDrive\\Рабочий стол\\Нейросеть\\data\\Обучение.txt");
    private static final Path PATH_CHECK = Path.of("C:\\Users\\admin\\OneDrive\\Рабочий стол\\Нейросеть\\data\\Проверка.txt");

    static {
        getQueue();
    }

    public static List<List<Double>> getQueueInput() {
        return queueInput;
    }

    public static List<Double> getQueueOutput() {
        return queueOutput;
    }

    public static List<List<Double>> getQueueCheck() {
        return queueCheck;
    }

    @SneakyThrows
    public static void getQueue() {
        try(FileReader fileReader = new FileReader(PATH_GYM.toFile());
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            FileReader fileReaderCheck = new FileReader(PATH_CHECK.toFile());
            BufferedReader bufferedReaderCheck = new BufferedReader(fileReaderCheck);) {

            while (bufferedReader.ready()) {
                String line = bufferedReader.readLine().replace(',','.');
                String[] split = line.split("\t");
                queueInput.add(getInput(split, split.length));
                queueOutput.add(getOutput(split));
            }

            while (bufferedReaderCheck.ready()) {
                String line = bufferedReaderCheck.readLine().replace(',','.');
                String[] split = line.split("\t");
                queueCheck.add(getInput(split, split.length + 1));
            }
        }
        queueInput = getMaxMin(queueInput);
        queueCheck = getMaxMin(queueCheck);
    }

    private static List<Double> getInput(String[] s, int length) {
        List<Double> array = new ArrayList<>();
        for (int i = 0; i < length - 1; i++) {
            if(i == 0){
                if(s[0].equals("темные")) {
                    array.add(1.0);
                } else if(s[0].equals("светлые")){
                    array.add(0.0);
                } else {
                    array.add(-1.0);
                }
            } else {
                array.add(Double.parseDouble(s[i]));
            }
        }
        return array;
    }

    private static Double getOutput(String[] s) {
        int length = s.length;
        if (s[length-1].equals("да")){
            return 1.0;
        } else {
            return 0.0;
        }
    }

    private static List<List<Double>> getMaxMin(List<List<Double>> q) {
        double[] max = new double[NeuralConstant.NUMBER_ATRIBUTS];
        double[] min = new double[NeuralConstant.NUMBER_ATRIBUTS];
        for (int i = 0; i < NeuralConstant.NUMBER_ATRIBUTS; i++) {
            max[i] = Double.MIN_VALUE;
            min[i] = Double.MAX_VALUE;
        }

        for(List<Double> list : q) {
            for (int i = 0; i < list.size(); i++) {
                if(max[i] < list.get(i)) {
                    max[i] = list.get(i);
                }
                if(min[i] > list.get(i)) {
                    min[i] = list.get(i);
                }
            }
        }
        return getRationing(max, min, q);
    }

    private static List<List<Double>> getRationing(double[] max, double[] min, List<List<Double>> q) {
        List<List<Double>> result = new LinkedList<>();
        for(int k = 0; k < q.size(); k++) {
            List<Double> list = q.get(k);
            for(int i = 0; i < list.size(); i++) {
                list.set(i, (list.get(i) - min[i])/(max[i] - min[i]));
            }
            result.add(list);
        }
        return result;
    }
}
