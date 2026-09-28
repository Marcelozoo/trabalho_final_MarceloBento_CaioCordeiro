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

    public static TelaPresenter telaCadastro(boolean eAdmin,ProvedorService provedor, GerenciadorTelasService gerenciadorTelas) {
        return new FabricaTelaCadastro(eAdmin).criar(provedor, gerenciadorTelas);
    }

    public static TelaPresenter telaAdmin(Usuario usuario, ProvedorService provedor, GerenciadorTelasService gerenciadorTelas) {
        return new FabricaTelaAdmin(usuario).criar(provedor, gerenciadorTelas);
    }

    public static TelaPresenter telaVisualizacao(Usuario usuario, ProvedorService provedor, GerenciadorTelasService gerenciadorTelas) {
        return new FabricaTelaVisualizacao(usuario).criar(provedor, gerenciadorTelas);
    }

    public static TelaPresenter telaEnviarNotificacoes(Usuario usuario, ProvedorService provedor, GerenciadorTelasService gerenciadorTelas) {
        return new FabricaTelaEnviarNotificacoes(usuario).criar(provedor, gerenciadorTelas);
    }

    public static TelaPresenter telaEdicao(Usuario usuario, ProvedorService provedor, GerenciadorTelasService gerenciadorTelas) {
        return new FabricaTelaEdicao(usuario).criar(provedor, gerenciadorTelas);
    }

    public static TelaPresenter telaNotificacao(Usuario usuario, ProvedorService provedor, GerenciadorTelasService g) {
        return new FabricaTelaNotificacao(usuario).criar(provedor, g);
    }

    public static TelaPresenter telaUsuarioComum(Usuario usuarioAutenticado, ProvedorService provedor, GerenciadorTelasService g) {
        return new FabricaTelaUsuarioComum(usuarioAutenticado).criar(provedor, g);
    }


}