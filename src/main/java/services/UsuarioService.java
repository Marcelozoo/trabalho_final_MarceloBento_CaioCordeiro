package services;

import com.pss.senha.validacao.ValidadorSenha;
import dao.UsuariosDAO;
import models.ResultadoOperacao;
import models.Usuario;
import org.mindrot.jbcrypt.BCrypt;
import utilidades.FormatarErros;

import java.util.List;

public class UsuarioService {

    private UsuariosDAO usuarioDAO;

    public  UsuarioService(UsuariosDAO usuariosDAO) {
        this.usuarioDAO = usuariosDAO;
    }

    public void autenticarUsuario(String nome, ResultadoOperacao<Void> resultado){
        Usuario usuario = usuarioDAO.buscarPorNome(nome);

        if(usuario != null){
            if(!usuario.getIsAutenticado()) {
                usuarioDAO.autorizar(nome);
            }else{
                resultado.adicionarErro("Usuário já está autorizado!");
            }
        }else{
            resultado.adicionarErro("Usuário não pode ser nulo");
        }

        resultado.adicionarResultado(null);
    }
    public ResultadoOperacao<Usuario> autenticarLogin(String nome, String senha){

        ResultadoOperacao<Usuario> resultado = new ResultadoOperacao<>();



        if(nome.isEmpty() || senha.isEmpty()){
            resultado.adicionarErro("Campos não podem ficar em branco!");
        }

        Usuario usuario = usuarioDAO.buscarPorNome(nome);
        if(usuario == null){
            resultado.adicionarErro("Algum campo inválido!");
            resultado.adicionarResultado(null);
            return resultado;
        }

        String senhaBanco = usuario.getSenha();
        if (!BCrypt.checkpw(senha, senhaBanco)) {
            resultado.adicionarErro("Algum campo inválido!");
        }

        if(!usuario.getIsAutenticado()){
            resultado.adicionarErro("Usuário precisa ser autorizado por um admin!");

        }

        resultado.adicionarResultado(usuario);
        return resultado;


    }

    public ResultadoOperacao<Usuario> buscar(String nome, ResultadoOperacao<Usuario> resultado){


        if(nome == null || nome.isEmpty()){
            resultado.adicionarErro("Usuário não pode ser vazio!");
            resultado.adicionarResultado(null);
        }else{
            Usuario usuario = this.usuarioDAO.buscarPorNome(nome);
            if(usuario == null){
                resultado.adicionarErro("Usuário não encontrado!");
                resultado.adicionarResultado(null);
                return resultado;
            }
            resultado.adicionarResultado(usuario);
        }

        return resultado;

    }

    public ResultadoOperacao<Usuario> cadastrarUsuario(String nome, String senha, String senhaNovamente){

        ResultadoOperacao<Usuario> resultadoOperacao = new ResultadoOperacao<>();
        if(nome.isEmpty() || senha.isEmpty() || senhaNovamente.isEmpty()){
            resultadoOperacao.adicionarErro("Campos não podem ficar vazios");
        }


        if(this.usuarioDAO.buscarPorNome(nome) != null){
            resultadoOperacao.adicionarErro("Usuário inválido");

        }

        if(!senha.equals(senhaNovamente)){
            resultadoOperacao.adicionarErro("As senhas não coincidem");
        }

        ValidadorSenha validadorSenha = new ValidadorSenha();
        List<String> erros = validadorSenha.validar(senha);

        if(!erros.isEmpty()){
            resultadoOperacao.adicionarErro(FormatarErros.unificarErros(erros));
        }

        if(resultadoOperacao.eValido()){
            if(this.usuarioDAO.listarTodos().isEmpty() ){
                this.usuarioDAO.inserir(new Usuario(nome, BCrypt.hashpw(senha, BCrypt.gensalt()),  true, true));

            }else{
                this.usuarioDAO.inserir(new Usuario(nome, BCrypt.hashpw(senha, BCrypt.gensalt()),  false, false));
            }
        }

//        for (Usuario usuario : this.usuariosDAO.listarTodos()){
//            System.out.println(usuario.toString() + "\n");
//        }

        resultadoOperacao.adicionarResultado(null);
        return resultadoOperacao;
    }


    public void excluirUsuario(String nome, ResultadoOperacao<Void> resultado){
        usuarioDAO.excluir(nome);
        resultado.adicionarResultado(null);

    }

    public void listar(ResultadoOperacao<List<Usuario>> resultado) {
        List<Usuario> usuarios = usuarioDAO.listarTodos();
        if(usuarios != null) {

            resultado.adicionarResultado(usuarios);

        }

    }

    public void editar(String nome, String senha, String senhaNovamente, ResultadoOperacao<Usuario> resultado){
        if(nome == null){

        }
    }

    public void editarUsuario(String nome, String senha, String senhaNovamente, ResultadoOperacao<Usuario> resultado){
        if(nome.isEmpty()){
            resultado.adicionarErro("Campos não podem ficar vazios");
        }

        if(this.usuarioDAO.buscarPorNome(nome) != null){
            resultado.adicionarErro("Usuário inválido");
        }

        if(!senha.equals(senhaNovamente)){
            resultado.adicionarErro("As senhas não coincidem");
        }

        ValidadorSenha validadorSenha = new ValidadorSenha();
        List<String> erros = validadorSenha.validar(senha);

        if(!erros.isEmpty()){
            resultado.adicionarErro(FormatarErros.unificarErros(erros));
        }

        if(resultado.eValido()){
            this.usuarioDAO.atualizarNome(nome);
            this.usuarioDAO.atualizarSenha(nome, senha);

        }


        resultado.adicionarResultado(null);


    }

    public void editarUsuario(String senha, String senhaNovamente, ResultadoOperacao<Usuario> resultado){

    }

}
