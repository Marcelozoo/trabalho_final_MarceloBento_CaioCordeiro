package state;

import command.Invoke;

public class BuscandoNotificacoesState extends Estado {

    public BuscandoNotificacoesState(EstadoTela estadoTela) {
        super(estadoTela);
    }

    @Override
    public void buscar(Invoke invoke) {
        invoke.executar();
        estadoTela.setEstado(new BuscandoNotificacoesState(estadoTela));
    }

    @Override
    public String getEstado() {
        return "Buscando notificações";
    }
}
