package com.phone;

public class Addition implements Operation{

    @Override
    public double operation(double[] arr) {
        double sum = 0;
        for (double v : arr) {
            sum += v;
        }
        return sum;
    }
}
