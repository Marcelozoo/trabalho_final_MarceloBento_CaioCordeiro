package presenters;

import command.AtualizarNotificacaoCommand;
import command.BuscarNotificacoesNLidasCommand;
import command.Invoke;
import models.Notificacao;
import models.ResultadoOperacao;
import models.Usuario;
import navegacao.TipoTela;
import services.GerenciadorTelasService;
import services.NotificacaoService;
import state.BuscandoNotificacoesState;
import state.EstadoTela;
import utilidades.FormatarErros;
import views.TelaNotificacoesView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class TelaNotificacoesPresenter implements TelaPresenter {

    private final TelaNotificacoesView tela;
    private final Usuario usuario;
    private final NotificacaoService notificacaoService;
    private final GerenciadorTelasService gerenciadorTelas;
    private final EstadoTela estadoTela;
    private final Invoke invoke;
    private final List<Notificacao> notificacoes;
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
        this.notificacoes = new ArrayList<>();
        this.notificacoesNaoLidas = new ArrayList<>();
        this.estadoTela.setEstado(new BuscandoNotificacoesState(estadoTela));

        config();
        configBtns();

    }

    private void config() {
        buscarNotificacoes();
    }

    private void buscarNotificacoes() {
        BuscarNotificacoesNLidasCommand buscar = new BuscarNotificacoesNLidasCommand(
                usuario.getId(),
                notificacaoService
        );
        invoke.setComando(buscar);
        estadoTela.buscar(invoke);

        ResultadoOperacao<List<Notificacao>> resultado = buscar.getResultado();
        if (resultado == null || !resultado.eValido()) {
            String mensagem = resultado == null
                    ? "Não foi possível buscar notificações"
                    : FormatarErros.unificarErros(resultado.getErros());
            tela.mostrarMensagem(mensagem);
            return;
        }

        notificacoes.clear();
        notificacoesNaoLidas.clear();
        notificacoes.addAll(resultado.getResultado());
        for (Notificacao notificacao : notificacoes) {
            if (!notificacao.getFoiLida()) {
                notificacoesNaoLidas.add(notificacao);
            }
        }
        atualizarTabelas();
    }

    private void atualizarTabelas() {
        tela.limparTabelaNotificacoesNaoLidas();
        tela.limparTabelaNotificacoesLidas();

        for (Notificacao notificacao : notificacoes) {
            if (notificacao.getFoiLida()) {
                tela.inserirDadoNaTabelaNotificacoesLida(
                        notificacao.getRemetenteNome(),
                        notificacao.getConteudo()
                );
            } else {
                tela.inserirDadoNaTabelaNotificacoesNaoLida(
                        notificacao.getRemetenteNome(),
                        notificacao.getConteudo()
                );
            }
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
