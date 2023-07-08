package com.example.robot.FactoryMethod;

import com.example.robot.Graph.Graph;
import com.example.robot.Graph.GraphLab;
import com.example.robot.LabInteractors.Cell;
import com.example.robot.LabInteractors.CellVal;
import com.example.robot.LabInteractors.Position;
import com.example.robot.Multimap.MultiMap;

/**
 * Classe astratta che rappresenta il Concrete Product nel Factory Method,
 * e dalla quale tutti i labirinti effettivi ne ereditano le caratteristiche
 * di base, lasciando a loro la gestione della creazione degli scenari.*/
public abstract class Labyrinth implements ILabyrinth{

    /**
     * La matrice che rappresenta il labirinto*/
    private Cell[][] lab;
    /**
     * la posizione d'arrivo*/
    private Position endPos;
    /**
     * la dimensione della matrice - dimXdim*/
    private int dim;
    /**
     * grafo del labirinto, che contiene solo le celle non "wall"*/
    private Graph<Cell> noWallCells;

    /**
     * Costruttore della classe, prende in input
     * @param dim numero di righe/colonne del labirinto*/
    public Labyrinth(int dim){

        lab = new Cell[dim][dim];
        for(int i = 0; i < dim; i++) {
            for(int j = 0; j < dim; j++) {
                lab[i][j] = new Cell(CellVal.empty,new Position(i,j));
            }
        }
        this.endPos = lab[dim-1][dim-1].getPos();
        this.dim = dim;
        this.noWallCells = new GraphLab(this.lab,this.dim);
    }

    /**
     * Metodo dell'interfaccia ILabyrinth, astratto, per permettere alle
     * sottoclassi di essere un prodotto (ILabyrinth) e ridefinire il metodo
     * per avere una diversa generazione dello scenario*/
    public abstract void generateLab();
    /**
     * Metodo getter che ritorna
     * @return Cell[][] matrice delle celle del labirinto*/
    public Cell[][] getLab() {
        return lab;
    }

    /**
     *Metodo che consente di ottenere una cella, dando come parametri di input:
     * @param x la coordinata x
     * @param y la coordinata y
     * @return Cell la cella x;y*/
    public Cell getCell(int x, int y) {
        return lab[x][y];
    }

    /**
     * Metodo che restituisce la rappresentazione del labirinto sotto forma di grafo,
     * e in particolare, visto come insieme di archi. Non sono considerati come nodi
     * le celle che sono delle "wall", e non vi sono archi tra celle normali/wall e
     * viceversa.
     * @return MultiMap<Integer, Integer> il grafo */
    public MultiMap<Integer, Integer> getEdges() {
        return noWallCells.getEdges();
    }

    /**
     * Metodo che restituisce la posizione di arrivo
     * @return Position posizione d'arrivo*/
    public Position getEndPos() {
        return endPos;
    }

    /**
     * Metodo che restituisce il numero di colonne/righe del labirinto
     * @return dim numero colonne/righe*/
    public int getDim() {
        return dim;
    }

    /**
     * Metodo che restituisce una cella, non "wall", tramite il suo id
     * @param id identificativo della cella
     * @return Cell la cella*/
    public Cell getCellById(int id){
        return noWallCells.getNode(id);
    }
    /**
     * Metodo che, sfruttando l'attuale rappresentazione del labirinto, fa
     * generare all'oggetto interno della classe Graph<Cell> la rappresentazione
     * del labirinto come insieme di archi.*/
    public void createGraph() {
        this.noWallCells.graphGen();
    }

}
