import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server implements Runnable {

    private CopyOnWriteArrayList<Client> clients = new CopyOnWriteArrayList();
    private int port;

    public Server (int port) {
        this.port = port;
    }

    @Override
    public void run() {
        Socket clientSocket = null;
        ServerSocket serverSocket = null;
        Logger logger = Logger.getInstance();
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server start!");
            logger.log("Server start!");
            while (true) {
                clientSocket = serverSocket.accept();
                Client client = new Client(clientSocket, this);
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

    public void sendToAllUsers(String msg, Client thisUser) {
        for (Client user : clients) {
            if (user.equals(thisUser)) {
                continue;
            }
            user.sendMsg(msg);
        }
    }

    public void removeClient(Client client) {
        clients.remove(client);
    }
}
