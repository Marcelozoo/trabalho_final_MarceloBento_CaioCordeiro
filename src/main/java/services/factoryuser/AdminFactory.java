
package services.factoryuser;
import java.time.LocalDate;
import models.usuario.Usuario;

public class AdminFactory extends UsuarioFactory {
    
    @Override
    public Usuario getUsuario(String nome, String senha, boolean tipo, boolean autenticado){
        return new Usuario(nome, senha, true, autenticado, LocalDate.now());
    }
}
