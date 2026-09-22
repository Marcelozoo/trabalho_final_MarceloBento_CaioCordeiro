package factory;

import presenters.TelaVisualizacaoPresenter;
import presenters.TelaPresenter;
import services.GerenciadorTelasService;
import services.ProvedorService;

public class FabricaTelaVisualizacao implements FabricaDeTela {

    private final String nome;

    public FabricaTelaVisualizacao(String nome) {
        this.nome = nome;
    }

    @Override
    public TelaPresenter criar(ProvedorService provedor, GerenciadorTelasService gerenciadorTelas) {
        return new TelaVisualizacaoPresenter(
                nome,
                provedor.obterUsuarioService(),
                gerenciadorTelas);
    }
}