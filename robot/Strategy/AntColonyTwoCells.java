package com.example.robot.Strategy;

import com.example.robot.ACO.AntSystem;
import com.example.robot.LabInteractors.Cell;
import com.example.robot.Multimap.MultiMap;

import java.util.ArrayList;

/**
 * Concrete Strategy del pattern Strategy, implementa l'ACO per
 * consentire al robot di muoversi fino a due celle alla volta rispetto
 * alla cella attuale*/
public class AntColonyTwoCells implements Strategy{

    /**
     * Un'istanza della classe ACO*/
    private AntSystem aco;

    /**
     * Costruttore, che prende in input
     * @param edges l'insieme degli archi del labirinto*/
    public AntColonyTwoCells(MultiMap<Integer, Integer> edges) {
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
            ants = 65;
            iterations = 100;
        }
        this.aco = new AntSystem(edges, ants, iterations);
    }

    @Override
    public Integer calculateNextMove(Cell cell) {
        ArrayList<Integer>path;
        int size = 2;
        do{
            path = aco.pathCalculator(cell.getId(),aco.getNodes()-1);
        }
        while(path.size() == 0); //cercami un cammino fintanto che non ne trovi uno

        if (size < path.size()) //se puo' muoversi di due celle
            return path.get(size);

        else
            return path.get(size-1);


    }
}
