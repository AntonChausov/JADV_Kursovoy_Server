import java.io.*;
import java.util.Scanner;

public class Main {

    static final String PATH_TO_SETTINGS = "settings.txt";

    public static void main(String[] args) throws IOException {

        int port = getPort();
        if (port == 0) {
            return;
        }
        System.out.println("port: " + port);
        Server server = new Server(port);
        new Thread(server).start();
    }

    private static int getPort() {
        File file = new File(PATH_TO_SETTINGS);
        String port = "";
        if (file.exists()) {
            port = WorkWithFiles.readString(PATH_TO_SETTINGS);
            if (!port.matches("[-+]?\\d+")) {
                port = "";
            }
        }
        if (port.equals("")) {
            System.out.println("Can't find or read settings-file. Please enter port: ");
            Scanner sc = new Scanner(System.in);
            while (true) {
                port = sc.nextLine();
                if (port.matches("[-+]?\\d+")) {
                    break;
                }
                System.out.println("Try again");
            }
            WorkWithFiles.writeString(port, PATH_TO_SETTINGS);
        }
        return Integer.parseInt(port);
    }
}
