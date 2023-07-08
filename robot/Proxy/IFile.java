package com.example.robot.Proxy;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Interfaccia Service del pattern Proxy, che implementa
 * le operazioni di base che possono essere effettuate
 * su un file*/
public interface IFile {

    /**
     * Metodo che permette la scrittura su un file
     * @return Boolean esito dell'operazione*/
    public Boolean write() throws IOException;

    /**
     * Metodo che permette la lettura da un file
     * @return ArrayList<String[]> un array di stringhe
     * per ogni riga: alla prima posizione il nome e cognome;
     * alla seconda, il punteggio*/
    public ArrayList<String[]> read() throws IOException;






}
