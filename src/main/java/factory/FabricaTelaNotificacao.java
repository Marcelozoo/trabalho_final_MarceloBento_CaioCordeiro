package factory;

import presenters.TelaNotificacoesPresenter;
import presenters.TelaPresenter;
import services.GerenciadorTelasService;
import services.ProvedorService;

public class FabricaTelaNotificacao implements FabricaDeTela {

    public FabricaTelaNotificacao() {

    }

    @Override
    public TelaPresenter criar(ProvedorService provedor, GerenciadorTelasService gerenciadorTelas) {
        return new TelaNotificacoesPresenter();
    }
}
