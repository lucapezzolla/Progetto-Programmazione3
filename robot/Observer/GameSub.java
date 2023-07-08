package com.example.robot.Observer;

import com.example.robot.LabInteractors.Cell;
import com.example.robot.LabInteractors.Position;
import com.example.robot.State.FleeState;
import com.example.robot.State.PursuitState;
import com.example.robot.State.SeekState;
import com.example.robot.State.State;

/**
 * Classe che rappresenta il ConcreteSubscriber nel pattern
 * Observer. Si occupa di contenere gli aggiornamenti inviati
 * dal game, in merito a posizione e stato del robot, stato del
 * labirinto e dimensione dello stesso*/
public class GameSub implements PositionSubscriber{
    /**
     * La posizione attuale del robot*/
    private Position pos;

    /**
     * La posizione precedente del robot*/
    private Position prevPos;
    /**
     * Labirinto*/
    private Cell[][] lab;

    /**
     * Stato attuale del robot*/
    private State state;

    /**
     * Dimensioni del labirinto - dimXdim*/
    private int dim;

    public GameSub(){
        this.prevPos = new Position(0, 0);
        this.pos = null;
        this.lab = null;
    }

    public String getState() {
        if(state instanceof PursuitState)
            return "Robot state: Pursuit";
        else if(state instanceof SeekState)
            return "Robot state: Seek";
        else if(state instanceof FleeState)
            return "Robot state: Flee";
        else
            return "Robot state: Evade";
    }
    public int getDim() {
        return dim;
    }


    public Cell[][] getLab() {
        return lab;
    }


    public void update(Position pos, Cell[][] lab, int dim, State state){
        this.prevPos = this.pos;
        this.pos = pos;
        this.lab = lab;
        this.dim = dim;
        this.state = state;
    };

    /**
     * Metodo che calcola qual e' lo shifting tra la posizione attuale
     * e quella precedente
     * @return Position un oggetto che contiene la differenza tra le coordinate
     * attuali e quelle precedenti*/
    public Position getUpdate()
    {
        Position newPos = new Position(0,0);

        newPos.setX(pos.getX() - prevPos.getX());
        newPos.setY(pos.getY() - prevPos.getY());

        return newPos;
    }

}
