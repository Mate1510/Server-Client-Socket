import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    public static final int PORT = 4000;
    private ServerSocket serverSocket;

    public void start() throws IOException {
        System.out.println("Servidor iniciado na porta " + PORT);
        serverSocket = new ServerSocket(PORT);
        clientConnectionLoop();
    }

    private void clientConnectionLoop() throws IOException {
        while (true) {
            ClientSocket clientSocket = new ClientSocket(serverSocket.accept());
            new Thread(() -> clientMessageLoop(clientSocket)).start();
        }
    }

    public void clientMessageLoop(ClientSocket clientSocket) {
        String msg;
        try {
            while((msg = clientSocket.getMessage()) != null) {
                if ("sair".equalsIgnoreCase(msg))
                    return;

                System.out.printf("Mensagem recebida do cliente %s: %s\n", clientSocket.getRemoteSocketAddress(), msg);
            }
        } finally {
            clientSocket.close();
        }
    }

    public static void main(String[] args) {
        try {
            Server server = new Server();
            server.start();
        } catch(IOException e) {
            System.out.println("Erro ao iniciar o servidor: " + e.getMessage());
        }
            
        System.out.println("Servidor Finalizado");
    }
}
