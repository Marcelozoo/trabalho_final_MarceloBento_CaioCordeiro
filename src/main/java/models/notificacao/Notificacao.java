package models.notificacao;


public class Notificacao {
    private String msg;
    
    public Notificacao(String mensagem){
        this.msg = mensagem;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
