package services.factoryuser.tela;

import java.util.ArrayList;
import javax.swing.JInternalFrame;
import models.usuario.Usuario;
import presenters.TelaPrincipalPresenter;
import presenters.telasadmin.TelaAdminPresenter;


public class TelaAdminFactory extends TelaUsuarioFactory {
    @Override
    public JInternalFrame getTelaUsuario(Usuario user, ArrayList<Usuario> lista, TelaPrincipalPresenter tela){
        return new TelaAdminPresenter(user, lista, tela).getTelaAdmin();
    }
}
