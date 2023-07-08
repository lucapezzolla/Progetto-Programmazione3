package com.example.robot.Graph;

import com.example.robot.Multimap.MultiMap;

/**
 * Classe Grafo generica astratta, contiene i metodi base di gestione
 * di un grafo e generalizza il tipo di nodo contenuto al suo interno.
 * Il grafo e' un grafo orientato e non pesato*/
public abstract class Graph<T>{

    /**
     * L'insieme degli archi del grafo*/
    private MultiMap<Integer, Integer> edges;


    Graph(){
        edges = new MultiMap<>();
    }

    /**
     * Metodo che aggiunge un arco
     * @param source nodo sorgente
     * @param dest nodo destinazione*/
    public void addEdge(Integer source, Integer dest){
        this.edges.put(source,dest);
    }

    public MultiMap<Integer, Integer> getEdges(){
        return this.edges;
    }

    /**
     * Metodo astratto, da ridefinire nelle sottoclassi, che restituisce
     * il Nodo.
     * @return T il nodo di tipo T*/
    public abstract T getNode(Integer index);

    /**
     * Metodo astratto, da ridefinire nelle sottoclassi, che genera un
     * grafo visto come insieme di archi.*/
    public abstract void graphGen();
}
