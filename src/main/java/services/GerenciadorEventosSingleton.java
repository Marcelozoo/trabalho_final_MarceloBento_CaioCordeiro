package services;

import eventosTela.EventosTela;
import observer.Observer;

import java.util.ArrayList;
import java.util.List;

public class GerenciadorEventosSingleton {

    private static List<Observer> observers;
    private static GerenciadorEventosSingleton instancia;

    private GerenciadorEventosSingleton() {

    }


    public static GerenciadorEventosSingleton getInstancia(){
        if(instancia == null){
            instancia = new GerenciadorEventosSingleton();
            observers = new ArrayList<>();
        }
        return instancia;
    }

    public void registrar(Observer observador){
        observers.add(observador);
    }

    public void remover(Observer observador){
        observers.remove(observador);
    }

    public void notificar(EventosTela tipo, Object dado){
        for(Observer o : observers){
            o.update(tipo, dado);
        }
    }
}
