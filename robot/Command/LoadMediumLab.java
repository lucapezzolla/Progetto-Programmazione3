package com.example.robot.Command;

import com.example.robot.HelloApplication;
import com.example.robot.LabController;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Classe che estende LabCommand, andando a collocarsi tra i Concrete
 * Commands del pattern Command. Si occupa della creazione della scena
 * per il labirinto di difficolta' Medium*/
public class LoadMediumLab extends LabCommand {


    /**
     * Costruttore, che prende in input
     * @param nome nome del player
     * @param cognome cognome del player
     * @param filename nome del file fxml da reperire*/
    public LoadMediumLab(String nome, String cognome, String filename){
        super(nome,cognome,filename);
        this.diff = 2;
    }
    /**
     * Metodo ridefinito dalla classe astratta, che prende in input l'evento
     * trigger e si occupa della costruzione del livello medium, caricando il file
     * fxml corrispondente, assegnando il controller e creando la scena per il game.*/
    @Override
    public void loadLab(Event event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("mediumLab.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        LabController maze = new LabController(this);
        fxmlLoader.setController(maze);
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.setMaximized(true);
        stage.setResizable(false);
    }
}
