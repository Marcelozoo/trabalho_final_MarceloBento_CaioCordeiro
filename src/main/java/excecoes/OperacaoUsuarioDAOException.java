package excecoes;

public class OperacaoUsuarioDAOException extends BancoDeDadosException {

    public OperacaoUsuarioDAOException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
