package excecoes;

import excecoes.enums.MensagensEstado;

public class TransicaoEstadoInvalidaException extends IllegalStateException {

    private final String estado;
    private final String operacao;

    public TransicaoEstadoInvalidaException(String estado, String operacao) {
        super(MensagensEstado.TRANSICAO_INVALIDA.formatar(estado, operacao));
        this.estado = estado;
        this.operacao = operacao;
    }

    public String getEstado() {
        return estado;
    }

    public String getOperacao() {
        return operacao;
    }
}
