package presenters;

import com.pss.senha.validacao.ValidadorSenha;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import models.usuario.Usuario;
import java.util.List;
import services.factoryuser.UsuarioFactory;
import services.usuario.GerenciaUsuariosService;
import views.TelaCadastroView;
import com.ufes.logadapter.GerenciadorDeArquivoService;


public class TelaCadastroPresenter {

    final private TelaCadastroView telaCadastro;
    final private ValidadorSenha validador;
    final private ArrayList<Usuario> lista;
    final private GerenciadorDeArquivoService gerenciadorArquivo;
    final private String OPERACAO = "Inclusão";
    private String tipoArquivo;
    

    public TelaCadastroPresenter(ArrayList<Usuario> lista, TelaPrincipalPresenter telaP, TelaSistemaPresenter tela) {
        this.telaCadastro = new TelaCadastroView();
        this.telaCadastro.setVisible(true);
        this.lista = lista;
        this.gerenciadorArquivo = new GerenciadorDeArquivoService();

        this.validador = new ValidadorSenha();
        configuraBtnRealizarCadastro(telaP, tela);

    }

    public JInternalFrame getTelaCadastroView() {
        return this.telaCadastro;
    }

    private void configuraBtnRealizarCadastro(TelaPrincipalPresenter telaP, TelaSistemaPresenter tela) {
        telaCadastro.getBtnRealizarCadastro().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    cadastraUsuario(telaP, tela);
                } catch (RuntimeException excecao) {
                    gerenciadorArquivo.processarLog
                    (
                            excecao.getMessage(),
                            tipoArquivo, 
                            OPERACAO, 
                            telaCadastro.getCampoTextoNome().getText(), 
                            LocalDate.now(), 
                            LocalTime.now(), 
                            true
                    );
                 
                    JOptionPane.showMessageDialog(null, excecao.getMessage());
                } finally {
                    limpaCampos();
                }

            }
        }
        );
    }

    private void cadastraUsuario(TelaPrincipalPresenter telaP, TelaSistemaPresenter tela) {
        String nome = telaCadastro.getCampoTextoNome().getText();
        String senha = telaCadastro.getCampoTextoSenha().getText();
        String senhaNovamente = telaCadastro.getCampoTextoSenhaNovamente().getText();
        this.verificaCamposVazios(nome, senha, senhaNovamente);
        this.verificaSenhasIguais(senha, senhaNovamente);
        this.validarSenha(senha);
        this.tipoArquivo = telaP.getTipoArquivo();

        if (lista.isEmpty()) {
            UsuarioFactory user = UsuarioFactory.getUsuarioFactory("Admin");
            lista.add(user.getUsuario(nome, senha, true, true));
            telaP.remove(telaCadastro);
            sucessoAoCadastrar();
            this.gerenciadorArquivo.processarLog("",tipoArquivo,OPERACAO, nome, LocalDate.now(), LocalTime.now(), true);
            tela.inicializar();
        } else {
            GerenciaUsuariosService gerenciaNomes = new GerenciaUsuariosService();
            if (!gerenciaNomes.verificaExistenciaNomeUsuario(nome, lista)) {
                UsuarioFactory user = UsuarioFactory.getUsuarioFactory("UsuarioComum");
                lista.add(user.getUsuario(nome, senha, false, false));
                telaP.remove(telaCadastro);
                sucessoAoCadastrar();
                this.gerenciadorArquivo.processarLog("",tipoArquivo,OPERACAO, nome, LocalDate.now(), LocalTime.now(), false);
                tela.inicializar();
            } else {
                throw new RuntimeException("Nome de usuário já existe!");
            }
        }
    }

    private void validarSenha(String senha) {
        StringBuilder builder = new StringBuilder();

        List<String> listaValidadoresSenha = this.validador.validar(senha);
        if (!listaValidadoresSenha.isEmpty()) {
            for (String item : listaValidadoresSenha) {
                builder.append(item);
                builder.append("\n");
            }
            throw new RuntimeException(builder.toString());
        }
    }

    private void sucessoAoCadastrar() {
        JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso!");
    }

    private void limpaCampos() {
        JTextField nome = this.telaCadastro.getCampoTextoNome();
        JTextField senha = this.telaCadastro.getCampoTextoSenha();
        JTextField senhaNovamente = this.telaCadastro.getCampoTextoSenhaNovamente();

        nome.setText("");
        senha.setText("");
        senhaNovamente.setText("");
    }

    private void verificaSenhasIguais(String senha, String senhaNovamente) {
        if (!senha.equals(senhaNovamente)) {
            throw new RuntimeException("As senhas não coincidem!");
        }
    }

    private void verificaCamposVazios(String nome, String senha, String senhaNovamente) {
        if (nome.isEmpty() || senha.isEmpty() || senhaNovamente.isEmpty()) {
            throw new RuntimeException("Preencha os campos vazios!");
        }
    }
     
}
