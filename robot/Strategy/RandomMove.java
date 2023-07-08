package com.example.robot.Strategy;

import com.example.robot.LabInteractors.Cell;
import com.example.robot.Multimap.MultiMap;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Concrete Strategy del pattern Strategy, consente al robot
 * di muoversi casualmente in una cella adiacente a quella attuale*/
public class RandomMove implements Strategy{

    /**
     * L'insieme degli archi del labirinto*/
    private MultiMap<Integer, Integer> edges;

    /**
     * Costruttore, che prende in input
     * @param edges l'insieme degli archi del labirinto*/
    public RandomMove(MultiMap<Integer, Integer> edges) {
        this.edges = edges;
    }

    @Override
    public Integer calculateNextMove(Cell cell) {

        ArrayList<Integer> collection = (ArrayList<Integer>) edges.get(cell.getId());
        int randomNum = ThreadLocalRandom.current().nextInt(0, collection.size());
        return collection.get(randomNum);

    }
}
