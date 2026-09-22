package state;

import command.Invoke;

public class VisualizacaoState extends Estado{


    public VisualizacaoState(EstadoTela estadoTela) {
        super(estadoTela);
    }


    @Override
    public void excluir(Invoke invoke){
        invoke.executar();
        this.estadoTela.setEstado(new ExcluindoState(estadoTela));
    }

    @Override
    public void visualizar(){
        this.estadoTela.setEstado(new VisualizacaoState(estadoTela));
    }

    @Override
    public void autenticar(Invoke invoke){
        invoke.executar();
        this.estadoTela.setEstado(new AutenticacaoState(estadoTela));
    }

    @Override
    public String getEstado(){
        return "Visualizar";
    }
}
