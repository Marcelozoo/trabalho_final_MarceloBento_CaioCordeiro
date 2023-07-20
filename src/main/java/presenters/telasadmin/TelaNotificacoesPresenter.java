package presenters.telasadmin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import models.notificacao.Notificacao;
import models.usuario.Usuario;
import presenters.TelaPrincipalPresenter;
import services.arquivoslog.GerenciadorDeArquivoService;
import views.TelaNotificacoesView;

public class TelaNotificacoesPresenter {

    private TelaNotificacoesView tela;
    private ArrayList<Usuario> lista;
    private JTable tabela;
    private StringBuilder usersSelecionados;
    private ArrayList<String> listaSelecionados;
    final private GerenciadorDeArquivoService gerenciaArquivo;
    final private TelaPrincipalPresenter telaP;

    public TelaNotificacoesPresenter(ArrayList<Usuario> lista, TelaPrincipalPresenter telaP) {
        this.lista = lista;
        this.tela = new TelaNotificacoesView();
        this.tela.setVisible(true);
        this.tela.setLocationRelativeTo(null);
        this.tabela = this.tela.getTabelaUser();
        this.usersSelecionados = new StringBuilder("");
        this.listaSelecionados = new ArrayList<>();
        this.gerenciaArquivo = new GerenciadorDeArquivoService();
        this.telaP = telaP;
        
        mostraTabela();
        configuraBtns();
    }

    private void configuraBtns() {
        tabela.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                atualizaUsuariosSelecionados();
            }
        });

        tela.getBtnSalvar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enviaParaCadaUser();
            }
        });

    }

    private void enviaParaCadaUser() {
        Integer count = 0;

        for (int i = 1; i < lista.size(); i++) {
            for (int j = 0; j < listaSelecionados.size(); j++) {
                if (lista.get(i).getNome().equals(listaSelecionados.get(j))) {
                    Notificacao notificacao = new Notificacao(
                            pegaMensagemEnviada(), 
                            lista.get(0).getNome());
                    lista.get(i).addNotificacao(notificacao);
                    count++;
                }
            }

        }
        lista.get(0).setNotificacoesEnviadas(count);
        JOptionPane.showMessageDialog(null, "Mensagem Enviada!");
        gerenciaArquivo.processarLog(
                "", 
                telaP.getTipoArquivo() ,
                "Enviar Notificação", 
                lista.get(0).getNome(), 
                LocalDate.now(), 
                LocalTime.now(), 
                true);
        tela.dispose();
    }

    private void atualizaUsuariosSelecionados() {
        int row = tabela.getSelectedRow();
        int column = tabela.getSelectedColumn();
        if (row >= 0 && column >= 0) {
            Object value = tabela.getValueAt(row, column);
            if (!verificaSeUsuarioJaFoiSelecionado(value.toString())) {
                listaSelecionados.add(value.toString());
                usersSelecionados.setLength(0);
                criaStringUsuariosSelecionados();
                getUsuarioSelecionados();
            }
        }
    }

    private boolean verificaSeUsuarioJaFoiSelecionado(String value) {
        for (int i = 0; i < listaSelecionados.size(); i++) {
            if (listaSelecionados.get(i).equals(value)) {
                return true;
            }
        }
        return false;
    }

    private void criaStringUsuariosSelecionados() {
        for (int i = 0; i < listaSelecionados.size(); i++) {
            usersSelecionados.append(listaSelecionados.get(i));
            usersSelecionados.append(" ");
        }

    }

    private void mostraTabela() {

        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
        modelo.setRowCount(0);
        for (int i = 1; i < this.lista.size(); i++) {
            Object[] dados = {
                this.lista.get(i).getNome(),
                lista.get(i).getData(),
                lista.get(i).getQtdNotificacoesEnviadas(),
                lista.get(i).getQtdNotificacoesLidas()
            };
            modelo.addRow(dados);
        }
    }

    private void getUsuarioSelecionados() {
        this.tela.getLabelUsuarios().setText(usersSelecionados.toString());
    }

    private String pegaMensagemEnviada() {
        return this.tela.getjTextArea1().getText();
    }

}
