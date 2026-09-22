package state;

import command.Invoke;

public class AtualizandoState extends Estado{

    public AtualizandoState(EstadoTela estadoTela) {

        super(estadoTela);
    }

    @Override
    public void atualizar(Invoke invoke) {
        invoke.executar();
        this.estadoTela.setEstado(new AtualizandoState(estadoTela));
    }

    @Override
    public void autenticarLogin(Invoke invoke){
        invoke.executar();
        this.estadoTela.setEstado(new AutenticacaoLoginState(estadoTela));
    }

    @Override
    public void cadastrar(Invoke invoke) {
        invoke.executar();
        this.estadoTela.setEstado(new CadastroState(estadoTela));
    }

    @Override
    public String getEstado(){
        return "Atualizando";
    }
}
