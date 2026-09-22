package dao;

import factory.ConexaoFactory;
import models.Usuario;
import org.jboss.resteasy.spi.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class UsuarioDAOSQLite implements UsuariosDAO {

    private Connection conexao2;

    public  UsuarioDAOSQLite(){

    }

    @Override
    public void inserir(Usuario usuario) {
        String sql = "INSERT INTO Usuarios (nome, senha_hash, is_admin, foi_autenticado,preferencia_log) VALUES (?, ?, ?, ?, ?)" ;

        try(Connection conexao = ConexaoFactory.criarConexao()){

            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getSenha());
            stmt.setBoolean(3, usuario.getIsAdmin());
            stmt.setBoolean(4, usuario.getIsAutenticado());
            stmt.setString(5, usuario.getPreferenciaLog());

            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Não foi posssível inserir um usuário ao banco." + "\n" + e.getMessage());
        }

    }

    public void autorizar(String nome) {
        String sql = "UPDATE usuarios SET foi_autenticado = ? WHERE nome = ?";
        try(Connection conexao = ConexaoFactory.criarConexao()){
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setBoolean(1, true);
            stmt.setString(2, nome);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao autorizar usuário ao banco");
        }
    }
    @Override
    public void atualizarPreferencia(Usuario usuario){
        String sql = "UPDATE usuarios SET preferencia_log = ? WHERE id = ?";

        try(Connection conexao = ConexaoFactory.criarConexao()){
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, usuario.getPreferenciaLog());
            stmt.setInt(2, usuario.getId());
            stmt.executeUpdate();
        }catch(SQLException e){
            System.out.println("Erro ao atualizar a preferencia de log no banco");
        }

    }
    @Override
    public void atualizarNome(String nome){
        String sql = "UPDATE usuarios SET nome = ? , senha_hash = ? WHERE nome = ?";

        try(Connection conexao = ConexaoFactory.criarConexao()){
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.setString(3, nome);
            stmt.executeUpdate();
        }catch(SQLException e){
            System.out.println("Erro ao atualizar Senha ao banco");
        }
    }

    @Override
    public void atualizarSenha(String nome, String senha) {

        String sql = "UPDATE usuarios SET senha_hash = ? WHERE id = ?";

        try(Connection conexao = ConexaoFactory.criarConexao()){
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, senha);
            stmt.setString(2, nome);
            stmt.executeUpdate();
        }catch(SQLException e){
            System.out.println("Erro ao atualizar Senha ao banco");
        }
    }

    @Override
    public void excluir(String nome) {
        String sql = "DELETE FROM usuarios WHERE nome = ?";

        try (Connection conexao = ConexaoFactory.criarConexao()){
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao deletar um usuario");
        }

    }


    public Usuario buscarPorNome(String nome) {
        String sql = "SELECT * FROM usuarios WHERE nome = ?";
        Usuario usuario = null;

        try(Connection conexao = ConexaoFactory.criarConexao()){
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setSenha(rs.getString("senha_hash"));
                usuario.setIsAdmin(rs.getBoolean("is_admin"));
                usuario.setAutenticacao(rs.getBoolean("foi_autenticado"));
                usuario.setPreferenciaLog(rs.getString("preferencia_log"));
                usuario.setCriadoEm(rs.getString("criado_em"));

            }

        }catch(SQLException e){
            throw new NotFoundException("Erro ao buscar usuario por nome");
        }

        return usuario;

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
                usuario.setNome(rs.getString("nome"));
                usuario.setSenha(rs.getString("senha_hash"));
                usuario.setIsAdmin(rs.getBoolean("is_admin"));
                usuario.setAutenticacao(rs.getBoolean("foi_autenticado"));
                usuario.setPreferenciaLog(rs.getString("preferencia_log"));
                usuario.setCriadoEm(rs.getString("criado_em"));

            }

        }catch(SQLException e){
            System.out.println("Erro ao buscar usuario");
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
                    usuario.setNome(rs.getString("nome"));
                    usuario.setSenha(rs.getString("senha_hash"));
                    usuario.setIsAdmin(rs.getBoolean("is_admin"));
                    usuario.setAutenticacao(rs.getBoolean("foi_autenticado"));
                    usuario.setPreferenciaLog(rs.getString("preferencia_log"));
                    usuario.setCriadoEm(rs.getString("criado_em"));

                    usuarios.add(usuario);
                }
            }

        catch (Exception e) {
            throw new RuntimeException("Não foi posssível inserir um usuário ao banco." + "\n" + e.getMessage());
        }
        return usuarios;

    }
}
