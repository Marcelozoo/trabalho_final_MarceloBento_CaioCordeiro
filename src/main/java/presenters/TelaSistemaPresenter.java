package presenters;

import presenters.state.telasistemastate.StatePresenter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import models.usuario.Usuario;
import presenters.state.telasistemastate.InicializaState;
import services.usuario.GerenciaLoginService;
import views.TelaSistemaView;
import javax.swing.JTextField;
import services.factoryuser.tela.TelaUsuarioFactory;

public class TelaSistemaPresenter {

    final private TelaSistemaView telaSistema;
    private StatePresenter estado;

    public TelaSistemaPresenter(ArrayList<Usuario> lista, TelaPrincipalPresenter telaP) {

        this.telaSistema = new TelaSistemaView();
        estado = new InicializaState(this, lista, telaP);

        configuraBtnsTela(lista, telaP);
    }

    private void configuraBtnsTela(ArrayList<Usuario> lista, TelaPrincipalPresenter telaP) {

        telaSistema.getBtnCadastrar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrar();
            }
        });

        telaSistema.getBtnLogin().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    gerenciarLogin(lista, telaP);
                } catch (RuntimeException excecao) {
                    JOptionPane.showMessageDialog(null, excecao.getMessage());
                }
            }
        });
    }

    private void gerenciarLogin(ArrayList<Usuario> lista, TelaPrincipalPresenter telaP) {
        String nome = telaSistema.getCampoTextoNome().getText();
        String senha = telaSistema.getCampoTextoSenha().getText();
        GerenciaLoginService gerenciaLogin = new GerenciaLoginService();

        verificaCamposVazios(telaSistema.getCampoTextoNome(), telaSistema.getCampoTextoSenha());

        Usuario user = gerenciaLogin.verificaLogin(nome, senha, lista);
        limpaCampos(telaSistema.getCampoTextoNome(), telaSistema.getCampoTextoSenha());
        TelaUsuarioFactory tela = TelaUsuarioFactory.getTelaUsuarioFactory(user.getIsAdmin(), user);
        telaP.atualiza(tela.getTelaUsuario(user, lista, telaP));
    }

    private void verificaCamposVazios(JTextField campoNome, JTextField campoSenha) {
        if (campoNome.getText().isEmpty() || campoSenha.getText().isEmpty()) {
            throw new RuntimeException("Preencha os campos vazios!");
        }
    }

    public JInternalFrame getTelaSistemaView() {
        return this.telaSistema;
    }

    public void inicializar() {
        estado.inicializar();
    }

    public void cadastrar() {
        estado.cadastrar();
    }

    public void setEstado(StatePresenter novoEstado) {
        estado = novoEstado;
    }

    private void limpaCampos(JTextField campoNome, JTextField campoSenha) {
        campoNome.setText("");
        campoSenha.setText("");
    }
}
