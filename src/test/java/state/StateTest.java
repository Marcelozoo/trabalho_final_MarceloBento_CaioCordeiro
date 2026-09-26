package state;

import static  org.junit.Assert.assertEquals;

import command.AutenticarLoginCommand;
import command.Invoke;
import excecoes.TransicaoEstadoInvalidaException;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.junit.Assert.assertThrows;

public class StateTest {





    @Test
    public void estadoLogandoStateDeveFazerTransicoesCorretas(){
        EstadoTela estado = new EstadoTela();
        Invoke invoke = mock(Invoke.class);

        estado.setEstado(new LogandoState(estado));
        estado.autenticarLogin(invoke);

        assertEquals("Autenticando usuário", estado.getEstado());
        verify(invoke).executar(); ;

        estado.logar();
        assertEquals("Logando", estado.getEstado());

        assertThrows(TransicaoEstadoInvalidaException.class, () -> estado.buscar(invoke));
        assertThrows(TransicaoEstadoInvalidaException.class, () -> estado.cadastrar(invoke));
        assertThrows(TransicaoEstadoInvalidaException.class, () -> estado.autenticar(invoke));
        assertThrows(TransicaoEstadoInvalidaException.class, () -> estado.atualizar(invoke));
        assertThrows(TransicaoEstadoInvalidaException.class, () -> estado.editar(invoke));
        assertThrows(TransicaoEstadoInvalidaException.class, () -> estado.enviarNotificacoes(invoke));
        assertThrows(TransicaoEstadoInvalidaException.class, () -> estado.excluir(invoke));
        assertThrows(TransicaoEstadoInvalidaException.class, () -> estado.listar(invoke));

    }

    @Test
    public void estadoAutenticandoStateDeveFazerTransicoesCorretas(){
        EstadoTela estado = new EstadoTela();
        Invoke invoke = mock(Invoke.class);

        estado.setEstado(new AutenticandoUsuarioState(estado));
        estado.autenticarLogin(invoke);

        assertEquals("Autenticando usuário", estado.getEstado());
        verify(invoke).executar(); ;

        estado.logar();
        assertEquals("Logando", estado.getEstado());

        assertThrows(TransicaoEstadoInvalidaException.class, () -> estado.buscar(invoke));
        assertThrows(TransicaoEstadoInvalidaException.class, () -> estado.cadastrar(invoke));
        assertThrows(TransicaoEstadoInvalidaException.class, () -> estado.autenticar(invoke));
        assertThrows(TransicaoEstadoInvalidaException.class, () -> estado.atualizar(invoke));
        assertThrows(TransicaoEstadoInvalidaException.class, () -> estado.editar(invoke));
        assertThrows(TransicaoEstadoInvalidaException.class, () -> estado.enviarNotificacoes(invoke));
        assertThrows(TransicaoEstadoInvalidaException.class, () -> estado.excluir(invoke));
        assertThrows(TransicaoEstadoInvalidaException.class, () -> estado.listar(invoke));

    }

    @Test
    public void estadoLogadoStateDeveFazerTransicoesCorretas(){
        EstadoTela estado = new EstadoTela();
        Invoke invoke = mock(Invoke.class);

        estado.setEstado(new LogadoState(estado));
        estado.buscar(invoke);

        assertEquals("Buscando usuários", estado.getEstado());
        verify(invoke).executar(); ;

        estado.listar(invoke);
        assertEquals("Buscando usuários", estado.getEstado());

        assertThrows(TransicaoEstadoInvalidaException.class, () -> estado.cadastrar(invoke));
        assertThrows(TransicaoEstadoInvalidaException.class, () -> estado.autenticar(invoke));
        assertThrows(TransicaoEstadoInvalidaException.class, () -> estado.atualizar(invoke));
        assertThrows(TransicaoEstadoInvalidaException.class, () -> estado.editar(invoke));
        assertThrows(TransicaoEstadoInvalidaException.class, () -> estado.enviarNotificacoes(invoke));
        assertThrows(TransicaoEstadoInvalidaException.class, () -> estado.excluir(invoke));
        assertThrows(TransicaoEstadoInvalidaException.class, () -> estado.autenticarLogin(invoke));

    }


    @Test

    public void estadoVisualizandoUsuarioDeveFazerTransicoesCorretas(){
        EstadoTela estado = new EstadoTela();
        Invoke invoke = mock(Invoke.class);

        estado.setEstado(new VisualizandoUsuarioState(estado));
        estado.excluir(invoke);

        assertEquals("Deletando usuário", estado.getEstado());
        verify(invoke).executar(); ;

        estado.autenticar(invoke);
        assertEquals("Atualizando usuário", estado.getEstado());

        estado.excluir(invoke);
        estado.autenticar(invoke);



        assertThrows(TransicaoEstadoInvalidaException.class, () -> estado.logar());
        assertThrows(TransicaoEstadoInvalidaException.class, () -> estado.buscar(invoke));
        assertThrows(TransicaoEstadoInvalidaException.class, () -> estado.listar(invoke));
        assertThrows(TransicaoEstadoInvalidaException.class, () -> estado.cadastrar(invoke));
        assertThrows(TransicaoEstadoInvalidaException.class, () -> estado.atualizar(invoke));
        assertThrows(TransicaoEstadoInvalidaException.class, () -> estado.editar(invoke));
        assertThrows(TransicaoEstadoInvalidaException.class, () -> estado.enviarNotificacoes(invoke));
        assertThrows(TransicaoEstadoInvalidaException.class, () -> estado.autenticarLogin(invoke));
    }
}
