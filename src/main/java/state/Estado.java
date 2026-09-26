package state;

import command.Invoke;
import excecoes.TransicaoEstadoInvalidaException;

public abstract class Estado {

    protected EstadoTela estadoTela;

    protected Estado(EstadoTela estadoTela) {
        this.estadoTela = estadoTela;
    }

    protected void transicaoInvalida(String operacao) {
        throw new TransicaoEstadoInvalidaException(getClass().getSimpleName(), operacao);
    }

    public void logar() {
        transicaoInvalida("logar");
    }

    public void editar(Invoke invoke) {
        transicaoInvalida("editar");
    }

    public void cadastrar(Invoke invoke) {
        transicaoInvalida("cadastrar");
    }

    public void autenticarLogin(Invoke invoke) {
        transicaoInvalida("autenticarLogin");
    }

    public void autenticar(Invoke invoke) {
        transicaoInvalida("autenticar");
    }

    public void excluir(Invoke invoke) {
        transicaoInvalida("excluir");
    }

    public void visualizar() {
        transicaoInvalida("visualizar");
    }

    public void logado() {
        transicaoInvalida("logado");
    }

    public void enviarNotificacoes(Invoke invoke) {
        transicaoInvalida("enviarNotificacoes");
    }

    public void atualizar(Invoke invoke) {
        transicaoInvalida("atualizar");
    }

    public void buscar(Invoke invoke) {
        transicaoInvalida("buscar");
    }

    public void listar(Invoke invoke) {
        transicaoInvalida("listar");
    }

    public abstract String getEstado();
}
