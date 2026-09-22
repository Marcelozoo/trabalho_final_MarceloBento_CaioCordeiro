package services;

import com.pss.senha.validacao.ValidadorSenha;
import dao.UsuariosDAO;
import models.ResultadoOperacao;
import models.Usuario;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;
import utilidades.FormatarErros;

public class CadastroService {


    private UsuariosDAO usuariosDAO;

    public CadastroService(UsuariosDAO usuariosDAO) {
        this.usuariosDAO = usuariosDAO;

    }

    public void setUsuarioDAO(UsuariosDAO usuariosDAO){
        this.usuariosDAO = usuariosDAO;
    }

    public ResultadoOperacao<Usuario> cadastrarUsuario(String nome, String senha, String senhaNovamente){

        ResultadoOperacao<Usuario> resultadoOperacao = new ResultadoOperacao<>();
        if(nome.isEmpty() || senha.isEmpty() || senhaNovamente.isEmpty()){
            resultadoOperacao.adicionarErro("Campos não podem ficar vazios");
        }


        if(this.usuariosDAO.buscarPorNome(nome) != null){
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
            if(this.usuariosDAO.listarTodos().isEmpty() ){
                this.usuariosDAO.inserir(new Usuario(nome, BCrypt.hashpw(senha, BCrypt.gensalt()),  true, true));

            }else{
                this.usuariosDAO.inserir(new Usuario(nome, BCrypt.hashpw(senha, BCrypt.gensalt()),  false, false));
            }
        }

//        for (Usuario usuario : this.usuariosDAO.listarTodos()){
//            System.out.println(usuario.toString() + "\n");
//        }

        resultadoOperacao.adicionarResultado(null);
        return resultadoOperacao;
    }



}
