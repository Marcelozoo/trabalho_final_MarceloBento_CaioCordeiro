package presenters;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import models.usuario.Usuario;
import views.TelaPrincipalView;

public class TelaPrincipalPresenter {

    private final TelaPrincipalView telaPrincipal;
    final private JDesktopPane painelDesktop;
    private ArrayList<Usuario> listaUser;
    private TelaConfiguraLogPresenter telaConfig;

    public TelaPrincipalPresenter(ArrayList<Usuario> lista) {
        this.telaPrincipal = new TelaPrincipalView();
        this.telaPrincipal.setVisible(true);
        this.telaPrincipal.setLocationRelativeTo(null);
        this.telaPrincipal.setExtendedState(JFrame.MAXIMIZED_BOTH);
        painelDesktop = this.telaPrincipal.getDesktop();
        this.listaUser = lista;
        
        configBtnsTela();
        new TelaSistemaPresenter(listaUser, this);
    }

    public void atualiza(JInternalFrame tela) {
        this.painelDesktop.revalidate();
        this.painelDesktop.repaint();
        this.painelDesktop.add(tela);
    }

    public void remove(JInternalFrame tela) {
        this.painelDesktop.revalidate();
        this.painelDesktop.repaint();
        this.painelDesktop.remove(tela);
    }
    
    private void configBtnsTela(){
        telaPrincipal.getBtnConfigura().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                telaConfig = new TelaConfiguraLogPresenter();
             
            }
        });
    }
    
    public String getTipoArquivo(){
        // json é o default
        if(this.telaConfig == null){
            return "Json";
        }
        return telaConfig.getTipoArquivo();
        
    }
    
}
