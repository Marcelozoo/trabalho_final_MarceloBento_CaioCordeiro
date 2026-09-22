package state;

import command.Invoke;

public class LoginState extends Estado{

    public LoginState(EstadoTela estadoTela) {
        super(estadoTela);

    }



    @Override
    public void cadastrar(Invoke invoke){
        invoke.executar();
        this.estadoTela.setEstado(new CadastroState(estadoTela));

    }

    public void autenticarLogin(Invoke invoke){
        invoke.executar();
        this.estadoTela.setEstado(new AutenticacaoLoginState(estadoTela));
    }
    public String getEstado(){
        return "Login";
    }
}
