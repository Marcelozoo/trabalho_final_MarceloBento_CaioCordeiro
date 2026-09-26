package presenters;

import command.AutenticarCommand;
import command.Command;
import command.ExcluirCommand;
import command.Invoke;
import eventosTela.EventosTela;
import models.ResultadoOperacao;
import models.Usuario;
import navegacao.TipoTela;
import services.GerenciadorEventosSingleton;
import services.GerenciadorTelasService;
import services.UsuarioService;
import state.EstadoTela;
import state.VisualizandoUsuarioState;
import utilidades.FormatarErros;
import views.TelaVisualizaView;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TelaVisualizacaoPresenter implements TelaPresenter {

    private final EstadoTela estadoTela;
    private final UsuarioService usuarioService;
    private final TelaVisualizaView tela;
    private final Invoke invoke;
    private final GerenciadorTelasService gerenciadorTelas;
    private final Usuario usuarioSelecionado;

    public TelaVisualizacaoPresenter(
            Usuario usuarioSelecionado,
            UsuarioService usuarioService,
            GerenciadorTelasService gerenciadorTelas
    ) {
        this.usuarioSelecionado = usuarioSelecionado;
        this.usuarioService = usuarioService;
        this.gerenciadorTelas = gerenciadorTelas;
        this.estadoTela = new EstadoTela();
        this.tela = new TelaVisualizaView();
        this.invoke = new Invoke();
        this.estadoTela.setEstado(new VisualizandoUsuarioState(estadoTela));

        configLabels();
        configuraBtns();
    }

    private void configLabels() {
        tela.setTextoDadoNomeLabel(usuarioSelecionado.getNome());
        tela.setTextoDadoEmailLabel(usuarioSelecionado.getEmail());
        tela.setTextoDadoDataCadastroLabel(usuarioSelecionado.getCriadoEm());
        tela.setTextoDadoNotificacoesLidasLabel(
                Integer.toString(usuarioSelecionado.getQtdNotificacoesLidas())
        );
    }

    private void configuraBtnsFechamentoTela() {

        tela.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                fecharTela();
            }
        });


    }

    private void configuraBtnEditar(){
        tela.getBtnEditar().addActionListener(e -> {
            gerenciadorTelas.abrirEdicao(TipoTela.TELA_EDITAR, this.usuarioSelecionado);
        });
    }

    private void configuraBtnAutenticar(){
        tela.getBtnAutenticar().addActionListener(e -> {
            if (confirmarAcao("Deseja realmente autenticar o usuário?") != 0) {
                return;
            }

            Command<Void> autenticar = new AutenticarCommand(usuarioSelecionado, usuarioService);
            invoke.setComando(autenticar);
            estadoTela.autenticar(invoke);

            ResultadoOperacao<Void> resultado = autenticar.getResultado();
            if (resultado != null && resultado.eValido()) {
                mostrarMensagem("Autenticado com sucesso!");
            } else if (resultado != null) {
                mostrarMensagem(FormatarErros.unificarErros(resultado.getErros()));
            }
        });
    }


    private void configuraBtnExcluir(){

        tela.getBtnExcluir().addActionListener(e -> {
            if (confirmarAcao("Deseja realmente excluir esse usuário?") != 0) {
                return;
            }

            Command<Void> excluir = new ExcluirCommand(usuarioSelecionado.getId(), usuarioService);
            invoke.setComando(excluir);
            estadoTela.excluir(invoke);

            ResultadoOperacao<Void> resultado = excluir.getResultado();
            if (resultado != null && resultado.eValido()) {
                mostrarMensagem("Usuário excluido com sucesso!");
                GerenciadorEventosSingleton.getInstancia().notificar(
                        EventosTela.USUARIO_EXCLUIDO_COM_SUCESSO,
                        null
                );
            } else if (resultado != null) {
                mostrarMensagem(FormatarErros.unificarErros(resultado.getErros()));
                return;
            }
            tela.dispose();
        });
    }

    private void configuraBtns() {
        configuraBtnAutenticar();
        configuraBtnEditar();
        configuraBtnExcluir();
        configuraBtnsFechamentoTela();

    }

    private void fecharTela(){
        gerenciadorTelas.abrirEdicao(
                TipoTela.criarTelaEdicao(usuarioSelecionado.getId()),
                usuarioSelecionado
        );
    }

    private void mostrarMensagem(String mensagem) {
        tela.mostrarMensagem(mensagem);
    }

    private int confirmarAcao(String mensagem) {
        return tela.confirmarAcao(mensagem);
    }

    @Override
    public void fechar(){
        tela.dispose();
    }
}
