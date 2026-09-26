package state;

import command.Invoke;

public class DeletandoUsuarioState extends Estado {

    public DeletandoUsuarioState(EstadoTela estadoTela) {
        super(estadoTela);
    }

    @Override
    public void excluir(Invoke invoke) {
        invoke.executar();
        estadoTela.setEstado(new DeletandoUsuarioState(estadoTela));
    }

    @Override
    public void autenticar(Invoke invoke){
        invoke.executar();
        estadoTela.setEstado(new AtualizandoUsuarioState(estadoTela));
    }

    @Override
    public String getEstado() {
        return "Deletando usuário";
    }
}
