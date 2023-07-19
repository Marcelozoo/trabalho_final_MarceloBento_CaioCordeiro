package services.usuario;

import java.util.ArrayList;
import models.usuario.Usuario;

public class GerenciaLoginService {

    public Usuario verificaLogin(String nome, String senha, ArrayList<Usuario> lista) {
        for (int i = 0; i < lista.size(); i++) {
            if (nome.equals(lista.get(i).getNome()) && senha.equals(lista.get(i).getSenha())) {
                if(lista.get(i).getIsAutenticado()){
                    return lista.get(i);
                }
                throw new RuntimeException("Usuário ainda não foi autenticado!");
            }
        }
        throw new RuntimeException("Login Inválido!");
    }
}
