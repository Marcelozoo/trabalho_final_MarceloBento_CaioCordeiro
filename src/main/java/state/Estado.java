package state;

import command.Invoke;

public abstract class Estado {
    protected EstadoTela  estadoTela;

    public Estado(EstadoTela estadoTela) {
        this.estadoTela = estadoTela;
    }


    public void iniciar(){throw new RuntimeException("Não é possivel ir para o estado inicial");}
    public void login(){
        throw new RuntimeException("Não é possível efetuar o login!");
    }

    public void editar(Invoke invoke){
        throw new RuntimeException("Não é possível ir para o estado de edição!");
    }
    public void cadastrar(Invoke invoke){
        throw new RuntimeException("Não é possível efetuar o cadastro!");
    }
    public void autenticarLogin(Invoke invoke){
        throw new RuntimeException("Login invalido!");
    }
    public void autenticar(Invoke invoke){
        throw new RuntimeException("Não foi possivel autenticar o usuario");
    }
    public void excluir(Invoke invoke){
        throw new RuntimeException("Não foi possivel ir para o estado de exclusao");
    }
    public void visualizar(){
        throw new RuntimeException("Não foi possível visuaalizar");
    }

    public void logado(){
        throw new RuntimeException("Não foi possível logar!");
    }
    public void enviarNotificacoes(Invoke invoke){
        throw new RuntimeException("nao foi possivel ir para o estado de enviara notificacoes");
    }

    public void atualizar(Invoke invoke) {throw new RuntimeException("Não foi possível atualizar");}

    public void buscar(Invoke invoke){
        throw new RuntimeException("Não foi possível buscar o usuário");
    }
    public void listar(Invoke invoke) {throw new RuntimeException("Não foi possível listar");}
    public String getEstado(){
        throw  new RuntimeException("Estado nao pode ser retornado");
    }
}
