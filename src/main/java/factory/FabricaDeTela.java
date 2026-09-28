package factory;

import presenters.TelaPresenter;
import services.GerenciadorTelasService;
import services.ProvedorService;

public interface FabricaDeTela {

    TelaPresenter criar(ProvedorService provedor, GerenciadorTelasService gerenciadorTelas);
}