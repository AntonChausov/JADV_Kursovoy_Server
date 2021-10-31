import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    private ArrayList<Clients> clients = new ArrayList();

    public Server (int port) {
        Socket clientSocket = null;
        ServerSocket serverSocket = null;
        Logger logger = Logger.getInstance();
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server start!");
            logger.log("Server start!");
            while (true) {
                clientSocket = serverSocket.accept();
                Clients client = new Clients(clientSocket, this);
                clients.add(client);
                new Thread(client).start();
                //How stop it?
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        finally {
            try {
                clientSocket.close();
                System.out.println("Server stop");
                logger.log("Server stop");
                serverSocket.close();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void sendToAllUsers(String msg, Clients thisUser) {
        for (Clients user : clients) {
            if (user.equals(thisUser)) {
                continue;
            }
            user.sendMsg(msg);
        }
    }

    public void removeClient(Clients client) {
        clients.remove(client);
    }
}
