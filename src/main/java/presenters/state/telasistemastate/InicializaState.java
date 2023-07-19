package presenters.state.telasistemastate;

import java.util.ArrayList;
import models.usuario.Usuario;
import presenters.TelaPrincipalPresenter;
import presenters.TelaSistemaPresenter;


public class InicializaState extends StatePresenter {
     public InicializaState(TelaSistemaPresenter tela, ArrayList<Usuario> lista, TelaPrincipalPresenter telaP) {
        super(tela, lista, telaP);
        telaP.atualiza(tela.getTelaSistemaView());
        
    }
    @Override
    public void cadastrar(){
        tela.setEstado(new CadastroState(tela, lista, telaP));
    }
   
}
