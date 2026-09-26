package excecoes.enums;

public enum MensagensUsuario {

    CAMPOS_OBRIGATORIOS("Campos não podem ficar vazios."),
    CREDENCIAIS_INVALIDAS("Algum campo inválido!"),
    USUARIO_NULO("Usuário não pode ser nulo."),
    BUSCA_OBRIGATORIA("Usuário não pode ser vazio!"),
    USUARIO_NAO_ENCONTRADO("Usuário não encontrado!"),
    USUARIO_JA_AUTORIZADO("Usuário já está autorizado!"),
    USUARIO_NAO_AUTORIZADO("Usuário precisa ser autorizado por um administrador."),
    SENHAS_NAO_COINCIDEM("As senhas não coincidem."),
    EMAIL_JA_CADASTRADO("Email já cadastrado."),
    NOME_JA_CADASTRADO("Nome já cadastrado.");

    private final String mensagem;

    MensagensUsuario(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }
}
