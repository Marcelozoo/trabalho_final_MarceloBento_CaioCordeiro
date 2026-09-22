package factory;

import models.Usuario;
import presenters.TelaPresenter;
import services.GerenciadorTelasService;
import services.ProvedorService;

public class TelaFactory {

    private TelaFactory() {
    }

    public static TelaPresenter telaLogin(ProvedorService provedor, GerenciadorTelasService gerenciadorTelas) {
        return new FabricaTelaLogin().criar(provedor, gerenciadorTelas);
    }

    public static TelaPresenter telaCadastro(ProvedorService provedor, GerenciadorTelasService gerenciadorTelas) {
        return new FabricaTelaCadastro().criar(provedor, gerenciadorTelas);
    }

    public static TelaPresenter telaAdmin(Usuario usuario, ProvedorService provedor, GerenciadorTelasService gerenciadorTelas) {
        return new FabricaTelaAdmin(usuario).criar(provedor, gerenciadorTelas);
    }

    public static TelaPresenter telaVisualizacao(String nome, ProvedorService provedor, GerenciadorTelasService gerenciadorTelas) {
        return new FabricaTelaVisualizacao(nome).criar(provedor, gerenciadorTelas);
    }

    public static TelaPresenter telaEnviarNotificacoes(Usuario usuario, ProvedorService provedor, GerenciadorTelasService gerenciadorTelas) {
        return new FabricaTelaEnviarNotificacoes(usuario).criar(provedor, gerenciadorTelas);
    }

    public static TelaPresenter telaEdicao(String nome, ProvedorService provedor, GerenciadorTelasService gerenciadorTelas) {
        return new FabricaTelaEdicao(nome).criar(provedor, gerenciadorTelas);
    }

    public static TelaPresenter telaNotificacao(ProvedorService provedor, GerenciadorTelasService g) {
        return new FabricaTelaNotificacao().criar(provedor, g);
    }
}