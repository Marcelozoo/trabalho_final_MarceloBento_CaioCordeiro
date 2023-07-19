package presenters.state.telasistemastate;


import java.util.ArrayList;
import models.usuario.Usuario;
import presenters.TelaCadastroPresenter;
import presenters.TelaPrincipalPresenter;
import presenters.TelaSistemaPresenter;



public class CadastroState extends StatePresenter {

    public CadastroState(TelaSistemaPresenter tela, ArrayList<Usuario> lista, TelaPrincipalPresenter telaP) {
        super(tela, lista, telaP);
        telaP.remove(tela.getTelaSistemaView());
        telaP.atualiza(new TelaCadastroPresenter(lista, telaP, tela).getTelaCadastroView());
    }
    @Override
    public void inicializar(){
        tela.setEstado(new InicializaState(tela, lista, telaP));
    }

}
