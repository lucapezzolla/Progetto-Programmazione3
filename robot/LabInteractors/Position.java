package com.example.robot.LabInteractors;

/**
 * Classe che indica la posizione di un oggetto nella matrice,
 * sotto forma di coordinate x e y*/
public class Position {

    /**
     * Coordinata x*/
    private int x;

    /**
     * Coordinata y*/
    private int y;

    /**
     * Costruttore che prende in input
     * @param x coordinata x
     * @param y coordinata y*/
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }


    public int getY() {
        return y;
    }


    public void setY(int y) {
        this.y = y;
    }
}
