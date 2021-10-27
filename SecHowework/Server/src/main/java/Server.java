import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.server.ServerCloneException;

import static java.lang.System.exit;

public class Server {
    private ServerSocket serverSocket;

    public static boolean portStatus(int port) {
        boolean free;
        try (var ignored = new ServerSocket(port)) {
            free = true;
        } catch (IOException e) {
            free = false;
        }
        return free;
    }

    private void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            new ServerConnection(clientSocket).start();
        }
    }

    static class ServerConnection extends Thread {
        private final Socket clientSocket;


        ServerConnection(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        public void run() {
            try {
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String operation;

                while (true) {
                    operation = in.readLine();

                    if (operation.equalsIgnoreCase("exit") || operation.equalsIgnoreCase("quit")) {
                        break;
                    }

                    out.println(operation);


                }
                in.close();
                out.close();
                clientSocket.close();
            } catch (Exception e) {}

        }
    }

    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.err.println("invalid arguments");
            exit(1);
        }
        int port = Integer.parseInt(args[0]);

        if (port < 0 || port >= 65536) {
            System.err.println("invalid arguments");
            exit(1);
        }
//        System.out.println(args[0]);
        if (!portStatus(port)) {
            System.err.println("port is already in use");
            exit(2);
        }

        Server server = new Server();
        server.start(port);


    }


}
