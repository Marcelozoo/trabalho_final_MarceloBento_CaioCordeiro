package presenters;

import command.AutenticarCommand;
import command.Command;
import command.ExcluirCommand;
import command.Invoke;
import eventosTela.EventosTela;
import models.ResultadoOperacao;
import navegacao.TipoTela;
import services.*;
import state.EstadoTela;
import state.VisualizacaoState;
import utilidades.FormatarErros;
import views.TelaVisualizaView;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TelaVisualizacaoPresenter implements TelaPresenter {
    @Override
    public TelaPresenter getTela() {
        return null;
    }

    @Override
    public JInternalFrame getTelaView() {
        return null;
    }

    private EstadoTela estadoTela;
    private final UsuarioService usuarioService;
    private final TelaVisualizaView tela;
    private final Invoke invoke;
    private final GerenciadorTelasService gerenciadorTelas;
    private String nome;

    public TelaVisualizacaoPresenter(String nome, UsuarioService usuarioService, GerenciadorTelasService gerenciadorTelas) {

        this.nome = nome;
        this.usuarioService = usuarioService;
        this.gerenciadorTelas = gerenciadorTelas;
        this.estadoTela = new EstadoTela();
        this.tela = new TelaVisualizaView();
        this.invoke = new Invoke();

        this.estadoTela.setEstado(new VisualizacaoState(estadoTela));
        tela.setTextoDadoNomeLabel(nome);

        configBtns();
        configFechamentoTela();

    }

    private void configFechamentoTela(){
        tela.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                gerenciadorTelas.fechar(
                        TipoTela.criarTelaVisualizacaoUsuario( nome ).getTipo()
                );
            }
        });
    }

    private void configBtns(){

        tela.getBtnEditar().addActionListener(e -> {
            gerenciadorTelas.abrirEdicao(TipoTela.criarTelaEdicao(nome), nome);
        });

        tela.getBtnAutenticar().addActionListener(e -> {

            if(confirmarAcao("Deseja realmente autenticar o usuário?") == 0){
                
                Command<Void> autenticar = new AutenticarCommand(nome, usuarioService);
                invoke.setComando(autenticar);

                estadoTela.autenticar(invoke);
                ResultadoOperacao<Void> resultado = autenticar.getResultado();

                if(resultado.eValido()){
                    mostrarMensagem("Autenticado com sucesso!");
                }else{
                    mostrarMensagem(FormatarErros.unificarErros( resultado.getErros()));
                }
            }
        });

        tela.getBtnExcluir().addActionListener(e -> {
            if(confirmarAcao("Deseja realmetne excluir esse usuário?") == 0){

                Command<Void> excluir = new ExcluirCommand(nome, usuarioService);
                invoke.setComando(excluir);
                estadoTela.excluir(invoke);

                ResultadoOperacao<Void> resultado = excluir.getResultado();

                if(resultado.eValido()){
                    mostrarMensagem("Usuário excluido com sucesso!");
                    GerenciadorEventosSingleton.getInstancia().notificar(EventosTela.USUARIO_EXCLUIDO_COM_SUCESSO, null);
                }
                tela.dispose();

            }
        });
    }


    private void mostrarMensagem(String mensagem){
        tela.mostrarMensagem(mensagem);
    }

    private int confirmarAcao(String mensagem){
        return tela.confirmarAcao(mensagem);
    }



}
