package command;

import models.ResultadoOperacao;

public interface Command<T> {

    ResultadoOperacao<T> executar();
    ResultadoOperacao<T> getResultado();
}