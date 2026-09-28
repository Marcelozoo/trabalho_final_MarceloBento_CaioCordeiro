package presenters;

import command.AutenticarLoginCommand;
import command.Invoke;
import models.ResultadoOperacao;
import models.Usuario;
import navegacao.TipoTela;
import services.GerenciadorTelasService;
import services.UsuarioService;
import state.EstadoTela;
import state.LogandoState;
import utilidades.FormatarErros;
import views.TelaLoginView;

import javax.swing.*;

public class TelaLoginPresenter implements TelaPresenter {

    private final TelaLoginView tela;
    private final UsuarioService usuarioService;
    private Invoke invokeCommands;
    private EstadoTela estadoTela;
    private final GerenciadorTelasService gerenciadorTelas;

    public TelaLoginPresenter(UsuarioService usuarioService, GerenciadorTelasService gerenciadorTelas) {

        this.usuarioService = usuarioService;
        this.gerenciadorTelas = gerenciadorTelas;
        this.invokeCommands = new Invoke();
        this.estadoTela = new EstadoTela();
        this.tela = new TelaLoginView();

        this.estadoTela.setEstado(new LogandoState(this.estadoTela));


        configuraBtns();
    }

    private void configuraBtns(){

        configuraBtnLogar();
        configuraBtnCadastrar();

    }

    private void configuraBtnLogar(){
        tela.getBtnLogar().addActionListener(e -> {

            ResultadoOperacao<Usuario> resultado = autenticarUsuario();

            if (!resultado.eValido()){
                tela.mostrarMensagem(FormatarErros.unificarErros(resultado.getErros()));
                return;
            }

            logarUsuario(resultado);


        });
    }

    private String obterEmailDigitado(){
        return tela.getEmailText().getText();
    }

    private String obterSenhaDigitada(){
        return tela.getSenhaText().getText();
    }

    private ResultadoOperacao<Usuario> autenticarUsuario(){
        AutenticarLoginCommand autenticar = new AutenticarLoginCommand(obterEmailDigitado(),obterSenhaDigitada(), usuarioService);
        invokeCommands.setComando(autenticar);
        estadoTela.autenticarLogin(invokeCommands);

        return autenticar.getResultado();
    }

    private void logarUsuario(ResultadoOperacao<Usuario> resultado){
        Usuario usuarioAutenticado = resultado.getResultado();

        gerenciadorTelas.fecharTudo();

        if(usuarioAutenticado.getIsAdmin()){
            gerenciadorTelas.abrirAdmin(TipoTela.TELA_ADMIN, usuarioAutenticado);
        }else{
            gerenciadorTelas.abrirUsuarioComum(TipoTela.TELA_USUARIO_COMUM, usuarioAutenticado);
        }
    }

    private void configuraBtnCadastrar(){
        tela.getBtnCadastrar().addActionListener(e -> {
            gerenciadorTelas.abrirCadastro(TipoTela.TELA_CADASTRO, false);
        });
    }

    private void fecharTela(){
        gerenciadorTelas.fechar(TipoTela.TELA_LOGIN.getTipo());
        tela.dispose();
    }


    @Override
    public void fechar(){
        tela.dispose();
    }
}
