package phone;

public class Division implements Operation{
    @Override
    public double operation(double[] arr) {
        double sum = arr[0];
        for (int i = 1;i< arr.length;++i) {
            sum /= arr[i];
        }
        return sum;
    }
}
