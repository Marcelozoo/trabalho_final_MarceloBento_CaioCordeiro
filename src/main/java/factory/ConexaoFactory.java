package factory;

import excecoes.FalhaConexaoException;
import excecoes.enums.MensagensErroBanco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoFactory {

    private static final String URL_PADRAO = "jdbc:sqlite:banco/meuBanco.db";
    private static final String PROPRIEDADE_URL = "database.url";

    public static Connection criarConexao()  {
        try {
            String url = System.getProperty(PROPRIEDADE_URL, URL_PADRAO);
            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new FalhaConexaoException(MensagensErroBanco.FALHA_CONEXAO.getTexto(), e);
        }
    }
}
