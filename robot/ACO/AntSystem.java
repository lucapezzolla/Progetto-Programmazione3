package com.example.robot.ACO;

import com.example.robot.Multimap.MultiMap;

import java.util.*;
/**
 * Questa classe gestisce l'ACO, implementato e modificato specificatamente
 * per il problema di cammino minimo, da un nodo sorgente a un nodo destinazione,
 * in un grafo orientato e non pesato.
*/

public class AntSystem
{

    /**
     * Quantita' iniziale di feromone
     */
    public static final int PHERO_QNT = 100;

    /**
     * Parametro che definisce l'importanza dei feromoni nella scelta del prossimo nodo.
     */
    public static final double ALPHA = 0.5;

    /**
     * Parametro che definisce l'importanza dei dati rimanenti nella scelta del prossimo nodo.
     */
    public static final double BETA = 0.5;

    /**
     * Percentuale di evaporazione dei feromoni.
     */
    public static final double EVAPORATE_PER_SECOND = 0.5;

    /**
     * Costante che indica l'assenza di vicini.
     */
    public static final int NO_NEIGHBOUR = -1;

    /**
     * Costante che indica l'assenza di feromoni.
     */
    public static final int NO_PHERO = -1;

    /**
     * Numero dei nodi del grafo
     */
    int nodes;

    /**
     * Multimap che rappresenta la topologia del grafo
     */
    private MultiMap<Integer, Integer> edges;

    /**
     * Numero di formiche.
     */
    private int ants = 0;

    /**
     * Numero totale di iterazioni.
     */
    private int iterations = 0;

    /**
     * Inizializza la classe, con i parametri:
     * @param edges rappresentazione degli archi
     * @param ants numero di formiche
     * @param iterations numero di iterazioni
     */
    public AntSystem(MultiMap<Integer, Integer> edges, int ants, int iterations)
    {
        this.ants = ants;
        this.iterations = iterations;
        this.edges = edges;

        try
        {
            start();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }


    /**
     * Inizializza il numero di nodi
     */
    private void start()
    {
        nodes = this.edges.keySet().size();
    }

    /**
     * Rappresenta in forma matriciale la topologia del labirinto, con la rispettiva quantita' di feromone su ogni arco
     * @return double[][] Una matrice contenente, su ogni arco, l'ammontare iniziale di feromone
     */
    private double[][] createTopology()
    {
        double[][] edge2phero = new double[nodes][nodes];
        for(int i = 0; i < nodes; ++i)
            for(int j = 0; j < nodes; ++j)
                edge2phero[i][j] = NO_PHERO;

        for(Integer node1 : edges.keySet()) {
            Collection<Integer> collection = edges.get(node1);
            for (Integer node2 : collection)
                edge2phero[node1][node2] = PHERO_QNT;
        }

        return edge2phero;
    }

    /**
     * Restituisce il cammino trovato tra una sequenza di nodi, compresi tra:
     * @param src nodo sorgente
     * @param dest nodo destinazione
     * @return ArrayList<Integer> Sequenza di nodi, restituiti come Integer, del cammino tra source e dest
     */
    public ArrayList<Integer> pathCalculator(int src, int dest)
    {
        Map<ArrayList<Integer>, Integer> pathCount = new HashMap<>();
        double[][] edgePheroQnt = createTopology();

        int i = 0;
        while(i++ < iterations)
        {
            int ant = 0;
            while(ant++ < ants)
            {
                ArrayList<Integer> tour = antColonyVisit(src, dest, edgePheroQnt);
                if(tour.size() > 1)
                    if(pathCount.containsKey(tour))
                    {
                        Integer currentValue = pathCount.get(tour);
                        pathCount.replace(tour, currentValue, currentValue + 1);
                    }
                    else
                        pathCount.put(tour, 1);
            }
            updatePheroQnt(pathCount, edgePheroQnt);
        }

        return convergedPath(pathCount);
    }

    public int getNodes() {
        return nodes;
    }

    /**
     * Fa partire le formiche dal nodo sorgente, cercando di trovare un cammino fino alla destinazione
     * @param src nodo sorgente
     * @param dest nodo destinazioni
     * @param edgePheroQnt Una matrice contenente, su ogni arco, l'ammontare di feromone
     * @return ArrayList<Integer> il cammino della formica iesima.
     */
    private ArrayList<Integer> antColonyVisit(int src, int dest, double[][] edgePheroQnt)
    {
        ArrayList<Integer> trace = new ArrayList<>();
        int node = src;
        trace.add(node);

        while(node != dest)
        {
            int neighbour = pickUpNeighbour(node, edgePheroQnt);
            if(neighbour == NO_NEIGHBOUR)
                break;
            if(!trace.contains(neighbour))
                trace.add(neighbour);
            else
                break;

            node = neighbour;
        }

        if(trace.size() <= 1)
            return new ArrayList<Integer>();

        return trace.get(0) == src && trace.get(trace.size()-1) == dest ? trace : new ArrayList<Integer>();
    }

    /**
     * Funzione che sceglie il prossimo nodo adiacente da visitare.
     * @param node nodo corrente
     * @param edgePheroQnt Una matrice contenente, su ogni arco, l'ammontare di feromone
     * @return Integer Identificativo del vicino.
     */
    private Integer pickUpNeighbour(int node, double[][] edgePheroQnt)
    {
        ArrayList<Integer> neighs = availableNeighbours(node, edgePheroQnt);
        if(neighs.size() == 0)
            return NO_NEIGHBOUR;

        double probs[] = new double[neighs.size()];
        int index = 0;


        for(int neigh : neighs)
            probs[index++] = probCalculator(node, neigh, edgePheroQnt);

        double value = Math.random();


        double sum = 0;
        for(index = 0; index < neighs.size(); ++index)
        {
            sum += probs[index];
            if(value <= sum)
                break;
        }

        return neighs.size() > 0 ? neighs.get(index) : NO_NEIGHBOUR;
    }

    /**
     * Controlla i vicini disponibili.
     * @param node nodo corrente
     * @param edgePheroQnt Una matrice contenente, su ogni arco, l'ammontare di feromone
     * @return ArrayList<Integer> Un insieme di vicini disponibili
     */
    private ArrayList<Integer> availableNeighbours(int node, double[][] edgePheroQnt)
    {
        ArrayList<Integer> neighbours = new ArrayList<>();

        for(int i = 0; i < edgePheroQnt[node].length; ++i)
            if(edgePheroQnt[node][i] >= 0 && i != node)
                neighbours.add(i);

        return neighbours;
    }

    /**
     * Calcola la probabilita' di scelta di un particolare nodo
     * @param start nodo di partenza
     * @param end nodo di destinazione
     * @param edgePheroQnt Una matrice contenente, su ogni arco, l'ammontare di feromone
     * @return double Probabilita'.
     */
    private double probCalculator(int start, int end, double[][] edgePheroQnt) throws IllegalArgumentException
    {
        double num = Math.pow(edgePheroQnt[start][end], ALPHA) * Math.pow(1, BETA);
        double denum = 0;
        ArrayList<Integer> neighs = availableNeighbours(start, edgePheroQnt);
        if(neighs.size() == 0)
            throw new IllegalArgumentException("No neighbours");

        for(int neigh : neighs)
            denum += Math.pow(edgePheroQnt[start][neigh], ALPHA) * Math.pow(1, BETA);

        return num / denum;
    }


    /**
     * Calcola la lunghezza di un cammino
     * @param path la sequenza di nodi
     * @return lunghezza del cammino
     */
    private int tourLength(ArrayList<Integer> path)
    {
        if(path.size() <= 1)
            return 0;

        return path.size();
    }

    /**
     * Funzione che aggiorna i livelli di feromone sugli archi
     * @param paths I cammini sui quali aggiornare il livello di feromone
     * @param edgePheroQnt Una matrice contenente, su ogni arco, l'ammontare di feromone
     */
    private void updatePheroQnt(Map<ArrayList<Integer>, Integer> paths, double[][] edgePheroQnt)
    {

        for(int i = 0; i < nodes; ++i)
            for(int j = 0; j < nodes; ++j)
                if(edgePheroQnt[i][j] != NO_PHERO)
                    edgePheroQnt[i][j] *= (1 - EVAPORATE_PER_SECOND);


        Set<ArrayList<Integer>> onlyTrails = paths.keySet();
        for(ArrayList<Integer> path : onlyTrails)
        {
            Iterator<Integer> it = path.iterator();
            int str = it.next();
            while(it.hasNext())
            {
                int end = it.next();
                edgePheroQnt[str][end] += PHERO_QNT / tourLength(path);
                str = end;
            }
        }
    }

    /**
     * Funzione che restituisce il cammino convergente, ovvero quello con la piu' alta occorrenza
     * @param pathCount Una map che associa un cammino con la sua occorrenza
     * @return Vector<Integer> il cammino
     */
    ArrayList<Integer> convergedPath(Map<ArrayList<Integer>, Integer> pathCount)
    {

        ArrayList<Integer> returnpath = new ArrayList<>();
        Integer cnt = Integer.MIN_VALUE;

        Set<ArrayList<Integer>> keys = pathCount.keySet();
        for(ArrayList<Integer> path : keys)
            if(pathCount.get(path) > cnt)
            {
                cnt = pathCount.get(path);
                returnpath = path;
            }

        return returnpath;
    }
}

