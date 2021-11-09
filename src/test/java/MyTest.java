import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class MyTest {
    @Test
    public void ServerTest() throws IOException, InterruptedException {
        Socket socket = Mockito.mock(Socket.class);
        ByteArrayInputStream myInputStream = new ByteArrayInputStream(("Hi!").getBytes(StandardCharsets.UTF_8));
        Mockito.when(socket.getInputStream())
                .thenReturn(myInputStream);
        ByteArrayOutputStream myOutputStream = new ByteArrayOutputStream();
        Mockito.when(socket.getOutputStream())
                .thenReturn(myOutputStream);
        Server server = new Server(999);
        new Thread(server).start();
        Client client = new Client(socket, server);
        client.sendMsg("Hi!");
        assertEquals(myOutputStream.toString(), "Hi!\r\n");
    }
}