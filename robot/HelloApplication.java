package com.example.robot;

import com.example.robot.Command.LabInvoker;
import com.example.robot.Command.LoadEasyLab;
import com.example.robot.Command.LoadHardLab;
import com.example.robot.Command.LoadMediumLab;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;

/**
 * Classe che estende Application e che rappresenta la GUI*/
public class HelloApplication extends Application {

    /**
     * Le colonne player/punteggio usate nelle TableView, due
     * per ogni difficolta'*/
    @FXML
    TableColumn playerEasy,ptEasy,playerMed,ptMed,playerHard,ptHard;

    /**
     * Le TableView utilizzate per mostrare i punteggi prima di
     * iniziare la partita, una per ogni difficolta'*/
    @FXML
    TableView tableEasy, tableMed, tableHard;

    /**
     * La theme song*/
    @FXML
    MediaPlayer sound;

    /**
     * Il file che contiene il suono dei bottoni*/
    File buttonAudioSound = new File("src/main/resources/sound/buttonSound.mp3");

    /**
     * Il suono dei bottoni*/
    AudioClip buttonSound = new AudioClip(buttonAudioSound.toURI().toString());

    /**
     * I bottoni presenti nell'interfaccia*/
    @FXML
    Button playBTN, helpBTN, exitBTN, easyBTN, mediumBTN, hardBTN, backBTN;

    /**
     * TextField per prendere in input nome e cognome*/
    @FXML
    TextField name,lastName;

    /**
     * I diversi pane che usa l'interfaccia*/
    @FXML
    Pane rulesPane,playMainMenu;

    /**
     * L'invoker del pattern Command*/
    private LabInvoker invoker = new LabInvoker();

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setResizable(false);
        soundManager();
        stage.setTitle("RoboMaze");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Metodo che cambia la visibilita' del pane del main menu'
     * @param bool bool da inserire nel setVisible*/
    private void makePlayMenuVis(Boolean bool) {
        playMainMenu.setVisible(bool);
    }

    /**
     * Metodo che cambia la visibilita' dei bottoni del menu'
     * @param bool bool da inserire nel setVisible*/
    private void makeStartButtonsVis(Boolean bool){
        playBTN.setVisible(bool);
        helpBTN.setVisible(bool);
        exitBTN.setVisible(bool);
    }

    /**
     * Metodo che cambia la visibilita' del bottone "back"
     * @param bool bool da inserire nel setVisible*/
    private void makeBackBttnVis(Boolean bool) {
        backBTN.setVisible(bool);
    }

    /**
     * Metodo che cambia la visibilita' del pane delle regole
     * @param bool bool da inserire nel setVisible*/
    private void makeRulesPaneVis(Boolean bool) {
        rulesPane.setVisible(bool);
    }

    /**
     * Metodo che regola la visualizzazione dell'interfaccia grafica una volta premuto il bottone "Play" del main menu.*/
    @FXML
    public void playScene() throws IOException {
        buttonSoundPlay();
        makeBackBttnVis(true);
        makeStartButtonsVis(false);
        makePlayMenuVis(true);
        tableLoad();
    }

    /***
     * Metodo per il caricamento dei file all'interno delle tabelle
     */
    private void tableLoad() throws IOException{
        TableManager.tableUpdater("easyscore.dat",playerEasy,ptEasy,tableEasy);
        TableManager.tableUpdater("mediumscore.dat",playerMed,ptMed,tableMed);
        TableManager.tableUpdater("hardscore.dat",playerHard,ptHard,tableHard);
    }

    /**
     * Metodo per il controllo della corretta compilazione del campo "Nome" nel main menu.*/
    @FXML
    public void controllerText1(){
        TextManager.textLength(name);
        buttonManager(lastName, name);
    }

    /**
     * Metodo per il controllo della corretta compilazione del campo "Cognome" nel main menu.*/
    @FXML
    public void controllerText2(){
        TextManager.textLength(lastName);
        buttonManager(name, lastName);
    }

    /**
     * Metodo per le funzionalità del bottone "Exit" nel main menu.*/
    @FXML
    public void exit(ActionEvent event)
    {
        buttonSoundPlay();
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * Metodo per il caricamento del labirinto livello di difficoltà facile,
     * tramite l'invoker del pattern Command.
     * @param event l'evento trigger*/
    @FXML
    public void easyPlay(ActionEvent event) throws IOException {
        buttonSoundPlay();
        invoker.execute(event, new LoadEasyLab(name.getText(),lastName.getText(),"easyscore.dat"));
    }

    /**
     * Metodo per il caricamento del labirinto livello di difficoltà medio,
     * tramite l'invoker del pattern Command.
     * @param event l'evento trigger*/
    @FXML
    public void mediumPlay(ActionEvent event) throws IOException {
        buttonSoundPlay();
        invoker.execute(event, new LoadMediumLab(name.getText(),lastName.getText(),"mediumscore.dat"));
    }

    /**
     * Metodo per il caricamento del labirinto livello di difficoltà difficile,
     * tramite l'invoker del pattern Command.
     * @param event l'evento trigger*/
    @FXML
    public void hardPlay(ActionEvent event) throws IOException {
        buttonSoundPlay();
        invoker.execute(event, new LoadHardLab(name.getText(),lastName.getText(),"hardscore.dat"));
    }

    /**
     * Metodo che definisce la visibilita' di bottoni/panes/liste, alla
     * pressione del tasto "Back"*/
    @FXML
    public void onBackButton() {
        buttonSoundPlay();
        makeBackBttnVis(false);
        makeStartButtonsVis(true);
        makePlayMenuVis(false);
        makeRulesPaneVis(false);
    }

    /**
     * Metodo che definisce la visibilita' di bottoni/panes/liste, alla
     * pressione del tasto "Help"*/
    @FXML
    public void onHelpButton()
    {
        buttonSoundPlay();
        makeStartButtonsVis(false);
        makeRulesPaneVis(true);
        makeBackBttnVis(true);
    }

    /***
     * Metodo per la gestione della colonna sonora di RoboMaze.
     */
    private void soundManager()
    {
        File BGS = new File("src/main/resources/sound/Song_of_Storms.mp3");
        sound = new MediaPlayer(new Media(BGS.toURI().toString()));
        sound.setVolume(0.025);
        sound.setOnEndOfMedia(() -> sound.seek(Duration.ZERO));
        sound.play();
    }

    /***
     * Metodo che attiva/disattiva i bottoni per avviare la partita (Easy, Medium, Hard)
     * in base ai TextField
     * @param text1 , TextField per uno tra i campi 'Nome' o 'Cognome'
     * @param text2 , TextField per uno tra i campi 'Nome' o 'Cognome'
     */
    public void buttonManager(TextField text1, TextField text2)
    {
        if(TextManager.textConstraints(text1, text2))
        {
            easyBTN.setDisable(true);
            mediumBTN.setDisable(true);
            hardBTN.setDisable(true);
        }
        else
        {
            easyBTN.setDisable(false);
            mediumBTN.setDisable(false);
            hardBTN.setDisable(false);
        }
    }

    /**
     * Metodo che fa riprodurre un suono alla pressione di un bottone*/
    public void buttonSoundPlay()
    {
        buttonSound.setVolume(0.05);
        buttonSound.play();
    }

    public static void main(String[] args){
        launch();
    }
}