import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import static java.lang.System.*;

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
                    int status = checkOperation(operation);

                    if (operation.equalsIgnoreCase("exit") || operation.equalsIgnoreCase("quit")) {
                        break;
                    }

                    if (status == 0) {
                            String[] input = operation.split(" ");
                            String zone_offset = input[1];

                            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
                            ZoneOffset zoneOffset = ZoneOffset.of(zone_offset);
                            OffsetTime time = OffsetTime.now(zoneOffset);
                            LocalTime l_time = time.toLocalTime();
                            out.println(dtf.format(l_time));
                    }

                    if (status == 1) {
                        out.println("invalid input");
                    }
                    if (status == 2) {
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
                        LocalDateTime now = LocalDateTime.now();
                        out.println(dtf.format(now));
                    }

                    if (status == 3)
                        out.println("invalid time zone");

                }

                in.close();
                out.close();
                clientSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private static int checkOperation(String operation) {
        try {
            String oper = operation.toLowerCase(Locale.ROOT);
            if (oper.equals("time"))
                return 2;

            String[] input = oper.split(" ");

            if (!input[0].equals("time") || input.length > 2) {
                return 1;
            }
            if (input[0].equals("time") && (input[1].charAt(0) == '+' || input[1].charAt(0) == '-') || input[1].charAt(0) == '0') {
                try{
                    String zone_offset = input[1];
                    if(zone_offset.charAt(4) != '0')
                        return 3;
                    ZoneOffset zoneOffset = ZoneOffset.of(zone_offset);
                    ZoneId zoneId = ZoneId.of(ZoneOffset.of(zone_offset).getId());
                }catch (Exception e){return 1;}


            }
            if (input[1].charAt(0) != '+' && input[1].charAt(0) != '-') {
                return 3;
            }


        } catch (Exception i) {
            i.printStackTrace();
        }
        return 0;
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
