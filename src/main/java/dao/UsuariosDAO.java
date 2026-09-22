package dao;

import models.Usuario;

import java.util.List;

@SuppressWarnings("unused")
public interface UsuariosDAO {

    void inserir(Usuario usuario);
    void atualizarPreferencia(Usuario usuario);
    void atualizarSenha(String nome, String senha);
    void atualizarNome(String nome);
    void excluir(String nome);
    void autorizar(String nome);
    Usuario buscarPorId(int id);
    Usuario buscarPorNome(String nome);
    List<Usuario> listarTodos();


}
