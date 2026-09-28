package state;

import command.Invoke;

public class AutenticandoUsuarioState extends Estado {

    public AutenticandoUsuarioState(EstadoTela estadoTela) {
        super(estadoTela);
    }

    @Override
    public void autenticarLogin(Invoke invoke) {
        invoke.executar();
        estadoTela.setEstado(new AutenticandoUsuarioState(estadoTela));
    }

    @Override
    public void logar(){
        estadoTela.setEstado(new LogandoState(estadoTela));
    }



    @Override
    public String getEstado() {
        return "Autenticando usuário";
    }
}
