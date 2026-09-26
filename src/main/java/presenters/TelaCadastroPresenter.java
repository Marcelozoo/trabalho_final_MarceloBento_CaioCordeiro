package presenters;

import command.CadastrarCommand;
import command.Command;
import command.Invoke;
import eventosTela.EventosTela;
import mensagens.MensagensSucesso;
import models.ResultadoOperacao;
import models.Usuario;
import navegacao.TipoTela;
import observer.Observer;
import services.GerenciadorEventosSingleton;
import services.GerenciadorTelasService;
import services.UsuarioService;
import state.CadastrandoUsuarioState;
import state.EstadoTela;
import utilidades.FormatarErros;
import views.TelaCadastroView;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TelaCadastroPresenter implements Observer, TelaPresenter {

    private final UsuarioService usuarioService;
    private final TelaCadastroView tela;
    private final GerenciadorTelasService gerenciadorTelas;
    private final EstadoTela estadoTela;
    private final Invoke invoke;
    private final boolean eAdmin;

    public TelaCadastroPresenter(boolean eAdmin, UsuarioService usuarioService, GerenciadorTelasService gerenciadorTelas) {
        this.eAdmin = eAdmin;
        this.usuarioService = usuarioService;
        this.gerenciadorTelas = gerenciadorTelas;
        this.tela = new TelaCadastroView();
        this.estadoTela = new EstadoTela();
        this.invoke = new Invoke();
        this.estadoTela.setEstado(new CadastrandoUsuarioState(estadoTela));


        btnConfigs();
        configuraFechamentoTela();
    }

    private void configuraFechamentoTela() {
        tela.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                gerenciadorTelas.fechar(TipoTela.TELA_CADASTRO.getTipo());
            }
        });
    }

    private void configuraBtnCancelar(){
        tela.getBtnCancelar().addActionListener(e -> {
            gerenciadorTelas.fechar(TipoTela.TELA_CADASTRO.getTipo());
            tela.dispose();
        });
    }

    private String obterNomeDigitado(){
        return tela.getUsuarioText();
    }
    private String obterEmailDigitado(){
        return tela.getEmailText();
    }

    private String obterSenhaDigitada(){
        return new String(tela.getSenhaText());
    }

    private String obterSenhaNovamenteDigitada(){
        return new String(tela.getSenhaNovamenteText());
    }

    private boolean obterAdminSelecionado(){
        return tela.getAdminCheckBox().isSelected();
    }
    private boolean obterAutenticadoSelecionado(){
        return tela.getAutenticadoCheckBox().isSelected();
    }


    private ResultadoOperacao<Usuario> verificarCadastroUsuario(){
        Command<Usuario> cadastrar = new CadastrarCommand(
                obterNomeDigitado(),
                obterEmailDigitado(),
                obterSenhaDigitada(),
                obterSenhaNovamenteDigitada(),
                obterAdminSelecionado(),
                obterAutenticadoSelecionado(),
                usuarioService
        );
        invoke.setComando(cadastrar);
        estadoTela.cadastrar(invoke);

        return cadastrar.getResultado();
    }


    private void configuraBtnCadastrar(){
        tela.getBtnCadastrar().addActionListener(e -> {


            ResultadoOperacao<Usuario> resultado = verificarCadastroUsuario();
            if(ocorreuErros(resultado)){return;}
            mostrarMensagem(MensagensSucesso.CADASTRO_REALIZADO.getMensagem());
            propagarEventoCadastro(resultado);
            limparCampos();
        });
    }

    private boolean ocorreuErros(ResultadoOperacao<Usuario> resultado){
        if (!resultado.eValido()) {
            tela.mostrarMensagem(FormatarErros.unificarErros(resultado.getErros()));
            return true;
        }
        return false;
    }

    private void propagarEventoCadastro(ResultadoOperacao<Usuario> resultado){
        GerenciadorEventosSingleton.getInstancia().notificar(
                EventosTela.CADASTRO_REALIZADO_COM_SUCESSO,
                resultado.getResultado()
        );
    }

    private void mostrarMensagem(String mensagem){
        tela.mostrarMensagem(mensagem);
    }

    private void limparCampos(){
        tela.limparCampos();
    }

    private void configuraBtnCheckBox(){
        if(!this.eAdmin){
            tela.desabilitarBtnAdminCheckBox();
            tela.desabilitarBtnAutenticadoCheckBox();
        }
    }

    private void btnConfigs() {
        configuraBtnCheckBox();
        configuraFechamentoTela();
        configuraBtnCancelar();
        configuraBtnCadastrar();

    }

    @Override
    public void update(EventosTela tipo, Object dados) {
    }

    public TelaCadastroView obterView() {
        return tela;
    }

    @Override
    public void fechar(){
        tela.dispose();
    }
}
