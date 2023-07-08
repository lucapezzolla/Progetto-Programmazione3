package com.example.robot.State;

import com.example.robot.LabInteractors.Cell;
import com.example.robot.Multimap.MultiMap;
import com.example.robot.Strategy.RandomMove;
import com.example.robot.Strategy.Strategy;

/**
 * Concrete State del pattern State, che consente al robot il
 * movimento in una cella adiacente alla sua, scelta casualmente
 * E' anche il context delle Strategies*/
public class EvadeState implements State {

    /**
     * La cella dalla quale computare la prossima mossa*/
    private Cell cell;

    /**
     * La strategia che utilizza il robot - pattern Strategy*/
    private Strategy strategy;

    /**
     * Costruttore, che prende in input
     * @param cell La cella dalla quale computare la prossima mossa
     * @param edges l'insieme degli archi del labirinto*/
    public EvadeState(Cell cell, MultiMap<Integer, Integer> edges){
        this.strategy = new RandomMove(edges);
        this.cell = cell;
    }


    @Override
    public Integer doAction(Cell cell) {
        return strategy.calculateNextMove(cell); //mossa calcolata in base alla strategia
    }
}
