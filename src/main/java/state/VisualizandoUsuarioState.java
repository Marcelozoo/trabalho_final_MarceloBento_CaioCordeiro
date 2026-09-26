package state;

import command.Invoke;

public class VisualizandoUsuarioState extends Estado {

    public VisualizandoUsuarioState(EstadoTela estadoTela) {
        super(estadoTela);
    }



    @Override
    public void excluir(Invoke invoke) {
        estadoTela.setEstado(new DeletandoUsuarioState(estadoTela));
        estadoTela.excluir(invoke);
    }

    @Override
    public void autenticar(Invoke invoke) {
        estadoTela.setEstado(new AtualizandoUsuarioState(estadoTela));
        estadoTela.autenticar(invoke);
    }

    @Override
    public String getEstado() {
        return "Visualizando usuário";
    }
}
