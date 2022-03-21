import java.io.*;
import java.net.Socket;
import java.net.SocketAddress;
import java.io.IOException;

public class ClientSocket {
    private final  Socket socket;
    private final BufferedReader in;
    private final PrintWriter out;

    public ClientSocket(Socket socket) throws IOException {
        this.socket = socket;
        System.out.println("Cliente " + socket.getRemoteSocketAddress() + " conectou");
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
    }

    public SocketAddress getRemoteSocketAddress() {
        return socket.getRemoteSocketAddress();
    }

    public void close() {
        try {
            in.close();
            out.close();
           socket.close();
        } catch(IOException ex) {
            System.out.println("Erro ao fechar socket: " + ex.getMessage());
        }
    }

    public String getMessage() {
        try {
            return in.readLine();
        } catch(IOException ex) {
            return null;
        }
    }

    public boolean sendMessage(String msg) {
        out.println(msg);
        return !out.checkError();
    }
}
