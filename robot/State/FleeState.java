package com.example.robot.State;

import com.example.robot.LabInteractors.Cell;
import com.example.robot.Multimap.MultiMap;
import com.example.robot.Strategy.AntColonyTwoCells;
import com.example.robot.Strategy.Strategy;

/**
 * Concrete State del pattern State, che consente al robot il
 * movimento fino a due celle alla volta, tramite l'ACO
 * E' anche il context delle Strategies*/
public class FleeState implements State{

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
    public FleeState(MultiMap<Integer, Integer> edges, Cell cell){
        this.strategy = new AntColonyTwoCells(edges);
        this.cell = cell;
    }


    @Override
    public Integer doAction(Cell cell) {
        return strategy.calculateNextMove(cell); //mossa calcolata in base alla strategia
    }
}
