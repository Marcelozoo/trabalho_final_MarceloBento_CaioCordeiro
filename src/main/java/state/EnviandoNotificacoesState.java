package state;

import command.Invoke;

public class EnviandoNotificacoesState extends Estado {

    public EnviandoNotificacoesState(EstadoTela estadoTela) {
        super(estadoTela);
    }

    @Override
    public void enviarNotificacoes(Invoke invoke) {
        invoke.executar();
        estadoTela.setEstado(new EnviandoNotificacoesState(estadoTela));
    }

    @Override
    public void listar(Invoke invoke) {
        invoke.executar();
    }

    @Override
    public String getEstado() {
        return "Enviando notificações";
    }
}
