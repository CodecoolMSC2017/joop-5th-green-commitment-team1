package com.codecool.greencommitment.client;

import java.util.Random;

public class DataGenerator {
    Random rand = new Random();

    public void measureThermo() {
        Float minValue = -20.0f;
        Float maxValue = 38.0f;
        Float currentTemp = rand.nextFloat() * (maxValue - minValue) + minValue;


    }

}
