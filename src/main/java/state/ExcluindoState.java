package state;

import command.Invoke;

public class ExcluindoState extends Estado{

    public ExcluindoState(EstadoTela estadoTela) {
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
    public void listar(Invoke invoke){
        invoke.executar();
        this.estadoTela.setEstado(new ListandoState(estadoTela));
    }

    @Override
    public void buscar(Invoke invoke){
        invoke.executar();
        this.estadoTela.setEstado(new BuscandoState(estadoTela));
    }

    @Override
    public void cadastrar(Invoke invoke){
        invoke.executar();
        this.estadoTela.setEstado(new CadastroState(estadoTela));
    }

    @Override
    public String getEstado(){
        return "Visualizar";
    }
}
