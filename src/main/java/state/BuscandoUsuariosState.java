package state;

import command.Invoke;

public class BuscandoUsuariosState extends Estado {

    public BuscandoUsuariosState(EstadoTela estadoTela) {
        super(estadoTela);
    }

    @Override
    public void buscar(Invoke invoke) {
        invoke.executar();
        estadoTela.setEstado(new BuscandoUsuariosState(estadoTela));
    }

    @Override
    public void listar(Invoke invoke){
        invoke.executar();
        estadoTela.setEstado(new BuscandoUsuariosState(estadoTela));
    }



    @Override
    public String getEstado() {
        return "Buscando usuários";
    }
}
