package com.example.robot.LabInteractors;

/**
 * Classe che rappresenta la cella iesima del labirinto*/
public class Cell {

    /**
     * Tipologia di cella, sottoforma di enum*/
    private CellVal value;

    /**
     * Position della cella nella matrice del labirinto*/
    private Position pos;

    /**
     * id della cella, quando questa viene vista come nodo di un grafo*/
    private Integer id;


    /**
     * Costruttore della classe, in input
     * @param value tipologia di cella
     * @param pos posizione della cella nella matrice
     * @param id id della cella*/
    public Cell(CellVal value, Position pos, int id)  {
        this.value = value;
        this.pos = pos;
        this.id = id;

    }
    /**
     * Costruttore della classe, in input
     * @param value tipologia di cella
     * @param pos posizione della cella nella matrice*/

    public Cell(CellVal value, Position pos)  {
        this.value = value;
        this.pos = pos;

    }
    /**
     * Costruttore per cella di default*/
    public Cell(){
        this.value = CellVal.empty;

    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CellVal getValue() {
        return value;
    }

    public Position getPos() {
        return pos;
    }

    public void setValue(CellVal value) {
        this.value = value;
    }



}
