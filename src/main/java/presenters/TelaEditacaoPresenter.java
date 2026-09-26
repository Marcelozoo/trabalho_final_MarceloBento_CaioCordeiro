package presenters;

import command.Command;
import command.EdicaoCommand;
import command.Invoke;
import models.ResultadoOperacao;
import models.Usuario;
import navegacao.TipoTela;
import services.GerenciadorTelasService;
import services.UsuarioService;
import state.EditandoUsuarioState;
import state.EstadoTela;
import utilidades.FormatarErros;
import views.TelaEditarView;

import javax.swing.*;

public class TelaEditacaoPresenter implements TelaPresenter {

    private final TelaEditarView tela;
    private final EstadoTela estadoTela;
    private final Invoke invoke;
    private final Usuario usuario;
    private final UsuarioService usuarioService;
    private final GerenciadorTelasService gerenciadorTelas;

    public TelaEditacaoPresenter(
            UsuarioService usuarioService,
            GerenciadorTelasService gerenciadorTelas,
            Usuario usuario
    ) {
        this.usuarioService = usuarioService;
        this.gerenciadorTelas = gerenciadorTelas;
        this.usuario = usuario;
        this.tela = new TelaEditarView();
        this.estadoTela = new EstadoTela();
        this.invoke = new Invoke();
        this.estadoTela.setEstado(new EditandoUsuarioState(estadoTela));

        preencherCampos();
        configBts();
    }

    private void preencherCampos() {
        tela.getNomeText().setText(usuario.getNome());
        tela.getEmailText().setText(usuario.getEmail());
    }

    private void configBts() {
        configuraBtnConfirmar();
        configuraBtnCancelar();


    }

    private void configuraBtnCancelar(){
        tela.getBtnCancelar().addActionListener(e -> {
            gerenciadorTelas.fechar(TipoTela.criarTelaEdicao(usuario.getId()).getTipo());
            tela.dispose();
        });
    }

    private void configuraBtnConfirmar(){

        tela.getBtnConfirmar().addActionListener(actionEvent -> {
            String nome = tela.getNomeText().getText();
            String email = tela.getEmailText().getText();
            String senha = new String(tela.getSenhaText().getPassword());
            String senhaNovamente = new String(tela.getSenhaNovamenteText().getPassword());

            Command<Void> editar = new EdicaoCommand(
                    usuario,
                    nome,
                    email,
                    senha,
                    senhaNovamente,
                    usuarioService
            );
            invoke.setComando(editar);
            estadoTela.editar(invoke);

            ResultadoOperacao<Void> resultado = editar.getResultado();
            if (resultado == null || !resultado.eValido()) {
                String mensagem = resultado == null
                        ? "Não foi possível editar usuário"
                        : FormatarErros.unificarErros(resultado.getErros());
                tela.mostrarMensagem(mensagem);
                return;
            }

            tela.mostrarMensagem("Usuário atualizado com sucesso");
            gerenciadorTelas.fechar(TipoTela.criarTelaEdicao(usuario.getId()).getTipo());
            tela.dispose();
        });

    }

    private void confirmar() {

    }

    @Override
    public void fechar(){
        tela.dispose();
    }
}
