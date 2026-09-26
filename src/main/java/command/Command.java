package command;

import models.ResultadoOperacao;

public interface Command<T> {

    void executar();
    ResultadoOperacao<T> getResultado();
}