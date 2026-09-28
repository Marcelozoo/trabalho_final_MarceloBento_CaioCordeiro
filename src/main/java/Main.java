import dao.NotificacaoDAO;
import dao.NotificacaoDAOSQLite;
import dao.UsuarioDAOSQLite;
import dao.UsuariosDAO;

import navegacao.TipoTela;
import services.*;



public class Main {

    public static void main(String[] args) {
        UsuariosDAO usuariosDAO = new UsuarioDAOSQLite();
        NotificacaoDAO notificacaoDAO = new NotificacaoDAOSQLite();

        UsuarioService usuarioService = new UsuarioService(usuariosDAO, notificacaoDAO);
        EnviarNotificacaoService enviarNotificacaoService  = new EnviarNotificacaoService(usuariosDAO,notificacaoDAO);
        NotificacaoService notificacaoService = new NotificacaoService(notificacaoDAO, usuariosDAO);



        ProvedorService provedor = new ProvedorService(
                usuarioService,
                enviarNotificacaoService,
                notificacaoService
        );

        GerenciadorTelasService gerenciadorTelas = new GerenciadorTelasService(provedor);

        gerenciadorTelas.abrirLogin(TipoTela.TELA_LOGIN);






    }

}
