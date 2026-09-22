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

        UsuarioService usuarioService = new UsuarioService(usuariosDAO);
        EnviarNotificacaoService enviarNotificacaoService  = new EnviarNotificacaoService(usuariosDAO,notificacaoDAO);

//        for (int i = 22; i < 24; i++){
//            usuariosDAO.excluir(i);
//        }

        ProvedorService provedor = new ProvedorService(usuarioService, enviarNotificacaoService);

        GerenciadorTelasService gerenciadorTelas = new GerenciadorTelasService(provedor);

        gerenciadorTelas.abrirLogin(TipoTela.TELA_LOGIN);





        //System.out.println(usuariosDAO.listarTodos());

        //System.out.println(notificacaoDAO.listarNotificacoes());


    }

}
