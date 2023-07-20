package presenters.telasadmin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import models.usuario.Usuario;
import presenters.TelaPrincipalPresenter;
import views.TelaAdminView;


public class TelaAdminPresenter {

    final private TelaAdminView telaAdmin;
    final private ArrayList<Usuario> lista;
    final private Usuario user;
    final private TelaPrincipalPresenter telaP;
    final private JTable tabela;
    

    public TelaAdminPresenter(Usuario usuario, ArrayList<Usuario> lista, TelaPrincipalPresenter telaP) {
        this.telaAdmin = new TelaAdminView();
        this.lista = lista;
        this.user = usuario;
        this.telaP = telaP;
        this.tabela = telaAdmin.getTabelaUsuarios();
      

        setLocalizacaoPainel();
        alteraLabelNomeUsuario();
        alteraLabelTipoUsuario();
        configuraBtns(usuario);
    }

    public TelaAdminView getTelaAdmin() {
        return telaAdmin;
    }

    private void configuraBtns(Usuario user) {
        telaAdmin.getBtnAlterarSenha().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TelaAlteraSenhaPresenter(user, telaP);
            }
        });

        telaAdmin.getBtnListarUsuarios().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                criaTabela();
            }
        });
        
        telaAdmin.getBtnAutenticar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TelaUsuariosNaoAutenticadosPresenter(lista, telaP);
            }
        });
        
        telaAdmin.getBtnEnviarNotificacao().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TelaNotificacoesPresenter(lista, telaP);
                
            }
        });
    }

    private void criaTabela() {

        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
        modelo.setRowCount(0);
        for (int i = 0; i < this.lista.size(); i++) {
            Object[] dados = {
                this.lista.get(i).getNome(),
                lista.get(i).getData(),
                lista.get(i).getQtdNotificacoesEnviadas(),
                lista.get(i).getQtdNotificacoesLidas()
            };
            modelo.addRow(dados);
        }
       
    }
    
 

    private void alteraLabelNomeUsuario() {
        JLabel labelUser = telaAdmin.getLabelNomeUsuario();
        labelUser.setText(user.getNome());
    }

    private void alteraLabelTipoUsuario() {
        JLabel labelUser = telaAdmin.getLabelTipoUsuario();
        labelUser.setText("Admin");

    }

    private void setLocalizacaoPainel() {
        telaAdmin.setLocation(400, 0);
    }

}
