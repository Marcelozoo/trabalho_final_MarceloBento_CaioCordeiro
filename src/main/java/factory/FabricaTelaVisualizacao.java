package factory;

import models.Usuario;
import presenters.TelaVisualizacaoPresenter;
import presenters.TelaPresenter;
import services.GerenciadorTelasService;
import services.ProvedorService;

public class FabricaTelaVisualizacao implements FabricaDeTela {

    private final Usuario usuario;

    public FabricaTelaVisualizacao(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public TelaPresenter criar(ProvedorService provedor, GerenciadorTelasService gerenciadorTelas) {
        return new TelaVisualizacaoPresenter(
                usuario,
                provedor.obterUsuarioService(),
                gerenciadorTelas);
    }
}