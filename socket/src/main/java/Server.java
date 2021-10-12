import phone.OperatingFactory;
import phone.Operation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Server {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        clientSocket = serverSocket.accept();
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        OperatingFactory of = new OperatingFactory();

            String[] array= in.readLine().split(" ");
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

    public void close() throws IOException {
        serverSocket.close();
        clientSocket.close();
        out.close();
        in.close();
    }


    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.start(6666);
    }


}
