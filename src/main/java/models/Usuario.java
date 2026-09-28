package models;

import java.util.ArrayList;


public class Usuario {

    private int id;
    private String nome;
    private String email;
    private String senha;
    private boolean isAdmin;
    private boolean foiAutenticado;
    private String preferenciaLog;
    private String criadoEm;

    private ArrayList<Notificacao> notificacoes;
    private Integer qtdNotificacao = 0;
    private Integer qtdNotificacoesNLidas;
    private Integer qtdNotificacoesLidas = 0;
    private Integer qtdNotificacoesEnviadas = 0;


    public Usuario(){}
    public Usuario(String nome, String senha, boolean isAdmin, boolean foiAutenticado) {

        this.nome = nome;
        this.senha = senha;
        this.isAdmin = isAdmin;
        this.foiAutenticado = foiAutenticado;
        this.criadoEm = null;
        this.notificacoes = new ArrayList<>();
        this.preferenciaLog = "JSON";
    }

    public void addNotificacao(Notificacao msg) {
        this.notificacoes.add(msg);
    }
    public void setGetQtdNotificacoesNLidas(int qtd){
        this.qtdNotificacoesNLidas = qtd;
    }
    public void setId(int id){
        this.id = id;
    }
    public void setNome(String nome){
        this.nome = nome;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public void setIsAdmin(boolean isAdmin){
        this.isAdmin = isAdmin;
    }
    public void setAutenticacao(boolean autenticado) {this.foiAutenticado = autenticado;}
    public void setPreferenciaLog(String preferenciaLog) {
        this.preferenciaLog = preferenciaLog;
    }
    public void setCriadoEm(String criadoEm){
        this.criadoEm = criadoEm;
    }
    public void setQtdNotificacao(Integer qtdNotificacao) {
        this.qtdNotificacao = qtdNotificacao;
    }
    public void setFoiAutenticado(boolean foiAutenticado) {
        this.foiAutenticado = foiAutenticado;
    }
    public void setNotificacoesEnviadas(Integer qtd){
        this.qtdNotificacao = qtd;
    }

    public Integer getQtdNotificacoesNLidas(){
        return qtdNotificacoesNLidas;
    }
    public int getId() {return this.id;}
    public String getNome() {
        return this.nome;
    }
    public String getSenha() {
        return this.senha;
    }
    public boolean getIsAdmin() {
        return this.isAdmin;
    }
    public boolean getIsAutenticado() {return this.foiAutenticado;}
    public String getPreferenciaLog() {return preferenciaLog;}
    public String getCriadoEm(){
        return this.criadoEm;
    }
    public Integer getQtdNotificacoesLidas(){
        return this.qtdNotificacoesLidas;
    }
    public ArrayList<Notificacao> getNotificacoes() {
        return this.notificacoes;
    }
    public int getQtdNotificacoesEnviadas() {return qtdNotificacoesEnviadas;}

    @Override
    public String toString(){
        return "ID: " + this.id + "\n" +
                "NOME: " + this.nome + "\n"+
                "SENHA: "  + this.senha + "\n"  +
                "É ADMIN: " + this.isAdmin + "\n" +
                "FOI AUTENTICADO: " + this.foiAutenticado + "\n" +
                "PREFERENCIA LOG: " + this.preferenciaLog +  "\n" +
                "DATA CRIACAO: " + this.criadoEm + "\n";
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail(){
        return this.email;
    }
}
