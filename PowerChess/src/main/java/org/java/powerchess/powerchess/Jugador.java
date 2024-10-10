package org.java.powerchess.powerchess;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Jugador extends Observable {
  private String nombre;
  private Color color;
  private List<Poder> poderes;
  private Poder poderSeleccionado;

  public Jugador(String nombre, Color color) {
    this.nombre = nombre;
    this.color = color;
    this.poderes = new ArrayList<>();
    this.agregarPoder(new Escudo());
    this.agregarPoder(new Limpieza());
    this.agregarPoder(new Freeze());
    this.poderSeleccionado = null;
  }

  public boolean moverPieza(int xOrigen, int yOrigen, int xDestino, int yDestino, Tablero tablero) {
    return tablero.moverPieza(xOrigen, yOrigen, xDestino, yDestino, this.color);
  }

  public boolean poderEstaSeleccionada(Poder poder) {
    return poder.equals(this.poderSeleccionado);
  }

  public void cambiarSeleccionPoder(Poder poder) {
    this.poderSeleccionado = (poder == this.poderSeleccionado) ? null : poder;
    setChanged();
    notifyObservers();
  }

  public Poder obtenerPoderSeleccionado() {
    return this.poderSeleccionado;
  }

  public boolean hayUnPoderSeleccionado() {
    return this.poderSeleccionado != null;
  } 

  public void activarPoder(Poder poder, Pieza pieza) {
    if (!poderes.contains(poder)) {
      throw new PoderInvalidoException();
    }
    poder.activar(pieza);
    setChanged();
    notifyObservers();
  }

  public String getNombre() {
    return nombre;
  }

  public Color getColor() {
    return color;
  }

  public List<Poder> getPoderes() {
    return poderes;
  }

  public void agregarPoder(Poder poder) {
    poderes.add(poder);
  }

  public void actualizarPoderesDeDuracion() {
    for (Poder poder : poderes) {
      if (poder.getNombre().equals(new Freeze().getNombre()) || poder.getNombre().equals(new Escudo().getNombre())) {
        PoderDeDuracion poderDeDuracion = (PoderDeDuracion) poder;
        if(poderDeDuracion.esUsado()){
          poderDeDuracion.disminuirTurnos();
          if (poderDeDuracion.getTurnos() == 0) {
            poderDeDuracion.desactivar();
          }
        }
      }
    }
  }
}
