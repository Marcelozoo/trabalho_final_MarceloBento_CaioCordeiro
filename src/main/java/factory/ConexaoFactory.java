package factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoFactory {


    private static final String URL = "jdbc:sqlite:banco/meuBanco.db";

    public static Connection criarConexao() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}
