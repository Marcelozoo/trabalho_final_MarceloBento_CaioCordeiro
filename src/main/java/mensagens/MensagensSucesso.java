package mensagens;

public enum MensagensSucesso {

    CADASTRO_REALIZADO("Cadastro realizado com sucesso."),
    USUARIO_ATUALIZADO("Usuário atualizado com sucesso."),
    USUARIO_AUTENTICADO("Usuário autenticado com sucesso."),
    USUARIO_EXCLUIDO("Usuário excluído com sucesso."),
    NOTIFICACAO_ENVIADA("Notificação enviada com sucesso."),
    NOTIFICACAO_MARCADA_LIDA("Notificação marcada como lida.");

    private final String mensagem;

    MensagensSucesso(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }
}
