package main;

import java.time.LocalDate;
import java.util.ArrayList;
import models.usuario.Usuario;
import presenters.TelaPrincipalPresenter;

public class Main {

    public static void main(String[] args) {
    
        // essa lista deve ser preenchida por uma service que pega os dados do banco.
        ArrayList<Usuario> lista = new ArrayList<>();
        lista.add(new Usuario("Marcelo", "11", true, true, LocalDate.now()));
        lista.add(new Usuario("Caio", "12", false, true, LocalDate.now()));
        lista.add(new Usuario("Jesus", "13", false, false, LocalDate.now()));
        
        new TelaPrincipalPresenter(lista);
    }
}
