package presenters;

import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import models.notificacao.Notificacao;
import models.usuario.Usuario;
import views.TelaNotificacoesComumView;

public class TelaNotificacoesComumPresenter {

    final private TelaNotificacoesComumView tela;
    private JTable tabela;
    private Usuario user;

    public TelaNotificacoesComumPresenter(Usuario user) {
        this.tela = new TelaNotificacoesComumView();
        this.tela.setVisible(true);
        this.tela.setLocationRelativeTo(null);
        this.tabela = tela.getTabelaNotificacoes();
        this.user = user;
        
        mostraTabelaNotificacoes();

    }

    private void mostraTabelaNotificacoes() {
        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
        modelo.setRowCount(0);

        ArrayList<Notificacao> lista = user.getNotificacoes();
        for (int i = 0; i < lista.size(); i++) {
            if (!lista.get(i).getLida()) {
                Object[] dados = {
                    lista.get(i).getRemetente(),
                    lista.get(i).getMsg()
                };
                modelo.addRow(dados);
            }

        }

    }

}
