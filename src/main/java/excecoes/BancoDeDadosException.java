package excecoes;

public class BancoDeDadosException extends RuntimeException{

    public BancoDeDadosException(String mensagem){
        super(mensagem);
    }

    public BancoDeDadosException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
