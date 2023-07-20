package models.usuario;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import models.notificacao.Notificacao;

public class Usuario {

    final private String nome;
    private String senha;
    final private boolean isAdmin;
    private boolean isAutenticado;
    private Integer qtdNotificacao = 0;
    private ArrayList<Notificacao> notificacoes;
    final private String dataCadastro;
    private Integer qtdNotificacoesLidas = 0;

    public Usuario(String nome, String senha, boolean isAdmin, boolean isAutenticado, LocalDate data) {
        DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        this.nome = nome;
        this.senha = senha;
        this.isAdmin = isAdmin;
        this.isAutenticado = isAutenticado;
        this.dataCadastro = data.format(formatterData);
        this.notificacoes = new ArrayList<>();
    }
    public void setNotificacoesEnviadas(Integer qtd){
        this.qtdNotificacao = qtd;
    }
    public Integer getQtdNotificacoesLidas(){
        return this.qtdNotificacoesLidas;
    }
    
    public String getData(){
        return this.dataCadastro;
    }
    public void addNotificacao(Notificacao msg) {
        this.notificacoes.add(msg);
    }

    public ArrayList<Notificacao> getNotificacoes() {
        return this.notificacoes;
    }

    public int getQtdNotificacoesEnviadas() {
        return qtdNotificacao;
    }

    public void setAutenticacao(boolean valor) {
        this.isAutenticado = valor;
    }

    public String getNome() {
        return this.nome;
    }

    public String getSenha() {
        return this.senha;
    }

    public boolean getIsAdmin() {
        return this.isAdmin;
    }

    public boolean getIsAutenticado() {
        return this.isAutenticado;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}
