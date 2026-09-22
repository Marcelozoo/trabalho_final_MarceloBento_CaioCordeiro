package state;

import command.Invoke;

public class AutenticacaoState  extends Estado{

    public AutenticacaoState(EstadoTela estadoTela) {
        super(estadoTela);
    }


    @Override
    public void excluir(Invoke invoke){
        invoke.executar();
        this.estadoTela.setEstado(new ExcluindoState(estadoTela));
    }

    @Override
    public void autenticar(Invoke invoke){
        invoke.executar();
        this.estadoTela.setEstado(new AutenticacaoState(estadoTela));
    }

    @Override
    public void visualizar(){
        this.estadoTela.setEstado(new VisualizacaoState(estadoTela));
    }



    @Override
    public String getEstado(){
        return "Autenticacao usuario";
    }
}
