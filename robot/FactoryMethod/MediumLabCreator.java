package com.example.robot.FactoryMethod;

/**
 * Classe che estende LabCreator, e che rappresenta uno dei ConcreteCreators
 * nel FactoryMethod*/
public class MediumLabCreator extends LabCreator {

    /**
     * Metodo che crea un labirinto di difficolta' medium, e ne genera
     * la sua rappresentazione a livello di archi.
     * @return Labyrinth un oggetto della classe Labyrinth*/
    @Override
    public Labyrinth createLab() {
        MediumLabyrinth lab =  new MediumLabyrinth();
        lab.generateLab();
        lab.createGraph();
        return lab;
    }
}
