package state;

import command.Invoke;

public class LogadoState extends Estado {

    public LogadoState(EstadoTela estadoTela) {
        super(estadoTela);
    }

    @Override
    public void buscar(Invoke invoke) {
        estadoTela.setEstado(new BuscandoUsuariosState(estadoTela));
        estadoTela.buscar(invoke);
    }

    @Override
    public void listar(Invoke invoke) {
        estadoTela.setEstado(new BuscandoUsuariosState(estadoTela));
        invoke.executar();

    }

    @Override
    public String getEstado() {
        return "Logado";
    }
}
