package org.java.powerchess.powerchess.controlador;

import javafx.util.Pair;
import org.java.powerchess.powerchess.Juego;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class ControladorJuego {
    private Juego juego;
    private List<Pair<Observable, Observer>> observables;

    public ControladorJuego(Juego juego){
        this.juego = juego;
        observables = new ArrayList<Pair<Observable, Observer>>();
    }

    public void agregarObservable(Observable modelo, Observer vista){
        Pair<Observable, Observer> observable = new Pair<Observable, Observer>(modelo, vista);
        observables.add(observable);
    }

    public boolean avanzarTurno(){
        for(Pair<Observable, Observer> obs : observables){
            obs.getKey().notifyObservers(obs.getValue());
        }
        // aca podria chequear si la partida esta terminada
        //return (juego.())? false: true;
        return false;
    }
}
