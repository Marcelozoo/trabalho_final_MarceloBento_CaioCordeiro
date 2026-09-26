package presenters;

import command.BuscarNotificacoesNLidasCommand;
import command.BuscarUsuariosCommand;
import command.Command;
import command.Invoke;
import eventosTela.EventosTela;
import models.Notificacao;
import models.ResultadoOperacao;
import models.Usuario;
import navegacao.TipoTela;
import observer.Observer;
import services.GerenciadorEventosSingleton;
import services.GerenciadorTelasService;
import services.NotificacaoService;
import services.UsuarioService;
import state.EstadoTela;
import state.LogadoState;
import utilidades.FormatarErros;
import views.TelaAdminView;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TelaAdminPresenter implements Observer, TelaPresenter {

    private final TelaAdminView tela;
    private final EstadoTela estadoTela;
    private final Invoke invokeCommands;
    private final Usuario usuario;
    private final GerenciadorTelasService gerenciadorTelas;
    private final UsuarioService usuarioService;
    private final Map<Integer, Usuario> usuariosPorLinha;
    private final NotificacaoService notificacaoService;

    public TelaAdminPresenter(
            Usuario usuario,
            GerenciadorTelasService gerenciadorTelas,
            UsuarioService usuarioService,
            NotificacaoService notificacaoService
    ) {
        this.usuario = usuario;
        this.gerenciadorTelas = gerenciadorTelas;
        this.usuarioService = usuarioService;
        this.tela = new TelaAdminView();
        this.invokeCommands = new Invoke();
        this.usuariosPorLinha = new HashMap<>();
        this.notificacaoService  = notificacaoService;


        this.estadoTela = new EstadoTela();

        this.estadoTela.setEstado(new LogadoState(estadoTela));

        GerenciadorEventosSingleton.getInstancia().registrar(this);

        configuraBtns();
        configuraRodape();
    }

    private void configuraNotificacoesNLidas(){
        tela.setQtdNotificacoesNLidas(this.usuario.getQtdNotificacoesNLidas());






    }
    private void configuraRodape() {

        tela.mudarNomeLabel(usuario.getNome());
        tela.mudarTipoLabel(usuario.getIsAdmin() ? "Admin" : "Usuário Comum");
    }

    private void configuraBtns() {

        configurarBtnSair();
        configurarBtnBuscar();
        configuraBtnNovo();
        configuraBtnVisualizar();
        configuraBtnNotificacoes();
        configuraBtnEnviarNotificacoes();
        configuraBtnFechamentoTela();

    }

    @Override
    public void update(EventosTela tipo, Object arg) {

    }
    private void configuraBtnFechamentoTela() {
        tela.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                fecharTela();
            }
        });
    }
    private void configurarBtnSair(){
        tela.getBtnSair().addActionListener(e -> {
            gerenciadorTelas.fecharTudo();
            gerenciadorTelas.abrirLogin(TipoTela.TELA_LOGIN);
        });
    }
    private void configurarBtnBuscar(){
        tela.getBtnBuscar().addActionListener(e -> {
            ResultadoOperacao<List<Usuario>> resultado = buscarUsuario();
            if (!resultado.eValido()) {
                tela.mostrarMensagem(FormatarErros.unificarErros(resultado.getErros()));
            }

        });
    }
    private ResultadoOperacao<List<Usuario>> buscarUsuario(){
        BuscarUsuariosCommand buscar = new BuscarUsuariosCommand(obterNomeDigitado(), usuarioService);
        invokeCommands.setComando(buscar);
        estadoTela.buscar(invokeCommands);

        return buscar.getResultado();
    }


    private void configuraBtnNovo(){
        tela.getBtnNovo().addActionListener(e -> {gerenciadorTelas.abrirCadastro(TipoTela.TELA_CADASTRO, true);});

    }
    private void configuraBtnVisualizar(){
        tela.getBtnVisualizar().addActionListener(e -> {
            int linha = tela.obterLinhaSelecionadaNaTabela();
            Usuario usuarioSelecionado = this.usuariosPorLinha.get(linha);
            if (usuarioSelecionado == null) {
                mostrarMensagem("Selecione um usuario");
                return;
            }
            gerenciadorTelas.abrirVisualizacao(
                    TipoTela.criarTelaVisualizacaoUsuario(usuarioSelecionado.getId()),
                    usuarioSelecionado
            );
        });
    }


    private void configuraBtnNotificacoes(){
        tela.getBtnNotificacoes().addActionListener(e ->
                gerenciadorTelas.abrirNotificacoes(TipoTela.TELA_NOTIFICACOES, usuario)
        );
    }
    private void configuraBtnEnviarNotificacoes(){

        tela.getBtnEnviarNotificacoes().addActionListener(e ->
                gerenciadorTelas.abrirEnviarNotificacoes(TipoTela.TELA_ENVIAR_NOTIFICACOES, usuario)
        );
    }

    private String obterNomeDigitado(){
        return tela.getNomeUsuarioText();
    }



    private void fecharTela(){
        gerenciadorTelas.fechar(TipoTela.TELA_ADMIN.getTipo());
        tela.dispose();
    }
    @Override
    public void fechar(){
        tela.dispose();
    }

  
    private void mostrarMensagem(String mensagem) {
        tela.mostrarMensagem(mensagem);
    }


}
