package com.codecool.greencommitment.client;

import java.util.Random;

public class DataGenerator {
    Random rand = new Random();

    public String measureThermo() {
        DOMCreater XMLW = new DOMCreater();
        Float minValue = -20.0f;
        Float maxValue = 38.0f;
        Float currentTemp = rand.nextFloat() * (maxValue - minValue) + minValue;
        return Float.toString(currentTemp);



    }

}
