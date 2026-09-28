package presenters;

import models.Usuario;
import navegacao.TipoTela;
import services.GerenciadorTelasService;
import services.UsuarioService;
import views.TelaUsuarioView;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TelaUsuarioPresenter implements TelaPresenter{

    private final TelaUsuarioView tela;
    private Usuario usuario;
    private final GerenciadorTelasService gerenciadorTelas;
    private final UsuarioService usuarioService;




    public TelaUsuarioPresenter(Usuario usuario, UsuarioService usuarioService, GerenciadorTelasService gerenciadorTelas) {
        this.tela = new TelaUsuarioView();
        this.usuario = usuario;
        this.usuarioService = usuarioService;
        this.gerenciadorTelas = gerenciadorTelas;

        configuraBtns();
        configuraRodape();
        //configuraNotificacoesNLidas();
    }

    private void config(){
//        tela.getBtnNotificacoes().setText();
    }

    private void configuraRodape() {

        tela.mudarNomeLabel(usuario.getNome());
        tela.mudarTipoLabel(usuario.getIsAdmin() ? "Admin" : "Usuário Comum");
    }

    private void configuraBtns(){
        configuraBtnFechamentoTela();
        configurarBtnSair();
        configuraBtnNotificacoes();
        configuraNotificacoesNLidas();

    }

    private void configuraNotificacoesNLidas(){
        tela.setQtdNotificacoesNLidas(Integer.toString(this.usuario.getQtdNotificacoesNLidas()) + " Notificações não lidas");
    }

    private void configuraBtnNotificacoes(){
        tela.getBtnNotificacoes().addActionListener(e -> {
            gerenciadorTelas.abrirNotificacoes(TipoTela.TELA_NOTIFICACOES, usuario);
        });
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

    private void fecharTela(){
        gerenciadorTelas.fechar(TipoTela.TELA_USUARIO_COMUM.getTipo());
        tela.dispose();
    }
    @Override
    public void fechar(){
        tela.dispose();
    }



}
