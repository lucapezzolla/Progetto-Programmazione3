package com.example.robot.Command;
import javafx.event.Event;
import java.io.IOException;

/**
 * Classe invoker, si occupa di memorizzare un command e di garantire
 * l'esecuzione del suo metodo.*/
public class LabInvoker {

    LabCommand command;

/**
 * Metodo che invoca la funzione loadLab del command inserito, garantendo
 * la generazione della scena rappresentante il labirinto in base al command
 * passato come argomento.
 * @param event il trigger
 * @param command il concrete command - quale labirinto caricare*/
    public void execute(Event event, LabCommand command) throws IOException {

        this.command = command;
        this.command.loadLab(event);

    }
}
