package excecoes.enums;

public enum MensagensEstado {

    TRANSICAO_INVALIDA("Transição inválida: o estado %s não permite a operação %s.");

    private final String texto;

    MensagensEstado(String texto) {
        this.texto = texto;
    }

    public String formatar(String estado, String operacao) {
        return String.format(texto, estado, operacao);
    }
}
