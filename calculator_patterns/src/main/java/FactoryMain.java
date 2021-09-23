import com.phone.OperatingFactory;
import com.phone.Operation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FactoryMain {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello World");
        OperatingFactory of = new OperatingFactory();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] array= br.readLine().split(" ");
        String oper = array[0];

        List<String> list = new ArrayList<String>(Arrays.asList(array));
        list.remove(0);

        array = list.toArray(new String[0]);

        double[] final_arr = new double[array.length];

        for(int i=0;i<final_arr.length;++i)
        {
            final_arr[i] = Double.parseDouble(array[i]);
        }
        Operation obj = of.getOperation(oper);
        System.out.println(obj.operation(final_arr));
    }
}
