package dao;

import excecoes.OperacaoUsuarioDAOException;
import excecoes.enums.MensagensErroBanco;
import factory.ConexaoFactory;
import models.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class UsuarioDAOSQLite implements UsuariosDAO {


    @Override
    public void inserir(Usuario usuario) {
        String sql = "INSERT INTO Usuarios (nome, email, senha_hash, is_admin, foi_autenticado,preferencia_log) VALUES (?, ?, ?, ?, ?, ?)" ;

        try(Connection conexao = ConexaoFactory.criarConexao()){

            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            stmt.setBoolean(4, usuario.getIsAdmin());
            stmt.setBoolean(5, usuario.getIsAutenticado());
            stmt.setString(6, usuario.getPreferenciaLog());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new OperacaoUsuarioDAOException(
                    MensagensErroBanco.FALHA_INSERIR_USUARIO.getTexto(),
                    e
            );
        }

    }
    @Override
    public void atualizar(Usuario usuario){

        String sql = "UPDATE usuarios SET nome = ?, email = ?, senha_hash = ?, preferencia_log = ?, is_admin = ?, foi_autenticado = ?  WHERE id = ?";

        try(Connection conexao = ConexaoFactory.criarConexao()){
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString( 1, usuario.getNome());
            stmt.setString( 2, usuario.getEmail());
            stmt.setString( 3, usuario.getSenha());
            stmt.setString( 4, usuario.getPreferenciaLog());
            stmt.setBoolean(5, usuario.getIsAdmin());
            stmt.setBoolean(6, usuario.getIsAutenticado());
            stmt.setInt(7, usuario.getId());



            stmt.executeUpdate();
        }catch(SQLException e){
            throw new OperacaoUsuarioDAOException(MensagensErroBanco.FALHA_ATUALIZAR_USUARIO.getTexto(), e);
        }

    }
    @Override
    public void excluir(int id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";

        try (Connection conexao = ConexaoFactory.criarConexao()){
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new OperacaoUsuarioDAOException(MensagensErroBanco.FALHA_EXCLUIR_USUARIO.getTexto(), e);

        }

    }
    @Override
    public Usuario buscarPorEmail(String email){
        String sql = "SELECT * FROM  usuarios WHERE email = ?";
        Usuario usuario = null;


        try(Connection conexao = ConexaoFactory.criarConexao()){
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setEmail(rs.getString("email"));
                usuario.setNome(rs.getString("nome"));
                usuario.setSenha(rs.getString("senha_hash"));
                usuario.setIsAdmin(rs.getBoolean("is_admin"));
                usuario.setAutenticacao(rs.getBoolean("foi_autenticado"));
                usuario.setPreferenciaLog(rs.getString("preferencia_log"));
                usuario.setCriadoEm(rs.getString("criado_em"));

            }

        }catch(SQLException e){
            throw new OperacaoUsuarioDAOException(MensagensErroBanco.FALHA_BUSCAR_USUARIO_POR_EMAIL.getTexto(), e);
        }

        return usuario;
    }
    @Override
    public List<Usuario> buscarPorNome(String nome) {
        String sql = "SELECT * FROM usuarios WHERE nome COLLATE NOCASE LIKE ?";
        List<Usuario> usuarios = new ArrayList<>();

        try (Connection conexao = ConexaoFactory.criarConexao()) {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, nome + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setEmail(rs.getString("email"));
                usuario.setNome(rs.getString("nome"));
                usuario.setSenha(rs.getString("senha_hash"));
                usuario.setIsAdmin(rs.getBoolean("is_admin"));
                usuario.setAutenticacao(rs.getBoolean("foi_autenticado"));
                usuario.setPreferenciaLog(rs.getString("preferencia_log"));
                usuario.setCriadoEm(rs.getString("criado_em"));

                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            throw new OperacaoUsuarioDAOException(
                    MensagensErroBanco.FALHA_BUSCAR_USUARIOS_POR_NOME.getTexto(),
                    e
            );
        }

        return usuarios;
    }
    @Override
    public Usuario buscarPorId(int id) {
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        Usuario usuario = null;

        try(Connection conexao = ConexaoFactory.criarConexao()){
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setEmail(rs.getString("email"));
                usuario.setNome(rs.getString("nome"));
                usuario.setSenha(rs.getString("senha_hash"));
                usuario.setIsAdmin(rs.getBoolean("is_admin"));
                usuario.setAutenticacao(rs.getBoolean("foi_autenticado"));
                usuario.setPreferenciaLog(rs.getString("preferencia_log"));
                usuario.setCriadoEm(rs.getString("criado_em"));

            }

        }catch(SQLException e){
            throw new OperacaoUsuarioDAOException(MensagensErroBanco.FALHA_BUSCAR_USUARIO_POR_ID.getTexto(), e);

        }

        return usuario;
    }
    @Override
    public List<Usuario> listarTodos() {
        String sql = "SELECT * FROM Usuarios" ;
        List<Usuario> usuarios = new ArrayList<>();


        try(Connection conexao = ConexaoFactory.criarConexao()){
                Statement stmt = conexao.createStatement();
                ResultSet rs = stmt.executeQuery(sql);

                while (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setId(rs.getInt("id"));
                    usuario.setEmail(rs.getString("email"));
                    usuario.setNome(rs.getString("nome"));
                    usuario.setSenha(rs.getString("senha_hash"));
                    usuario.setIsAdmin(rs.getBoolean("is_admin"));
                    usuario.setAutenticacao(rs.getBoolean("foi_autenticado"));
                    usuario.setPreferenciaLog(rs.getString("preferencia_log"));
                    usuario.setCriadoEm(rs.getString("criado_em"));

                    usuarios.add(usuario);
                }
            }

        catch (SQLException e) {
            throw new OperacaoUsuarioDAOException(MensagensErroBanco.FALHA_LISTAR_USUARIOS.getTexto(), e);

        }
        return usuarios;

    }
}
