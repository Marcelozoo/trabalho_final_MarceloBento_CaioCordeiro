package models;


public class Notificacao {
    private int id;
    private int destinatarioId;
    private int remetenteId;
    private String criadaEm;
    private boolean foiLida;
    private String conteudo;
    private String remetenteNome;


    public Notificacao() {}
    public Notificacao(String conteudo, int remetenteId, int destinatarioId) {
        this.conteudo = conteudo;
        this.remetenteId = remetenteId;
        this.destinatarioId = destinatarioId;
        this.foiLida = false;
    }

    public void setId(int id) {this.id = id;}
    public void setDestinatarioId(int destinatarioId) {
        this.destinatarioId = destinatarioId;
    }
    public void setRemetenteId(int remetenteId) {
        this.remetenteId = remetenteId;
    }
    public void setCriadaEm(String criadaEm) {this.criadaEm = criadaEm; }
    public void setFoiLida(boolean foiLida) {
        this.foiLida = foiLida;
    }
    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }


    public int getId() {
        return id;
    }
    public int getDestinatarioId() {
        return this.destinatarioId;
    }
    public int getRemetenteId() {
        return this.remetenteId;
    }
    public String getCriadaEm() {return this.criadaEm;}
    public boolean getFoiLida(){
        return foiLida;
    }
    public String getConteudo() {
        return this.conteudo;
    }

    public String getRemetenteNome() {
        return remetenteNome;
    }

    public void setRemetenteNome(String remetenteNome) {
        this.remetenteNome = remetenteNome;
    }

    @Override
    public String toString() {
        return
                "ID: " + this.id + "\n" +
                "DESTINATARIO ID: " + this.destinatarioId + "\n" +
                "REMETENTE ID: " + this.remetenteId + "\n" +
                "CRIADA EM:"  + this.criadaEm + "\n" +
                "FOI LIDA: " + this.foiLida + "\n" +
                "CONTEUDO: " + this.conteudo + "\n";
    }
}
