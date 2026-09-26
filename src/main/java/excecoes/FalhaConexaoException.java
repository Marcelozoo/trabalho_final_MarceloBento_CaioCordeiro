package excecoes;

public class FalhaConexaoException extends BancoDeDadosException {

    public FalhaConexaoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
