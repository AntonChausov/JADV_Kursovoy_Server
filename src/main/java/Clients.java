import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Clients implements Runnable {
    private Server server;
    private PrintWriter outMessage;
    private Scanner inMessage;
    private static String name = "";
    private static final int PORT = 3443;
    private Socket clientSocket = null;
    private static int clients_count = 0;

    public Clients(Socket socket, Server server) {
        try {
            clients_count++;
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
        try {
            while (true) {
                if (inMessage.hasNext()) {
                    String clientMessage = inMessage.nextLine();
                    if (clientMessage.endsWith("/exit")) {
                        server.sendToAllUsers(name + " left chat", this);
                        sendMsg("Bye!");
                        break;
                    } else if (clientMessage.endsWith("/start")) {
                        name = clientMessage.replace(": /start", "");
                        server.sendToAllUsers("New user connect - " + name, this);
                        server.sendToAllUsers("Users count = " + clients_count, null);
                    } else {
                        server.sendToAllUsers(clientMessage, this);
                    }
                    System.out.println(clientMessage);
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
        clients_count--;
        server.sendToAllUsers("Users count = " + clients_count, null);
    }
}