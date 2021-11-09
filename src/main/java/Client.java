import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable {
    private Server server;
    private PrintWriter outMessage;
    private Scanner inMessage;
    private static String name = "";
    private Socket clientSocket = null;
    private static int clientsCount = 0;

    public Client(Socket socket, Server server) {
        try {
            clientsCount++;
            this.server = server;
            this.clientSocket = socket;
            this.outMessage = new PrintWriter(socket.getOutputStream());
            this.inMessage = new Scanner(socket.getInputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        boolean work = true;
        Logger logger = Logger.getInstance();
        try {
            while (work) {
                if (inMessage.hasNext()) {
                    String clientMessage = inMessage.nextLine();
                    if (clientMessage.endsWith("/exit")) {
                        server.sendToAllUsers(name + " left chat", this);
                        sendMsg("Bye!");
                        work = false;
                    } else if (clientMessage.endsWith("/start")) {
                        name = clientMessage.replace(": /start", "");
                        server.sendToAllUsers("New user connect - " + name, this);
                        server.sendToAllUsers("Users count = " + clientsCount, null);
                    } else {
                        server.sendToAllUsers(clientMessage, this);
                    }
                    System.out.println(clientMessage);
                    logger.log(clientMessage);
                }
                Thread.sleep(100);
            }
        }
        catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        finally {
            this.close();
        }
    }

    public void sendMsg(String msg) {
        try {
            outMessage.println(msg);
            outMessage.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void close() {

        server.removeClient(this);
        clientsCount--;
        server.sendToAllUsers("Users count = " + clientsCount, null);
    }
}