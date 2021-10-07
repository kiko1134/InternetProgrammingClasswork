import java.io.*;
import java.net.Socket;

public class Client {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip,port);
        out = new PrintWriter(clientSocket.getOutputStream(),true);
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

    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.startConnection("127.0.0.1",6666);//ip-to na koeto vurvi servera i porta, na koito server slusha

        System.out.println(client.sendMessage("hello server"));
        System.out.println(client.sendMessage("hello"));
        System.out.println(client.sendMessage("hell"));
        System.out.println(client.sendMessage("!!"));
        System.out.println(client.sendMessage("!"));
        System.out.println(client.sendMessage("."));
    }
}
