package presenters;

import eventosTela.EventosTela;
import models.ResultadoOperacao;
import navegacao.TipoTela;
import observer.Observer;
import services.GerenciadorEventosSingleton;
import services.GerenciadorTelasService;
import services.UsuarioService;
import utilidades.FormatarErros;
import views.TelaCadastroView;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TelaCadastroPresenter implements Observer, TelaPresenter {
    private final  UsuarioService usuarioService;
    private final TelaCadastroView tela;
    private final GerenciadorTelasService gerenciadorTelas;

    @Override
    public JInternalFrame getTelaView() {
        return null;
    }

    @Override
    public TelaPresenter getTela() {
        return null;
    }

    public TelaCadastroPresenter(UsuarioService usuarioService, GerenciadorTelasService gerenciadorTelas) {
        this.usuarioService = usuarioService;
        this.gerenciadorTelas = gerenciadorTelas;
        this.tela = new TelaCadastroView();

        btnConfigs();
        configFechamentoTela();

    }

    private void configFechamentoTela(){
        tela.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                gerenciadorTelas.fechar(
                        TipoTela.TELA_CADASTRO.getTipo()
                );
            }
        });
    }

    private void btnConfigs() {
        tela.getBtnCancelar().addActionListener(e -> {
            gerenciadorTelas.fechar(TipoTela.TELA_CADASTRO.getTipo());
            tela.dispose();
        });
        tela.getBtnCadastrar().addActionListener(e -> {
            String nome = tela.getUsuarioText().getText();
            char[] senha = tela.getSenhaText().getPassword();
            char[] senhaNovamente = tela.getSenhaNovamenteText().getPassword();

           ResultadoOperacao<?> resultado = usuarioService.cadastrarUsuario(nome, new String(senha), new String(senhaNovamente));

           if (!resultado.eValido()){

               tela.mostrarMensagem(
                       FormatarErros.unificarErros(resultado.getErros())
               );

           }else{
                tela.mostrarMensagem("Cadastro realizado com sucesso");
                GerenciadorEventosSingleton.getInstancia().notificar(EventosTela.CADASTRO_REALIZADO_COM_SUCESSO, null);
                tela.dispose();
            }

        });
    }


    @Override
    public void update(EventosTela tipo, Object dados) {

    }



    public TelaCadastroView obterView(){
        return tela;
    }
}
