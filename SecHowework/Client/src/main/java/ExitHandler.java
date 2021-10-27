import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ExitHandler extends Thread {

     private PrintWriter out;
     private BufferedReader in;


    public ExitHandler(String ip, int port) throws IOException {
        Socket clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public void run() {
        while (true) {
            synchronized (out){
                try {
                    out.println("handle");
                    String operation = in.readLine();
                    if(operation == null){
                        System.out.println("server disconnect");
                        System.exit(0);
                    }
                }catch (IOException e){
                    System.out.println("server disconnect");
                    System.exit(0);
                }
            }

        }
    }
}
