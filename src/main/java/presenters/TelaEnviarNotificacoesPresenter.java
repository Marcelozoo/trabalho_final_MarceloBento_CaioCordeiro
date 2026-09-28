package presenters;

import command.Command;
import command.EnviarNotificacaoCommand;
import command.Invoke;
import command.ListarCommand;
import eventosTela.EventosTela;
import models.ResultadoOperacao;
import models.Usuario;
import navegacao.TipoTela;
import observer.Observer;
import services.EnviarNotificacaoService;
import services.GerenciadorEventosSingleton;
import services.GerenciadorTelasService;
import services.UsuarioService;
import state.EnviandoNotificacoesState;
import state.EstadoTela;
import utilidades.FormatarErros;
import views.TelaEnviarNotificacoesView;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class TelaEnviarNotificacoesPresenter implements Observer, TelaPresenter {


    private final TelaEnviarNotificacoesView tela;
    private EstadoTela estadoTela;
    private final Invoke invokeCommands;
    private final UsuarioService usuarioService;
    private final EnviarNotificacaoService enviarNotificacaoService;
    private Usuario usuarioSessao;
    private  final GerenciadorTelasService gerenciadorTelas;
    private List<Usuario> listaUsuario;


    public TelaEnviarNotificacoesPresenter(Usuario usuarioSessao, UsuarioService usuarioService, EnviarNotificacaoService enviarNotificacaoService, GerenciadorTelasService gerenciadorTelas) {
        this.tela = new TelaEnviarNotificacoesView();
        this.usuarioService = usuarioService;
        this.estadoTela = new EstadoTela();
        this.estadoTela.setEstado(new EnviandoNotificacoesState(estadoTela));
        this.invokeCommands = new Invoke();
        this.enviarNotificacaoService = enviarNotificacaoService;
        this.usuarioSessao = usuarioSessao;
        this.gerenciadorTelas = gerenciadorTelas;
        this.listaUsuario = new ArrayList<>();

        GerenciadorEventosSingleton.getInstancia().registrar(this);

        config();
        configuraBtns();
        configFechamentoTela();

    }

    @Override
    public void update() {

        listarUsuarios();

    }

    private void configFechamentoTela(){
        tela.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                gerenciadorTelas.fechar(
                        TipoTela.TELA_ENVIAR_NOTIFICACOES.getTipo()
                );
            }
        });
    }




    private void config(){
        listarUsuarios();

    }

    private void listarUsuarios(){
        Command<List<Usuario>> listar = new ListarCommand(usuarioService);
        invokeCommands.setComando(listar);

        estadoTela.listar(invokeCommands);

        ResultadoOperacao<List<Usuario>> resultado =  listar.getResultado();

        limparTabela();
        for (Usuario usuario : resultado.getResultado()){
            tela.preencherTabelaComUsuarios(usuario.getNome(), usuario.getEmail());
        }

        listaUsuario = resultado.getResultado();

    }

    private void limparTabela(){
        tela.limparTabela();
    }
    private void configuraBtnEnviar(){
        tela.getBtnEnviar().addActionListener(e -> {
            
            List<Object> selecionados = tela.getUsuariosSelecionadosNaTabela();

           String msg = tela.getMensagemASerEnviada();

           for (Object selecionado : selecionados){
               Command<Void> enviar = new EnviarNotificacaoCommand(
                       selecionado.toString(),
                       usuarioSessao.getEmail(),
                       msg,
                       enviarNotificacaoService
               );
               invokeCommands.setComando(enviar);
               estadoTela.enviarNotificacoes(invokeCommands);

               if(!enviar.getResultado().eValido()){
                   mostrarMensagem(FormatarErros.unificarErros(enviar.getResultado().getErros()));
                   return;
               }
           }

            mostrarMensagem("Sucesso ao enviar as notificacoes");
        });
    }

    private void configuraBtns(){
        configuraBtnEnviar();
    }

    private void mostrarMensagem(String mensagem){
        tela.mostrarMensagem(mensagem);
    }

    @Override
    public void fechar(){
        tela.dispose();
    }

}
