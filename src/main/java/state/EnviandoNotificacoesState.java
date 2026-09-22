package state;

import command.Invoke;

public class EnviandoNotificacoesState extends Estado {

    public EnviandoNotificacoesState(EstadoTela estadoTela) {
        super(estadoTela);
    }


    @Override
    public void enviarNotificacoes(Invoke invoke){
        invoke.executar();
        this.estadoTela.setEstado(new EnviandoNotificacoesState(estadoTela));
    }








    @Override
    public String getEstado(){
        return "Enviando notificaacoes";
    }
}
