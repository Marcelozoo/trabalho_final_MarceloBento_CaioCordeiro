package state;

import command.Invoke;

public class AutenticacaoLoginState extends Estado{

    public AutenticacaoLoginState(EstadoTela estadoTela) {
        super(estadoTela);
    }


    @Override
    public void autenticarLogin(Invoke invoke){
        invoke.executar();
        this.estadoTela.setEstado(new AutenticacaoLoginState(estadoTela));
    }

    @Override
    public void login(){
        this.estadoTela.setEstado(new LoginState(estadoTela));
    }
    @Override
    public String getEstado(){
        return "Autenticacao Login";
    }
}