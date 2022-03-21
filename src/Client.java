import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private String SERVER_ADDRESS = "localhost";
    private ClientSocket clientSocket;
    private Scanner scanner;

    public Client() {
        scanner = new Scanner(System.in);
    }

    public void start() throws IOException {
        clientSocket = new ClientSocket(new Socket(SERVER_ADDRESS, Server.PORT));

        System.out.println("Cliente conectado ao servidor em " + SERVER_ADDRESS + ":" + Server.PORT);
        messageLoop();
    }

    private void messageLoop() throws IOException {
        String msg;

        do {
            System.out.print("Digite uma mensagem (ou sair para finalizar): ");
            msg = scanner.nextLine();
            clientSocket.sendMessage(msg);
        } while(!msg.equals("sair"));
    }

    public static void main(String[] args) {
        try {
            Client client = new Client();
            client.start();
        } catch (IOException e) {
            System.out.println("Erro ao iniciar cliente: " + e.getMessage());
        }

        System.out.println("Cliente Finalizado");
    }
}
