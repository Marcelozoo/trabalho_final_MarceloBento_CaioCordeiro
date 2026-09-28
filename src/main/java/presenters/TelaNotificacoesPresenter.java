package presenters;

import command.*;
import models.Notificacao;
import models.ResultadoOperacao;
import models.Usuario;
import navegacao.TipoTela;
import observer.Observer;
import services.GerenciadorEventosSingleton;
import services.GerenciadorTelasService;
import services.NotificacaoService;
import state.BuscandoNotificacoesState;
import state.EstadoTela;
import utilidades.FormatarErros;
import views.TelaNotificacoesView;

import java.util.ArrayList;
import java.util.List;

public class TelaNotificacoesPresenter implements TelaPresenter, Observer {

    private final TelaNotificacoesView tela;
    private final Usuario usuario;
    private final NotificacaoService notificacaoService;
    private final GerenciadorTelasService gerenciadorTelas;
    private final EstadoTela estadoTela;
    private final Invoke invoke;
    private final List<Notificacao> notificacoesLidas;
    private final List<Notificacao> notificacoesNaoLidas;

    public TelaNotificacoesPresenter(
            Usuario usuario,
            NotificacaoService notificacaoService,
            GerenciadorTelasService gerenciadorTelas
    ) {
        this.usuario = usuario;
        this.notificacaoService = notificacaoService;
        this.gerenciadorTelas = gerenciadorTelas;
        this.tela = new TelaNotificacoesView();
        this.estadoTela = new EstadoTela();
        this.invoke = new Invoke();
        this.notificacoesLidas = new ArrayList<>();
        this.notificacoesNaoLidas = new ArrayList<>();
        this.estadoTela.setEstado(new BuscandoNotificacoesState(estadoTela));

        GerenciadorEventosSingleton.getInstancia().registrar(this);

        config();
        configBtns();

    }

    @Override
    public void update(){
        buscarNotificacoesLidas();
        buscarNotificacoesNLidas();

        atualizarTabelas();
    }

    private void config() {
        buscarNotificacoes();
    }

    private void buscarNotificacoes() {

        buscarNotificacoesNLidas();
        buscarNotificacoesLidas();

        atualizarTabelas();
    }

    private void buscarNotificacoesLidas(){
        Command<List<Notificacao>> buscar = new BuscarNotificacoesLidasCommand(
                usuario.getId(),
                notificacaoService
        );
        invoke.setComando(buscar);
        estadoTela.buscar(invoke);

        ResultadoOperacao<List<Notificacao>> resultado = buscar.getResultado();


        if (!resultado.eValido()) {
            String mensagem = FormatarErros.unificarErros(resultado.getErros());
            tela.mostrarMensagem(mensagem);
            return;
        }

        notificacoesLidas.clear();
        notificacoesLidas.addAll(resultado.getResultado());

    }

    private void buscarNotificacoesNLidas(){
        Command<List<Notificacao>> buscar = new BuscarNotificacoesNLidasCommand(
                usuario.getId(),
                notificacaoService
        );
        invoke.setComando(buscar);
        estadoTela.buscar(invoke);

        ResultadoOperacao<List<Notificacao>> resultado = buscar.getResultado();


        if (!resultado.eValido()) {
            String mensagem = FormatarErros.unificarErros(resultado.getErros());
            tela.mostrarMensagem(mensagem);
            return;
        }
        notificacoesNaoLidas.clear();
        notificacoesNaoLidas.addAll(resultado.getResultado());


    }

    private void atualizarTabelas() {
        tela.limparTabelaNotificacoesNaoLidas();
        tela.limparTabelaNotificacoesLidas();

        for (Notificacao notificacao : notificacoesLidas) {
            tela.inserirDadoNaTabelaNotificacoesLida(notificacao.getRemetenteNome(), notificacao.getConteudo());
        }

        for (Notificacao notificacao : notificacoesNaoLidas){
            tela.inserirDadoNaTabelaNotificacoesNaoLida(notificacao.getRemetenteNome(), notificacao.getConteudo());
        }
    }

    private void configBtns() {
        configuraBtnLer();
        configuraBtnsFecharTela();
    }


    private void configuraBtnLer(){
        tela.getBtnLer().addActionListener(actionEvent -> {
            int linha = tela.getTabelaNotificacoesNaoLidas().getSelectedRow();
            if (linha < 0 || linha >= notificacoesNaoLidas.size()) {
                tela.mostrarMensagem("Selecione uma notificação");
                return;
            }

            Notificacao notificacao = notificacoesNaoLidas.get(linha);
            AtualizarNotificacaoCommand atualizar = new AtualizarNotificacaoCommand(
                    notificacao.getId(),
                    usuario.getId(),
                    notificacaoService
            );
            invoke.setComando(atualizar);
            estadoTela.atualizar(invoke);

            ResultadoOperacao<Void> resultado = atualizar.getResultado();
            if (resultado != null && !resultado.eValido()) {
                tela.mostrarMensagem(FormatarErros.unificarErros(resultado.getErros()));
                return;
            }

            GerenciadorEventosSingleton.getInstancia().notificar();

            buscarNotificacoes();
        });
    }

    private void configuraBtnsFecharTela(){
        tela.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                fecharTela();
            }
        });
        tela.getBtnSairNotificacoesNLidas().addActionListener(e -> fecharTela());
        tela.getBtnSairNotificacoesLidas().addActionListener(e -> fecharTela());
    }


    private void fecharTela() {
        gerenciadorTelas.fechar(TipoTela.TELA_NOTIFICACOES.getTipo());
        tela.dispose();
    }

    @Override
    public void fechar(){
        tela.dispose();
    }
}
