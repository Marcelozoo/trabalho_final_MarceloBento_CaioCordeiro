package models.notificacao;


public class Notificacao {
    final private String msg;
    private boolean foiLida;
    final private String remetente;
    
    public Notificacao(String mensagem, String remetente){
        this.msg = mensagem;
        this.foiLida = false;
        this.remetente = remetente;
    }
    
    public String getRemetente(){
        return remetente;
    }
    
    public boolean getLida(){
        return foiLida;
    }
    
    public void setFoiLida(){
        foiLida = true;
    }

    public String getMsg() {
        return msg;
    }
}
