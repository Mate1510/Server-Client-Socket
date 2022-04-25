import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private String SERVER_ADDRESS = "localhost";
    private ClientSocket clientSocket;
    private Scanner scanner;
    private Transferencia transferir = new Transferencia();

    public Client() {
        scanner = new Scanner(System.in);
    }

    public void start() throws IOException {
        System.out.print("\nInforme o IP da máquina que deseja se conectar: ");
        SERVER_ADDRESS = scanner.nextLine();

        clientSocket = new ClientSocket(new Socket(SERVER_ADDRESS, Server.PORT));

        System.out.println("Cliente conectado ao servidor em " + SERVER_ADDRESS + ":" + Server.PORT);
        messageLoop();
    }

    public static int checksum (String msg) {
		byte[] bytes = msg.getBytes();
		int checksum = 0;

		for (int i = 0; i < bytes.length; i++) {
			checksum ^= bytes[i];
		}
		return checksum;
	}

    public void menuBanco() {
        System.out.println("\n\tBanco X\n");
        System.out.println("Menu:");
        System.out.println("1 - Cadastrar conta");
        System.out.println("2 - Sacar");
        System.out.println("3 - Depositar");
        System.out.println("4 - Transferir");
        System.out.println("5 - Empréstimo");
        System.out.println("\n-Digite 'sair' para finalizar a conexão!-\n");
        System.out.print("Escreva o comando desejado ou o número desejado: ");     
    }

    public void menuTransferencia() {
        System.out.println("\n\n\tTransferência");
        System.out.print("\nInsira a Agência de Origem: ");
        transferir.setAgenciaOrigem(scanner.nextLine());
        System.out.print("\nInsira a Conta de Origem: ");
        transferir.setContaOrigem(scanner.nextLine());
        System.out.print("\nInsira a Agência de Destino: ");
        transferir.setAgenciaDestino(scanner.nextLine());
        System.out.print("\nInsira a Conta de Destino: "); 
        transferir.setContaDestino(scanner.nextLine());
        System.out.print("\nInsira o valor a transferir: ");
        transferir.setValor(scanner.nextInt());
    }

    public static String padLeftZeros(String inputString, char pad, int length) {
		if (inputString.length() >= length) {
			return inputString;
		}
		StringBuilder sb = new StringBuilder();
		while (sb.length() < length - inputString.length()) {
			sb.append(pad);
		}
		sb.append(inputString);

		return sb.toString();
	}

	public static String stringComEspacos(String ip, String comando, String mensagem) {
		String mensagemFinal = null;

		mensagemFinal = padLeftZeros(ip, ' ',20);
		mensagemFinal += padLeftZeros(comando, ' ',15);
		mensagemFinal += padLeftZeros(mensagem, ' ',50);
		mensagemFinal += padLeftZeros(checksum(mensagemFinal) + "", ' ', 2);

		return mensagemFinal;
	}

    private void messageLoop() throws IOException {
        String msg;

        do {
            menuBanco();
            msg = scanner.nextLine();
            clientSocket.sendMessage(msg);

            if (!msg.equals("sair")) {
                do {
                    menuTransferencia();
                    if (transferir.getErro() != 200) {
                        System.out.println("\nTransação inválida!\n\tERRO400");
                    }
                } while(transferir.getErro() != 200);

                String endereco = SERVER_ADDRESS + ":" + Server.PORT;
                String mensagem = transferir.getAgenciaOrigem() + transferir.getContaOrigem()
                + transferir.getAgenciaDestino() + transferir.getContaDestino(); 
        
                String mensagemFinal = stringComEspacos(endereco, "Transferência", mensagem);
                clientSocket.sendMessage(mensagemFinal);

                System.out.println("\n" + mensagemFinal);

                scanner.nextLine();
            }
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
