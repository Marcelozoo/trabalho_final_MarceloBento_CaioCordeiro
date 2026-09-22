package state;

import command.Invoke;

public class EstadoTela {

    private Estado estado;

    public EstadoTela() {
        this.estado = new LoginState(this);
    }


    public void iniciar(){
        this.estado.iniciar();
    }
    public void login(){
        this.estado.login();
    }
    public void cadastrar(Invoke invoke){
        this.estado.cadastrar(invoke);
    }

    public void autenticar(Invoke invoke){
        this.estado.autenticar(invoke);
    }
    public void autenticarLogin(Invoke invoke){
        this.estado.autenticarLogin(invoke);
    }

    public void atualizar(Invoke invoke){
        this.estado.atualizar(invoke);
    }

    public void editar(Invoke invoke){
        this.estado.editar(invoke);
    }

    public void enviarNotificacoes(Invoke invoke){
        this.estado.enviarNotificacoes(invoke);
    }

    public void excluir(Invoke invoke){
        this.estado.excluir(invoke);
    }
    public void buscar(Invoke invoke){
        this.estado.buscar(invoke);
    }

    public void listar(Invoke invoke){
        this.estado.listar(invoke);
    }
    public void setEstado(Estado estado){
        this.estado = estado;
    }

    public String getEstado(){
        return this.estado.getEstado();
    }
}
