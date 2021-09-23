package com.phone;

public class Substration implements Operation{
    @Override
    public double operation(double[] arr) {
        double sum = 0;
        for (double v : arr) {
            sum -= v;
        }
        return sum;
    }
}
