package factory;

import models.Usuario;
import presenters.TelaPresenter;
import presenters.TelaUsuarioPresenter;
import presenters.TelaVisualizacaoPresenter;
import services.GerenciadorTelasService;
import services.ProvedorService;

public class FabricaTelaUsuarioComum implements FabricaDeTela {
    private final Usuario usuario;

    public FabricaTelaUsuarioComum(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public TelaPresenter criar(ProvedorService provedor, GerenciadorTelasService gerenciadorTelas) {
        return new TelaUsuarioPresenter(
                usuario,
                provedor.obterUsuarioService(),
                gerenciadorTelas);
    }

}
