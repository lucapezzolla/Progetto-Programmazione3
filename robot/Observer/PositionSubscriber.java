package com.example.robot.Observer;

import com.example.robot.LabInteractors.Cell;
import com.example.robot.LabInteractors.Position;
import com.example.robot.State.State;
/**
 * Interfaccia che rappresenta il Subscriber nel pattern
 * Observer*/
public interface PositionSubscriber {
    /**
     * L'unico metodo di questa interfaccia, chiamato dal publisher su ogni oggetto
     * di questo tipo,che aggiorna lo stato attuale del gioco
     * @param pos la posizione attuale del robot
     * @param lab lo stato attuale del labirinto
     * @param dim le dimensioni del labirinto - dimXdim
     * @param state lo stato attuale del robot*/
    void update(Position pos, Cell[][] lab, int dim, State state);

}
