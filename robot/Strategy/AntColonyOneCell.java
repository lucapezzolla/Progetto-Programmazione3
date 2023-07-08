package com.example.robot.Strategy;

import com.example.robot.ACO.AntSystem;
import com.example.robot.LabInteractors.Cell;
import com.example.robot.Multimap.MultiMap;

import java.util.ArrayList;

/**
 * Concrete Strategy del pattern Strategy, implementa l'ACO per
 * consentire al robot di muoversi in una cella adiacente a quella
 * attuale*/
public class AntColonyOneCell implements Strategy{

    /**
     * Un'istanza della classe ACO*/
    private AntSystem aco;

    /**
     * Costruttore, che prende in input
     * @param edges l'insieme degli archi del labirinto*/
    public AntColonyOneCell(MultiMap<Integer, Integer> edges) {
        int ants,iterations;
        if (edges.size() == 256){ //se il labirinto e' il medium
            ants = 100;
            iterations = 100;
        }
        else if (edges.size() > 256){ //se e' hard
            ants = 200;
            iterations = 300;
        }
        else{
            ants = 50;
            iterations = 100;
        }

        this.aco = new AntSystem(edges, ants, iterations);
    }

    @Override
    public Integer calculateNextMove(Cell cell) {
        ArrayList<Integer>path;
        int solution;
        do{
            path = aco.pathCalculator(cell.getId(),aco.getNodes()-1);
        }
        while(path.size() == 0); //cercami un cammino fintanto che non ne trovi uno
        solution = path.get(1);
        return solution;
    }
}
