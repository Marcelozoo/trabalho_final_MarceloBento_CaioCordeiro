package presenters;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import models.usuario.Usuario;
import presenters.telasadmin.TelaAlteraSenhaPresenter;
import views.TelaUsuarioComumView;

public class TelaUsuarioComumPresenter {

    private final TelaUsuarioComumView telaUsuario;
    final private Usuario user;
    final private TelaPrincipalPresenter telaP;

    public TelaUsuarioComumPresenter(Usuario usuario, TelaPrincipalPresenter tela) {
        this.telaUsuario = new TelaUsuarioComumView();
        this.telaP = tela;
        this.user = usuario;
        
        setLocalizacaoPainel();
        alteraLabelNomeUsuario();
        alteraLabelTipoUsuario();
        configuraBtns(usuario);
    }
    
    private void configuraBtns(Usuario usuario){
        telaUsuario.getBtnAlterarSenha().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TelaAlteraSenhaPresenter alteraSenha = new TelaAlteraSenhaPresenter(user, telaP);
            }
        });
        
    }

    public JInternalFrame getTelaUsuarioComumView() {
        return this.telaUsuario;
    }
    
    

    private void alteraLabelNomeUsuario() {
        JLabel labelUser = telaUsuario.getLabelNomeUsuario();
        labelUser.setText(user.getNome());
    }

    private void alteraLabelTipoUsuario() {
        JLabel labelUser = telaUsuario.getLabelTipoUsuario();
        
        labelUser.setText("Usuário Comum");
        
    }
    
    private void setLocalizacaoPainel(){
       telaUsuario.setLocation(400, 0);
    }

}
