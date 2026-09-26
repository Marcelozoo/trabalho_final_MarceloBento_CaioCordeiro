package state;

import command.Invoke;

public class AtualizandoUsuarioState extends Estado{


    public AtualizandoUsuarioState(EstadoTela estadoTela) {
        super(estadoTela);
    }


    @Override
    public void excluir(Invoke invoke) {
        estadoTela.setEstado(new DeletandoUsuarioState(estadoTela));
        estadoTela.excluir(invoke);
    }

    @Override
    public void autenticar(Invoke invoke) {
        estadoTela.setEstado(new AtualizandoUsuarioState(estadoTela));
        estadoTela.autenticar(invoke);
    }

    @Override
    public String getEstado() {
        return "Atualizando usuário";
    }
}
