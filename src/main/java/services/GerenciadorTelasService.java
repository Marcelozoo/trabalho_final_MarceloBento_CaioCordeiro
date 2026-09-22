package services;

import factory.TelaFactory;
import models.Usuario;
import navegacao.TipoTela;
import presenters.TelaPresenter;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class GerenciadorTelasService {

    private final Map<String, TelaPresenter> telas;
    private final ProvedorService provedor;

    public GerenciadorTelasService(ProvedorService provedor) {
        this.telas = new HashMap<>();
        this.provedor = provedor;
    }

    public void abrirLogin(TipoTela tipo) {
        abrir(tipo.getTipo(), g -> TelaFactory.telaLogin(provedor, g));
    }

    public void abrirCadastro(TipoTela tipo) {
        abrir(tipo.getTipo(), g -> TelaFactory.telaCadastro(provedor, g));
    }

    public void abrirAdmin(TipoTela tipo, Usuario usuario) {
        abrir(tipo.getTipo(), g -> TelaFactory.telaAdmin(usuario, provedor, g));
    }

    public void abrirVisualizacao(TipoTela tipo, String nome) {
        abrir(tipo.getTipo(), g -> TelaFactory.telaVisualizacao(nome, provedor, g));
    }

    public void abrirEnviarNotificacoes(TipoTela tipo, Usuario usuario) {
        abrir(tipo.getTipo(), g -> TelaFactory.telaEnviarNotificacoes(usuario, provedor, g));
    }

    public void abrirEdicao(TipoTela tipo, String nome) {
            abrir(tipo.getTipo(), g -> TelaFactory.telaEdicao(nome, provedor, g));
    }

    public void abrirNotificacoes(TipoTela tipo){
        abrir(tipo.getTipo(), g -> TelaFactory.telaNotificacao(provedor, g));
    }

    public void fechar(String tipo) {
        telas.remove(tipo);
    }

    public boolean existe(String tipo) {
        return telas.containsKey(tipo);
    }

    private void abrir(String tipo, Function<GerenciadorTelasService, TelaPresenter> fabrica) {
        telas.computeIfAbsent(tipo, k -> fabrica.apply(this));
    }


}