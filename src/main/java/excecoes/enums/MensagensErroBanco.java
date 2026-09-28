package excecoes.enums;

public enum MensagensErroBanco {

    FALHA_BUSCAR_USUARIO_POR_ID("Não foi possível buscar o usuário pelo ID fornecido no banco."),
    FALHA_BUSCAR_USUARIO_POR_EMAIL("Não foi possível buscar o usuário pelo email fornecido no banco."),
    FALHA_BUSCAR_USUARIOS_POR_NOME("Não foi possível buscar usuários pelo nome fornecido no banco."),
    FALHA_EXCLUIR_USUARIO("Não foi possível excluir o usuário usando ID no banco"),
    FALHA_LISTAR_USUARIOS("Não foi possível listar os usuários do banco."),
    FALHA_ATUALIZAR_USUARIO("Não foi possível atualizar as informações do usuário no banco."),
    FALHA_INSERIR_USUARIO("Não foi possível inserir o usuário no banco."),
    FALHA_CONEXAO("Não foi possível conectar ao banco de dados.");

    private final String texto;

    MensagensErroBanco(String texto) {
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }
}

