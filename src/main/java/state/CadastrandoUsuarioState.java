package state;

import command.Invoke;

public class CadastrandoUsuarioState extends Estado {

    public CadastrandoUsuarioState(EstadoTela estadoTela) {
        super(estadoTela);
    }

    @Override
    public void cadastrar(Invoke invoke) {
        invoke.executar();
        estadoTela.setEstado(new CadastrandoUsuarioState(estadoTela));
    }

    @Override
    public String getEstado() {
        return "Cadastrando usuário";
    }
}
