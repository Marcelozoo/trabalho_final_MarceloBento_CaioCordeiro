package excecoes.enums;

public enum MensagensNotificacao {

    CAMPOS_INVALIDOS("Campos inválidos!"),
    USUARIO_INVALIDO("Usuário inválido."),
    USUARIOS_NAO_ENCONTRADOS("Destinatário ou remetente não encontrado!"),
    NOTIFICACAO_OU_USUARIO_INVALIDO("Notificação ou usuário inválido.");

    private final String mensagem;

    MensagensNotificacao(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }
}
