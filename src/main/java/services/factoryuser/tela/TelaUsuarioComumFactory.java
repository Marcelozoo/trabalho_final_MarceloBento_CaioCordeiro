package services.factoryuser.tela;

import java.util.ArrayList;
import javax.swing.JInternalFrame;
import models.usuario.Usuario;
import presenters.TelaPrincipalPresenter;
import presenters.TelaUsuarioComumPresenter;


public class TelaUsuarioComumFactory extends TelaUsuarioFactory {
    @Override
    public JInternalFrame getTelaUsuario(Usuario user,  ArrayList<Usuario> lista, TelaPrincipalPresenter tela){
        return new TelaUsuarioComumPresenter(user, tela).getTelaUsuarioComumView();
    }
}
