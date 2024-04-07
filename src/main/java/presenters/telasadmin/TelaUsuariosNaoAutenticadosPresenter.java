package presenters.telasadmin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import models.usuario.Usuario;
import presenters.TelaPrincipalPresenter;
import com.ufes.logadapter.services.GerenciadorDeArquivoService;
import views.UsuariosNaoAutenticadosView;

public class TelaUsuariosNaoAutenticadosPresenter {

    final private UsuariosNaoAutenticadosView tela;
    final private JTable lista;
    final private ArrayList<Usuario> listaUser;
    final private GerenciadorDeArquivoService gerenciadorArquivo;
    final private TelaPrincipalPresenter telaP;
    final private String OPERACAO = "Autorização de usuário";

    public TelaUsuariosNaoAutenticadosPresenter(ArrayList<Usuario> lista, TelaPrincipalPresenter telaP) {

        this.tela = new UsuariosNaoAutenticadosView();
        this.tela.setVisible(true);
        this.tela.setLocationRelativeTo(null);
        this.lista = this.tela.getTabelaUser();
        this.listaUser = lista;
        this.gerenciadorArquivo = new GerenciadorDeArquivoService();
        this.telaP = telaP;

        mostraListaUsuariosNaoAutenticados(lista);
        configuraBtns();

    }

    private void configuraBtns() {
        tela.getBtnAutenticar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                autenticarUsuarioSelecionadoNaTabela();
            }
        });
    }

    private void autenticarUsuarioSelecionadoNaTabela() {
        int linhaSelecionada = this.lista.getSelectedRow();
        if (linhaSelecionada != -1) {

            DefaultTableModel modelo = (DefaultTableModel) lista.getModel();

            Object[] valoresLinha = new Object[modelo.getColumnCount()];
            for (int i = 0; i < modelo.getColumnCount(); i++) {
                valoresLinha[i] = modelo.getValueAt(linhaSelecionada, i);
            }
            achaUsuarioSelecionado(valoresLinha[0].toString());
        }
    }

    private void achaUsuarioSelecionado(String nome) {
        for (int i = 0; i < listaUser.size(); i++) {
            if (listaUser.get(i).getNome().equals(nome)) {
                listaUser.get(i).setAutenticacao(true);
                this.gerenciadorArquivo.processarLog(
                        "",
                        telaP.getTipoArquivo(),
                        OPERACAO,
                        listaUser.get(i).getNome(),
                        LocalDate.now(),
                        LocalTime.now(),
                        true);
                JOptionPane.showMessageDialog(null, "Usuário autenticado com sucesso!");
                this.tela.dispose();
            }
        }
    }

    private void mostraListaUsuariosNaoAutenticados(ArrayList<Usuario> lista) {
        DefaultTableModel tabela = (DefaultTableModel) this.lista.getModel();
        tabela.setRowCount(0);

        for (int i = 0; i < lista.size(); i++) {
            if (!lista.get(i).getIsAutenticado()) {
                Object[] dados = { lista.get(i).getNome() };
                tabela.addRow(dados);
            }
        }

    }

}
