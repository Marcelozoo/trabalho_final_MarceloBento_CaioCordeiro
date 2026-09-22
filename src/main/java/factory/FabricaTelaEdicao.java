package factory;

import presenters.TelaEditacaoPresenter;
import presenters.TelaLoginPresenter;
import presenters.TelaPresenter;
import services.GerenciadorTelasService;
import services.ProvedorService;
import services.UsuarioService;

public class FabricaTelaEdicao implements FabricaDeTela {
    private String nome;
    public FabricaTelaEdicao(String nome) {
        this.nome = nome;
    }

    @Override
    public TelaPresenter criar(ProvedorService provedorService, GerenciadorTelasService gerenciadorTelas) {
        return new TelaEditacaoPresenter(provedorService.obterUsuarioService(), gerenciadorTelas, nome);
    }
}
