package phone;

public class Multiplication implements Operation{

    @Override
    public double operation(double[] arr) {
        double sum = 1;
        for (double v : arr) {
            sum *= v;
        }
        return sum;
    }
}
