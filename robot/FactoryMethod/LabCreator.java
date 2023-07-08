package com.example.robot.FactoryMethod;

/**
 * Classe astratta che corrisponde al Factory nel FactoryMethod, e
 * dalla quale i Concrete Creators ereditano e ridefiniscono l'unico
 * metodo, CreateLab, che permette la creazione del labirinto.*/
public abstract class LabCreator {

    /**
     * Metodo astratto, ridefinito nei Concrete Creators (EasyLabCreator/
     * MediumLabCreator/HardLabCreator) e che permette la creazione del
     * labirinto*/
    public abstract Labyrinth createLab();
}
