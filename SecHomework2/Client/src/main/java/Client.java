import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Scanner;

import static java.lang.System.console;
import static java.lang.System.exit;

public class Client {

    public static int clientStatus(String host, int port) {
        try {
            Socket socket = new Socket(host, port);
            InetAddress domainInetAddress = null;
            domainInetAddress = InetAddress.getByName(host);
            try {
                socket.close();
            } catch (IOException i) {
            }

        } catch (UnknownHostException u) {
            return 1;
        } catch (ConnectException e) {
            return -1;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;


    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public String sendMessage(String msg) throws IOException {
        out.println(msg);
        String resp = in.readLine();
        return resp;
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }

    public PrintWriter getOut() {
        return out;
    }

    public BufferedReader getIn() {
        return in;
    }

    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.err.println("invalid arguments");
            exit(1);
        }
        Scanner reader = new Scanner(System.in);
        String[] input = args[0].split(":");
        String host = input[0];
        int port = Integer.parseInt(input[1]);

        if (port < 0 || port >= 65536) {
            System.err.println("invalid arguments");
            exit(1);
        }

        int response = clientStatus(host, port);

        if (response == 1) {
            System.err.println("invalid host");
            exit(3);
        }

        if (response == -1) {
            System.err.println("connection not possible");
            exit(4);
        }

        Client client = new Client();
        client.startConnection(host, port);
        ExitHandler handler = new ExitHandler(host,port);
        handler.start();

        while (true) {
            if (reader.hasNextLine()) {
                String in = reader.nextLine();
                String operation = null;

                if(in.toLowerCase().contains("time")){
                    operation = client.sendMessage(in+" "+ ZoneId.systemDefault());
                }
                else{
                    operation = client.sendMessage(in);
                }


                if (operation == null) {
                    System.out.println("server disconnect");
                    System.exit(0);
                }
                System.out.println(operation);
            }

        }

//        client.stopConnection();
    }
}
