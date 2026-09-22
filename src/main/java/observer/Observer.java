package observer;

import eventosTela.EventosTela;


public interface Observer {

    public void update(EventosTela tipo,Object arg);
}
