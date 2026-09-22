package navegacao;

public final class TipoTela {

    public static final TipoTela TELA_LOGIN =
            new TipoTela("TELA_LOGIN");

    public static final TipoTela TELA_CADASTRO =
            new TipoTela("TELA_CADASTRO");

    public static final TipoTela TELA_ADMIN =
            new TipoTela("TELA_ADMIN");

    public static final TipoTela TELA_VISUALIZAR =
            new TipoTela("TELA_VISUALIZAR");

    public static final TipoTela TELA_ENVIAR_NOTIFICACOES =
            new TipoTela("TELA_ENVIAR_NOTIFICACOES");
    public static final TipoTela TELA_NOTIFICACOES = new TipoTela("TELA_NOTIFICACOES");

    public static final TipoTela TELA_EDITAR =
            new TipoTela("TELA_EDITAR");

    private final String nome;

    public String getTipo(){
        return nome;
    }
    private TipoTela(String nome) {
        this.nome = nome;
    }
    public static TipoTela criarTelaVisualizacaoUsuario(String idUsuario) {
        return new TipoTela("TELA_VISUALIZACAO_USUARIO_" + idUsuario);
    }

    public static TipoTela criarTelaEdicao(String idUsuario) {
        return new TipoTela("TELA_EDITAR" + idUsuario);
    }
}