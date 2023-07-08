package com.example.robot.Command;

import javafx.event.Event;

import java.io.IOException;

/**
 * Classe astratta che implementa il pattern Command.
 * Quest'ultima, rappresenta il Command nella struttura del pattern,
 * che viene poi esteso dai concrete commands (LoadEasyLab/LoadMediumLab/
 * LoadHardLab*/


public abstract class LabCommand {

    public String nome; //nome del player

    public String cognome; //cognome del player
    public String filename; //file fxml
    public int diff; //difficolta' - da passare all'istanza di Game

    /**
     * Costruttore, che prende in input
     * @param nome nome del player
     * @param cognome cognome del player
     * @param filename nome del file fxml da reperire*/
    LabCommand(String nome, String cognome, String filename){

        this.nome = nome;
        this.cognome = cognome;
        this.filename = filename;

    }
/**
 * Metodo che equivale all'execute, che ogni concrete command estende. Si occupa
 * del caricamento del labirinto
 * @param event il trigger*/
    abstract void loadLab(Event event) throws IOException;

}
