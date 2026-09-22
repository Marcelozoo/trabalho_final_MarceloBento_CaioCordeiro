package state;

import command.Invoke;

public class EdicaoState extends Estado{


    public EdicaoState(EstadoTela estadoTela) {
        super(estadoTela);
    }

    @Override
    public void editar(Invoke invoke){
        invoke.executar();
        estadoTela.setEstado(new EdicaoState(estadoTela));
    }

    public String getEstado(){
        return "Edição";
    }



}
