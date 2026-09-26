package factory;

import models.Usuario;
import presenters.TelaNotificacoesPresenter;
import presenters.TelaPresenter;
import services.GerenciadorTelasService;
import services.ProvedorService;

public class FabricaTelaNotificacao implements FabricaDeTela {

    private final Usuario usuario;

    public FabricaTelaNotificacao(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public TelaPresenter criar(ProvedorService provedor, GerenciadorTelasService gerenciadorTelas) {
        return new TelaNotificacoesPresenter(
                usuario,
                provedor.obterNotificacaoService(),
                gerenciadorTelas
        );
    }
}
