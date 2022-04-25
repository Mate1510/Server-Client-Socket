public class Transferencia {
    private String agenciaOrigem;
    private String contaOrigem;
    private String agenciaDestino;
    private String contaDestino;
    private int valor;
    private int erro;

    public String getAgenciaOrigem() {
        return this.agenciaOrigem;
    }

    public void setAgenciaOrigem(String agenciaOrigem) {
        if (agenciaOrigem.length() == 4) {
            this.agenciaOrigem = agenciaOrigem;
            this.erro = 200;
        } else {
            System.out.println("\n\nErro agência inválida!\n");
            this.erro = 400;
        }
    }

    public String getContaOrigem() {
        return this.contaOrigem;
    }

    public void setContaOrigem(String contaOrigem) {
        if(contaOrigem.length() == 6) {
            this.contaOrigem = contaOrigem;
            this.erro = 200;
        } else {
            System.out.println("\n\nErro agência inválida!\n");
            this.erro = 400;
        }
    }

    public String getAgenciaDestino() {
        return this.agenciaDestino;
    }

    public void setAgenciaDestino(String agenciaDestino) {
        if(agenciaDestino.length() == 4) {
            this.agenciaDestino = agenciaDestino;
            this.erro = 200;
        } else {
            System.out.println("\n\nErro agência inválida!\n");
            this.erro = 400;
        }
    }

    public String getContaDestino() {
        return this.contaDestino;
    }

    public void setContaDestino(String contaDestino) {
        if(contaDestino.length() == 6) {
            this.contaDestino = contaDestino;
            this.erro = 200;
        } else {
            System.out.println("\n\nErro agência inválida!\n");
            this.erro = 400;
        }
    }

    public int getValor() {
        return this.valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public int getErro() {
        return erro;
    }

    public void setErro(int erro) {
        this.erro = erro;
    }
}

