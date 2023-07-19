package services.factoryuser;

import models.usuario.Usuario;

public abstract class UsuarioFactory {
    
    public abstract Usuario getUsuario(String nome, String senha, boolean tipo, boolean autenticado);
    
    public static UsuarioFactory getUsuarioFactory(String tipoUser){
        if(tipoUser.equalsIgnoreCase("UsuarioComum")){
            return new UsuarioComumFactory(); 
        }
        else if(tipoUser.equalsIgnoreCase("Admin")){
            return new AdminFactory();
        }
        else{
            throw new IllegalArgumentException("Tipo de usuário inválido!");
        }
    }  
   
}
