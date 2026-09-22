package state;

import command.Invoke;

public class CadastroState extends Estado {

    public CadastroState(EstadoTela estadoTela) {
        super(estadoTela);
    }

    @Override
    public void login(){
        this.estadoTela.setEstado(new LoginState(estadoTela));
    }

    @Override
    public void excluir(Invoke invoke){
        invoke.executar();
        this.estadoTela.setEstado(new ExcluindoState(estadoTela));
    }

   @Override
   public void atualizar(Invoke invoke){
        invoke.executar();
        this.estadoTela.setEstado(new AtualizandoState(estadoTela));
   }

    public String getEstado(){
        return "Cadastro";
    }
}