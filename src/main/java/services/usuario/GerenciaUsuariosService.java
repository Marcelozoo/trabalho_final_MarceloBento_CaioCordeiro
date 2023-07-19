
package services.usuario;

import java.util.ArrayList;
import models.usuario.Usuario;


public class GerenciaUsuariosService {
    
    public boolean verificaExistenciaNomeUsuario(String nome, ArrayList<Usuario> lista){
        for(int i = 0; i < lista.size(); i++){
            if(nome.equals(lista.get(i).getNome())){
                return true;
            }
        }
        return false;
    }
}    

