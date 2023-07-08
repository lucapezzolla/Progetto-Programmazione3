package com.example.robot.FactoryMethod;

/**
 * Interfaccia che rappresenta il Product nel FactoryMethod, e viene implementata
 * da tutti i Concrete Products (nel nostro caso, la classe astratta Labyrinth, dalla
 * quale vengono ereditate le caratteristiche base di ogni labirinto).*/
public interface ILabyrinth {
    /**
     * Metodo che consente di generare un labirinto.*/
    public void generateLab();

    /**
     * Metodo che costruisce un grafo tenendo conto della struttura di un labirinto.*/
    public void createGraph();

}
