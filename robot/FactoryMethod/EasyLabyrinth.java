package com.example.robot.FactoryMethod;

import com.example.robot.LabInteractors.CellVal;

/**
 * Classe per il labirinto di difficolta' Easy - ConcreteProduct nel
 * FactoryMethod, che eredita da Labirinto tutte le caratteristiche,
 * ma ridefinisce generateLab() per la creazione di livelli diversi
 * in base alla difficolta'*/
public class EasyLabyrinth extends Labyrinth{

    EasyLabyrinth(){
        super(4);
    }

    /**
     * Genera un labirinto, cambiando il valore delle celle da "empty"
     * a "wall". Generazione custom, figlia di un level design.*/
    public void generateLab(){
        getCell(1,2).setValue(CellVal.wall);
        getCell(2,2).setValue(CellVal.wall);
    }


}
