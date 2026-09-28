package state;

import command.Invoke;

public class AtualizandoNotificacoesState extends Estado {

    public AtualizandoNotificacoesState(EstadoTela estadoTela) {
        super(estadoTela);
    }

    @Override
    public void atualizar(Invoke invoke) {
        invoke.executar();
        estadoTela.setEstado(new AtualizandoNotificacoesState(estadoTela));
    }

    @Override
    public void buscar(Invoke invoke) {
        invoke.executar();
        estadoTela.setEstado(new BuscandoNotificacoesState(estadoTela));
    }

    @Override
    public String getEstado() {
        return "Atualizando notificações";
    }
}
