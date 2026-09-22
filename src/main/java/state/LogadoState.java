package state;

import command.Invoke;

public class LogadoState  extends Estado {

    public LogadoState(EstadoTela estadoTela) {
        super(estadoTela);

    }

    @Override
    public void atualizar(Invoke invoke){
        invoke.executar();
        this.estadoTela.setEstado(new AtualizandoState(estadoTela));
    }

    @Override
    public void cadastrar(Invoke invoke){
        invoke.executar();
        this.estadoTela.setEstado(new CadastroState(estadoTela));
    }


    @Override
    public void buscar(Invoke invoke){
        invoke.executar();
        this.estadoTela.setEstado(new BuscandoState(estadoTela));
    }

    @Override
    public void listar(Invoke invoke){
        invoke.executar();
        this.estadoTela.setEstado(new ListandoState(estadoTela));
    }
    public String getEstado(){
        return "Logado";
    }
}
