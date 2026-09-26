package state;

import command.Invoke;

public class EditandoUsuarioState extends Estado {

    public EditandoUsuarioState(EstadoTela estadoTela) {
        super(estadoTela);
    }

    @Override
    public void editar(Invoke invoke) {
        invoke.executar();
        estadoTela.setEstado(new EditandoUsuarioState(estadoTela));
    }

    @Override
    public String getEstado() {
        return "Editando usuário";
    }
}
