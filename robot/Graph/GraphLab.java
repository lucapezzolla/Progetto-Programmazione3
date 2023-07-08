package com.example.robot.Graph;

import com.example.robot.LabInteractors.Cell;
import com.example.robot.LabInteractors.CellVal;

/**
 * Classe che eredita da Graph e che gestisce un grafo originatosi
 * da una matrice di Celle, ovvero, il grafo del labirinto*/
public class GraphLab extends Graph{

    /**
     * La matrice di celle che costituisce il labirinto*/
    private Cell[][] lab;

    /**
     * Size della matrice - dimXdim*/
    private int dim;

    /**
     * Costruttore che prende in input:
     * @param lab la matrice del labirinto
     * @param dim il suo size - dimXdim*/
    public GraphLab(Cell[][] lab, int dim){
        super();
        this.lab = lab;
        this.dim = dim;
    }


    @Override
    public Cell getNode(Integer index) {

        for (int i = 0; i < this.dim; i++)
            for (int j = 0; j < this.dim; j++) {
                if (this.lab[i][j].getId() == index)
                    return lab[i][j];
            }

        return null;
    }


    public void graphGen(){
        //settaggio degli id dei nodi - solo i nodi non "wall" hanno un id
        int id = 0;
        for (int i = 0; i < this.dim; i++)
            for (int j = 0; j < this.dim; j++)
                if (this.lab[i][j].getValue() != CellVal.wall)
                    this.lab[i][j].setId(id++);



        //Si aggiungono gli archi in base ai vicini - le celle muro non hanno
        //archi entranti / archi uscenti
        for (int i = 0; i < this.dim; i++) {
            for (int j = 0; j < this.dim; j++) {
                if (this.lab[i][j].getValue() == CellVal.wall)
                    continue;

                //se esiste la cella superiore-sx e se non e' un muro -> aggiungi l'arco corrispondente
                 if (i - 1 >= 0 && j - 1 >= 0)
                    if (this.lab[i-1][j-1].getValue() != CellVal.wall)
                        this.addEdge(this.lab[i][j].getId(), this.lab[i-1][j-1].getId());

                //se esiste la cella laterale-sx e se non e' un muro -> aggiungi l'arco corrispondente
                 if (j - 1 >= 0)
                    if (this.lab[i][j-1].getValue() != CellVal.wall)
                        this.addEdge(this.lab[i][j].getId(), this.lab[i][j-1].getId());

                //se esiste la cella inferiore-sx e se non e' un muro -> aggiungi l'arco corrispondente
                 if (j - 1 >= 0 && j - 1 <= this.dim - 1 && i + 1 <= this.dim - 1)
                    if (this.lab[i+1][j-1].getValue() != CellVal.wall)
                        this.addEdge(this.lab[i][j].getId(), this.lab[i+1][j-1].getId());

                //se esiste la cella inferiore e se non e' un muro -> aggiungi l'arco corrispondente
                 if (i + 1 <= this.dim - 1 && j <= this.dim - 1)
                    if (this.lab[i+1][j].getValue() != CellVal.wall)
                        this.addEdge(this.lab[i][j].getId(), this.lab[i+1][j].getId());

                //se esiste la cella inferiore-dx e se non e' un muro -> aggiungi l'arco corrispondente
                 if (i + 1 <= this.dim - 1 && j + 1 <= this.dim - 1)
                    if (this.lab[i+1][j+1].getValue() != CellVal.wall)
                        this.addEdge(this.lab[i][j].getId(),this.lab[i+1][j+1].getId());

                //se esiste la cella laterale-dx e se non e' un muro -> aggiungi l'arco corrispondente
                 if (i <= this.dim - 1 && j + 1 <= this.dim - 1)
                    if (lab[i][j+1].getValue() != CellVal.wall)
                        this.addEdge(this.lab[i][j].getId(), this.lab[i][j+1].getId());

                //se esiste la cella superiore-dx e se non e' un muro -> aggiungi l'arco corrispondente
                 if (i - 1 <= this.dim - 1 && j + 1 <= this.dim - 1 && i - 1 >= 0)
                    if (lab[i-1][j+1].getValue() != CellVal.wall)
                        this.addEdge(this.lab[i][j].getId(), this.lab[i-1][j+1].getId());

                //se esiste la cella superiore e se non e' un muro -> aggiungi l'arco corrispondente
                 if (i - 1 >= 0 && i - 1 <= this.dim - 1 && j <= this.dim - 1)
                    if (lab[i-1][j].getValue() != CellVal.wall)
                        this.addEdge(this.lab[i][j].getId(), this.lab[i-1][j].getId());

            }
        }
    }
}
