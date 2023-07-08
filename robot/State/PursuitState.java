package com.example.robot.State;

import com.example.robot.LabInteractors.Cell;
import com.example.robot.Multimap.MultiMap;
import com.example.robot.Strategy.AntColonyOneCell;
import com.example.robot.Strategy.Strategy;

/**
 * Concrete State del pattern State, che consente al robot il
 * movimento in una cella adiacente, tramite l'ACO
 * E' anche il context delle Strategies*/
public class PursuitState implements State {

    /**
     * La cella dalla quale computare la prossima mossa*/
    private Cell cell;

    /**
     * La strategia che utilizza il robot - pattern Strategy*/
    private Strategy strategy;

    public PursuitState(MultiMap<Integer, Integer> edges, Cell cell){
        this.strategy = new AntColonyOneCell(edges);
        this.cell = cell;
    }


    @Override
    public Integer doAction(Cell cell) {
        return strategy.calculateNextMove(cell); //mossa calcolata in base alla strategia
    }
}
