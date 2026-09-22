package command;

public class Invoke {

    private Command<?> comando;


    public void setComando(Command<?> comando) {
        this.comando = comando;
    }

    public void executar(){
        this.comando.executar();
    }
}
