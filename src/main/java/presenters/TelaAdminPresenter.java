package presenters;

import command.BuscarCommand;
import command.Command;
import command.Invoke;
import command.ListarCommand;
import eventosTela.EventosTela;
import models.ResultadoOperacao;
import models.Usuario;
import navegacao.TipoTela;
import observer.Observer;
import services.*;
import state.EstadoTela;
import state.LogadoState;
import utilidades.FormatarErros;
import views.TelaAdminView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class TelaAdminPresenter implements Observer, TelaPresenter {

    private final TelaAdminView tela;
    private EstadoTela estadoTela;
    private final Invoke invokeCommands;
    private final Usuario usuario;
    private final GerenciadorTelasService gerenciadorTelas;
    private final UsuarioService usuarioService;



    public TelaAdminPresenter(Usuario usuario, GerenciadorTelasService gerenciadorTelas, UsuarioService usuarioService) {

        this.usuario = usuario;
        this.gerenciadorTelas = gerenciadorTelas;
        this.usuarioService = usuarioService;


        this.tela = new TelaAdminView();
        this.estadoTela = new EstadoTela();
        this.invokeCommands = new Invoke();
        this.estadoTela.setEstado(new LogadoState(estadoTela));

        GerenciadorEventosSingleton.getInstancia().registrar(this);

        configBtns();
        configLabels();
        configFechamentoTela();
    }

    private void configFechamentoTela(){
        tela.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                gerenciadorTelas.fechar(
                        TipoTela.TELA_ADMIN.getTipo()
                );
            }
        });
    }

    private void configLabels(){
        tela.getNomeUsuarioLabel().setText(usuario.getNome());
        tela.getTipoUsuarioLabel().setText(usuario.getIsAdmin() ? "Admin" : "Comum");

    }



    @Override
    public void update(EventosTela tipo, Object arg) {
        if(tipo == EventosTela.CADASTRO_REALIZADO_COM_SUCESSO || tipo == EventosTela.USUARIO_EXCLUIDO_COM_SUCESSO){
            Command<List<Usuario>> listar = new ListarCommand(usuarioService);
            invokeCommands.setComando(listar);
            estadoTela.atualizar(invokeCommands);

            ResultadoOperacao<List<Usuario>> resultado = listar.getResultado();
            if (resultado != null) {
                atualizaTabelaLista(resultado.getResultado());
            }
        }

    }

    private void configBtns(){


        tela.getBtnSair().addActionListener(e -> {tela.dispose();});
        tela.getBtnBuscar().addActionListener(e -> {
            String nome = tela.getNomeUsuarioText().getText();

            BuscarCommand buscar = new BuscarCommand(nome, usuarioService);
            invokeCommands.setComando(buscar);
            estadoTela.buscar(invokeCommands);

            ResultadoOperacao<Usuario> resultado = buscar.getResultado();

            if (!resultado.eValido()){
                tela.mostrarMensagem(FormatarErros.unificarErros(resultado.getErros()));
            }else{
                atualizaTabela(resultado.getResultado());
            }

        });
        tela.getBtnNovo().addActionListener(e -> {
            gerenciadorTelas.abrirCadastro(TipoTela.TELA_CADASTRO);
            //TelaCadastroPresenter ob = new TelaCadastroPresenter(this.cadastro, gerenciadorTelas);

            //GerenciadorEventosSingleton.getInstancia().registrar(ob);


        });
        tela.getBtnListarTodos().addActionListener(e -> {
            Command<List<Usuario>> listar = new ListarCommand(usuarioService);
            invokeCommands.setComando(listar);
            estadoTela.listar(invokeCommands);

            ResultadoOperacao<List<Usuario>> resultado = listar.getResultado();
            if (resultado != null) {
                atualizaTabelaLista(resultado.getResultado());
            }
        });

        tela.getBtnVisualizar().addActionListener(e -> {
            int linha = tela.getTabelaUsuarios().getSelectedRow();
            try {
                String nome = (String) tela.getTabelaUsuarios().getValueAt(linha, 0);
                gerenciadorTelas.abrirVisualizacao(TipoTela.criarTelaVisualizacaoUsuario(nome), nome);

            }catch (ArrayIndexOutOfBoundsException excecao){
                mostrarMensagem("Selecione um usuario");
            }

        });

        tela.getBtnNotificacoes().addActionListener(e -> {
            gerenciadorTelas.abrirNotificacoes(TipoTela.TELA_NOTIFICACOES);
        });

        tela.getBtnEnviarNotificacoes().addActionListener(e -> {
            gerenciadorTelas.abrirEnviarNotificacoes(TipoTela.TELA_ENVIAR_NOTIFICACOES,usuario);
        });
    }
    private void atualizaTabelaLista(List<Usuario> usuarios){
        DefaultTableModel modelo =
                (DefaultTableModel) tela.getTabelaUsuarios().getModel();

        modelo.setRowCount(0);

        for (Usuario usuario : usuarios){
            modelo.addRow(new Object[]{
                    usuario.getNome(),
                    usuario.getCriadoEm(),
                    usuario.getQtdNotificacoesLidas(),
                    usuario.getQtdNotificacoesEnviadas()
            });
        }


    }


    private void mostrarMensagem(String mensagem){
        tela.mostrarMensagem(mensagem);
    }
    private void atualizaTabela(Usuario usuario){
        DefaultTableModel modelo =
                (DefaultTableModel) tela.getTabelaUsuarios().getModel();

        modelo.setRowCount(0);

 

            modelo.addRow(new Object[]{
                    usuario.getNome(),
                    usuario.getCriadoEm(),
                    usuario.getQtdNotificacoesLidas(),
                    usuario.getQtdNotificacoesEnviadas()
            });
        


    }

    @Override
    public TelaPresenter getTela() {
        return null;
    }

    @Override
    public JInternalFrame getTelaView() {
        return null;
    }



}
