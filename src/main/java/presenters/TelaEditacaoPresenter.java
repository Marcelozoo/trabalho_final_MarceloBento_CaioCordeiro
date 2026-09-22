package presenters;

import command.Invoke;
import services.GerenciadorTelasService;
import services.UsuarioService;
import state.EdicaoState;
import state.EstadoTela;
import views.TelaEditarView;

import javax.swing.*;

public class TelaEditacaoPresenter implements TelaPresenter {
    private final TelaEditarView tela;
    private EstadoTela estadoTela;
    private final Invoke invoke;
    private final GerenciadorTelasService gerenciadorTelas;

    public TelaEditacaoPresenter(UsuarioService service, GerenciadorTelasService gerenciadorTelasService, String nome) {
        tela = new TelaEditarView();
        this.estadoTela = new EstadoTela();
        this.invoke = new Invoke();
        this.gerenciadorTelas = gerenciadorTelasService;
        this.estadoTela.setEstado(new EdicaoState(estadoTela));

    }


    private void configBts(){
        tela.getBtnConfirmar().addActionListener(e -> {

        });
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
