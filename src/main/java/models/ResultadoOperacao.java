package models;

import java.util.ArrayList;
import java.util.List;

public class ResultadoOperacao <T> {

    private List<String> erros;
    private boolean foiSucesso;
    private T resultado;


    public ResultadoOperacao() {
        this.erros = new ArrayList<>();
    }

    public List<String> getErros() {
        return erros;
    }

    public boolean eValido(){
        return erros.isEmpty();
    }

    public void adicionarErro(String erro) {
        this.erros.add(erro);
    }

    public void adicionarResultado(T resultado) {
        this.resultado = resultado;
    }

    public T getResultado() {
        return resultado;
    }
}
