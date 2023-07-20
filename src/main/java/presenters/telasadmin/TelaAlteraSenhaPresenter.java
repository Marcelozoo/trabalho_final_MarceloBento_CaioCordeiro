package presenters.telasadmin;

import com.pss.senha.validacao.ValidadorSenha;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import models.usuario.Usuario;
import presenters.TelaPrincipalPresenter;
import com.ufes.logadapter.GerenciadorDeArquivoService;
import views.TelaAlteraSenhaView;

public class TelaAlteraSenhaPresenter {

    final private TelaAlteraSenhaView telaAlteraSenha;
    final private ValidadorSenha validaSenha;
    final private Usuario user;
    final private GerenciadorDeArquivoService gerenciadorArquivo;
    final private TelaPrincipalPresenter telaP;
    final private String OPERACAO = "Alterar Senha";

    public TelaAlteraSenhaPresenter(Usuario user, TelaPrincipalPresenter telaP) {
        this.telaAlteraSenha = new TelaAlteraSenhaView();
        this.validaSenha = new ValidadorSenha();
        this.gerenciadorArquivo = new GerenciadorDeArquivoService();
        this.telaP = telaP;

        this.telaAlteraSenha.setVisible(true);
        this.telaAlteraSenha.setLocationRelativeTo(null);
        this.user = user;
        configuraBtns();

    }

    public TelaAlteraSenhaView getTelaAlteraSenhaView() {
        return this.telaAlteraSenha;
    }

    private void configuraBtns() {
        telaAlteraSenha.getBtnConfirmar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    getLabelSenha();
                } catch (RuntimeException excecao) {
                    gerenciadorArquivo.processarLog(
                            excecao.getMessage(),
                            telaP.getTipoArquivo(),
                            OPERACAO,
                            user.getNome(),
                            LocalDate.now(),
                            LocalTime.now(),
                            true);
                    JOptionPane.showMessageDialog(telaAlteraSenha, excecao.getMessage());
                }
            }
        });
    }

    private void campoVazio(String campo, String campoNovamente) {
        if (campo.isEmpty() || campoNovamente.isEmpty()) {
            throw new RuntimeException("Preencha os campos vazios!");
        }
    }

    private void getLabelSenha() {
        JTextField campoSenha = telaAlteraSenha.getCampoSenha();
        JTextField campoSenhaNovamente = telaAlteraSenha.getCampoSenhaNovamente();
        campoVazio(campoSenha.getText(), campoSenhaNovamente.getText());
        if (!verificaSenhaIguais(campoSenha.getText(), campoSenhaNovamente.getText())) {
            throw new RuntimeException("Senhas não coincidem!");
        }
        List<String> lista = this.validaSenha.validar(campoSenha.getText());
        verificaListaValidacao(lista);
        this.user.setSenha(campoSenha.getText());
        this.gerenciadorArquivo.processarLog(
                "",
                telaP.getTipoArquivo(),
                OPERACAO,
                user.getNome(),
                LocalDate.now(),
                LocalTime.now(),
                true);
        JOptionPane.showMessageDialog(telaAlteraSenha, "Senha Alterada com sucesso!");
        this.telaAlteraSenha.dispose();

    }

    private void verificaListaValidacao(List<String> lista) {
        StringBuilder builder = new StringBuilder();
        if (!lista.isEmpty()) {
            for (String item : lista) {
                builder.append(item);
                builder.append("\n");
            }
            throw new RuntimeException(builder.toString());
        }
    }

    private boolean verificaSenhaIguais(String senha, String senhaNovamente) {
        return senha.equals(senhaNovamente);

    }

}
