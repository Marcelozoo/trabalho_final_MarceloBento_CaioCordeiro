package dao;

import com.ufes.logadapter.models.Log;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LogDAOSQLite implements LogDAO{
    private Connection conexao;

    public LogDAOSQLite() {
        try{
            this.conexao = DriverManager.getConnection("jdbc:sqlite:banco/meuBanco.db");
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco");
        }
    }

    @Override
    public void inserirLog(Log log) {
        String sql = "INSERT INTO log (operacao, nome, usuario, mensagem_falha, tipo_arquivo) VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement stmt = this.conexao.prepareStatement(sql);
            stmt.setString(1, log.getOperacao());
            stmt.setString(2, log.getNome());
            stmt.setString(3, log.getUsuario());
            stmt.setString(4, log.getMsgFalha());
            stmt.setString(5, log.getTipoArquivoLog());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao inserir log");
        }
    }

    @Override
    public List<Log> listarLogs() {
        String sql = "SELECT * FROM log";
        List<Log> logs = new ArrayList<>();

        try {
            PreparedStatement stmt = this.conexao.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {

                Log log = new Log();
                log.setId(rs.getInt("id"));
                log.setOperacao(rs.getString("operacao"));
                log.setNome(rs.getString("nome"));
                log.setUsuario(rs.getString("usuario"));
                log.setMsgFalha(rs.getString("mensagem_falha"));
                log.setData(rs.getString("data"));
                log.setHora(rs.getString("hora"));
                log.setTipoArquivoLog(rs.getString("tipo_arquivo"));

                logs.add(log);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar log do banco");
        }



        return logs;
    }
}
