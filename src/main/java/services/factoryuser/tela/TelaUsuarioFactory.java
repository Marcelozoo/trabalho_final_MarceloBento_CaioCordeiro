package services.factoryuser.tela;

import java.util.ArrayList;
import javax.swing.JInternalFrame;
import models.usuario.Usuario;
import presenters.TelaPrincipalPresenter;


public abstract class TelaUsuarioFactory {
    
    public abstract JInternalFrame getTelaUsuario(Usuario user,  ArrayList<Usuario> lista, TelaPrincipalPresenter tela);
    
    public static TelaUsuarioFactory getTelaUsuarioFactory(boolean tipoUser, Usuario user){
        if(!tipoUser){
            return new TelaUsuarioComumFactory(); 
        }
        else{
            return new TelaAdminFactory();
        }
    }  
    
}
