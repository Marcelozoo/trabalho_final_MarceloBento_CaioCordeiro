package presenters;

import command.AutenticarLoginCommand;
import command.Invoke;
import models.ResultadoOperacao;
import models.Usuario;
import navegacao.TipoTela;
import services.GerenciadorTelasService;
import services.UsuarioService;
import state.EstadoTela;
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

        configBtns();
    }


    public void configBtns(){
        tela.getBtnLogar().addActionListener(e -> {
            String usuario = tela.getUsuarioText().getText();
            String senha =  new String(tela.getSenhaText().getPassword());

            AutenticarLoginCommand autenticar = new AutenticarLoginCommand(usuario, senha, usuarioService);
            invokeCommands.setComando(autenticar);

            estadoTela.autenticarLogin(invokeCommands);

            ResultadoOperacao<Usuario> resultado = autenticar.getResultado();

            if (!resultado.eValido()){
                tela.mostrarMensagem(FormatarErros.unificarErros(resultado.getErros()));

            }else{

                Usuario usuarioAutenticado = resultado.getResultado();

                if(usuarioAutenticado.getIsAdmin()){
                    gerenciadorTelas.abrirAdmin(TipoTela.TELA_ADMIN, usuarioAutenticado);
                }else{

                }

                tela.dispose();
            }


        });

        tela.getBtnCadastrar().addActionListener(e -> {
             gerenciadorTelas.abrirCadastro(TipoTela.TELA_CADASTRO);
        });



    }

    public TelaLoginView obterView(){
        return tela;
    }


    @Override
    public TelaPresenter getTela() {
        return null;
    }

    @Override
    public JInternalFrame getTelaView() {
        return null;
    }
}
