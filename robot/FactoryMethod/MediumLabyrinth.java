package com.example.robot.FactoryMethod;

import com.example.robot.LabInteractors.CellVal;

/**
 * Classe per il labirinto di difficolta' Medium - ConcreteProduct nel
 * FactoryMethod, che eredita da Labirinto tutte le caratteristiche,
 * ma ridefinisce generateLab() per la creazione di livelli diversi
 * in base alla difficolta'*/
public class MediumLabyrinth extends Labyrinth {

    MediumLabyrinth() {
        super(8);
    }

    /**
     * Genera un labirinto, cambiando il valore delle celle da "empty"
     * a "wall". Generazione custom, figlia di un level design.*/
    public void generateLab() {
        getCell(1, 2).setValue(CellVal.wall);
        getCell(1, 3).setValue(CellVal.wall);
        getCell(2, 2).setValue(CellVal.wall);
        getCell(2, 3).setValue(CellVal.wall);

        getCell(1, 6).setValue(CellVal.wall);
        getCell(1, 7).setValue(CellVal.wall);

        getCell(3, 7).setValue(CellVal.wall);

        getCell(5, 1).setValue(CellVal.wall);

        getCell(4, 4).setValue(CellVal.wall);
        getCell(4, 5).setValue(CellVal.wall);
        getCell(5, 4).setValue(CellVal.wall);
        getCell(5, 5).setValue(CellVal.wall);

        getCell(7, 3).setValue(CellVal.wall);

    }






}


