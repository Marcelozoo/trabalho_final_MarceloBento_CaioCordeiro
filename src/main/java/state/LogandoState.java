package state;

import command.Invoke;

public class LogandoState extends Estado{

    public LogandoState(EstadoTela estadoTela) {
        super(estadoTela);
    }


    @Override
    public void logar(){estadoTela.setEstado(new LogandoState(estadoTela));}

    @Override
    public void autenticarLogin(Invoke invoke){
        estadoTela.setEstado(new AutenticandoUsuarioState(estadoTela));
        invoke.executar();
    }

    @Override
    public String getEstado() {
        return "Logando";
    }
}
