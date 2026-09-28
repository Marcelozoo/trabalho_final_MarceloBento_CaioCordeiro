package presenters;

import views.TelaUsuarioView;

import javax.swing.*;

public class TelaUsuarioPresenter implements TelaPresenter{


    @Override
    public void fechar(){
        tela.dispose();
    }

    private final TelaUsuarioView tela;


    public TelaUsuarioPresenter() {
        this.tela = new TelaUsuarioView();
    }

    private void config(){
//        tela.getBtnNotificacoes().setText();
    }



}
