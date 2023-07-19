package presenters.state.telasistemastate;

import java.util.ArrayList;
import models.usuario.Usuario;
import presenters.TelaPrincipalPresenter;
import presenters.TelaSistemaPresenter;



public class StatePresenter {

    protected TelaSistemaPresenter tela;
    protected ArrayList<Usuario> lista;
    protected TelaPrincipalPresenter telaP;
    
    public StatePresenter(TelaSistemaPresenter estado, ArrayList<Usuario> lista, TelaPrincipalPresenter telaP) {
        this.tela = estado;
        this.lista = lista;
        this.telaP  = telaP;
    }

    public void cadastrar() {
        throw new RuntimeException("Não é possível cadastrar em uma tela de " + getNomeEstado());
    }    
    public void inicializar(){
        throw new RuntimeException("Não é possível logar em um tela de " + getNomeEstado());
    }
    
    private String getNomeEstado() {
        return this.getClass().getSimpleName().toLowerCase();
    }
    
   

}
