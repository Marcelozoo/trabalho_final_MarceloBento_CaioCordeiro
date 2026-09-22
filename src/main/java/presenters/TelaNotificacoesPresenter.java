package presenters;

import views.TelaNotificacoesView;

import javax.swing.*;

public class TelaNotificacoesPresenter implements TelaPresenter {
    private final TelaNotificacoesView tela;



    public TelaNotificacoesPresenter() {
        this.tela = new TelaNotificacoesView();

        iniciarComponentes();
    }



    private void iniciarComponentes() {
        for (int i = 0; i < 3; i++) {
            tela.inserirDadoNaTabelaNotificacoesNaoLida("marcelo", "azzuzzl");
        }
    }


    private void config(){


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
