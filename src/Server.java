import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;


public class Server {
    public static final int PORT = 4000;
    private ServerSocket serverSocket;

    static Scanner scan = new Scanner(System.in);

    public void start() throws IOException {
        System.out.println("\nServidor iniciado na porta " + PORT);
        serverSocket = new ServerSocket(PORT);
        clientConnectionLoop();
    }

    private void clientConnectionLoop() throws IOException {
        while (true) {
            ClientSocket clientSocket = new ClientSocket(serverSocket.accept());
            new Thread(() -> clientMessageLoop(clientSocket)).start();
        }
    }

    public static boolean conferirChecksumXor(String mensagemCompleta){
		String mensagem = mensagemCompleta.substring(0, 85);
		String checkOrigem = mensagemCompleta.substring(85, 87);
		int calculo=0;
		String check;
		byte[] bytes= mensagem.getBytes();

		for(int i=0;i<bytes.length;i++) {
			calculo ^= bytes[i];
		}
		check=calculo+"";

		if (checkOrigem.equals(check)) {
			System.out.println("Checksum válido");
			return true;
		}
		System.out.println("Checksum inválido:"+check+"!="+checkOrigem);
		return false;
	}
   
    public void clientMessageLoop(ClientSocket clientSocket) {
        String msg = null;

        try {
            while((msg = clientSocket.getMessage()) != null) {
                if ("sair".equalsIgnoreCase(msg)) {
                    System.out.println("\nCliente saiu!\n");
                    return;
                } else if ("transferir".equalsIgnoreCase(msg)) {
                    System.out.printf("Mensagem recebida do cliente %s: %s\n", clientSocket.getRemoteSocketAddress(), msg);
                } else {
                    System.out.printf("Mensagem recebida do cliente %s: %s\n", clientSocket.getRemoteSocketAddress(), msg);
                }
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
