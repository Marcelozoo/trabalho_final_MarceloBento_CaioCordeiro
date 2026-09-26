package dao;

import models.Usuario;

import java.util.List;

public interface UsuariosDAO {

    void inserir(Usuario usuario);
    void atualizar(Usuario usuario);
    void excluir(int id);
    Usuario buscarPorId(int id);
    Usuario buscarPorEmail(String email);
    List<Usuario> buscarPorNome(String nome);
    List<Usuario> listarTodos();


}
