package com.example.robot;

import com.example.robot.Command.LabCommand;
import com.example.robot.Observer.Game;
import com.example.robot.LabInteractors.Cell;
import com.example.robot.LabInteractors.CellVal;
import com.example.robot.Observer.GameSub;
import com.example.robot.Proxy.IFile;
import com.example.robot.Proxy.ProxyFileScoreboard;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Controller del labirinto - consente la gestione della scena di gioco
 * e del suo prosieguo.
 * E' anche la classe Receiver nel pattern Command*/
public class LabController{

    /**
    * La TableView dello score finale */
    @FXML
    TableView tableFScore;

    /**
     * Le colonne della TableView dello score finale*/
    @FXML
    TableColumn playerCol, ptCol;
    /**
     * Bottoni Play e Continue*/
    @FXML
    Button playBTN, continueBTN;

    /**
     * Rappresentazione del robot a livello grafico*/
    @FXML
    Rectangle robot;

    /**
     * ArrayList contenente l'insieme delle celle del labirinto*/
    @FXML
    ArrayList<Rectangle> matrix;

    /**
     * Label di nome, cognome, stato e punteggio*/
    @FXML
    Label LabelNome, labelGameOver, labelRobotState, punteggio, newRecordLabel;


    /**
     * Il labirinto vero e proprio*/
    private Cell[][] field;

    /**
     * Istanza della classe game - La partita*/
    private Game instance;

    /**
     * L'iscritto alla newsletter di Game, che contiene
     * gli aggiornamenti di stato della classe - Observer*/
    private GameSub gameStateSub;

    /**
     * Numero di passi*/
    private int steps = 0;

    /**
     * La timeline*/
    private Timeline timeline;

    /**
     * Il concrete command giunto dall'Invoker (Command)*/
    private final LabCommand command;


    /**
     * Costruttore, che prende in input
     * @param command il concrete command inviato dall'invoker*/
    public LabController(LabCommand command){
        this.command = command;
    }

    /**
     * Metodo che inizializza i diversi oggetti coinvolti nella partita*/
    public void initialize() {
        instance = new Game(command.diff); //difficolta
        gameStateSub = new GameSub(); //subscriber - Observer
        LabelNome.setText(command.nome + "\n" + command.cognome); //label del player
    }

    /**
     * Metodo che viene eseguito alla pressione del tasto "Play" - si occupa
     * di gestire la timeline della partita, di controllarne l'avanzamento
     * e della gestione della scoreboard finale*/
    public void onPlayButton(){
        buttonSoundPlay();
        playBTN.setDisable(true);
        instance.subscribe(gameStateSub);

        timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
            instance.notifySubscribers();
            steps++;
            punteggio.setText("Steps: " + steps); //incrementiamo gli steps
            instance.move();
            instance.updateCells();
            field = gameStateSub.getLab();
            this.setLabColors(); //aggiorniamo la rappresentazione grafica
            instance.notifySubscribers();
            labelRobotState.setText(gameStateSub.getState()); //aggiorniamo lo stato del robot


            if (instance.getRobotPos() == instance.goal()) {
                try {
                    this.afterGame();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }


            robot.setLayoutX((robot.getLayoutX() + (gameStateSub.getUpdate().getY() * 50))); //aggiorniamo la posizione del robot sull'asse x
            robot.setLayoutY(((robot.getLayoutY() + (gameStateSub.getUpdate().getX() * 50)))); //aggiorniamo la posizione del robot sull'asse y
        }));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /**
     * Metodo che rappresenta a video il colore delle celle del labirinto*/
    private void setLabColors(){
        for(int i = 0; i < gameStateSub.getDim(); i++)
            for(int j = 0; j < gameStateSub.getDim(); j++)
                if(field[i][j].getValue() == CellVal.wall) { }
                else
                {
                    if(field[i][j].getValue() == CellVal.cyan)
                        matrix.get(i*gameStateSub.getDim() + j).setFill(Color.CYAN);
                    else if(field[i][j].getValue() == CellVal.red)
                        matrix.get(i*gameStateSub.getDim() + j).setFill(Color.RED);
                    else if(field[i][j].getValue() == CellVal.green)
                        matrix.get(i*gameStateSub.getDim() + j).setFill(Color.GREEN);
                    else if(field[i][j].getValue() == CellVal.yellow)
                        matrix.get(i*gameStateSub.getDim() + j).setFill(Color.YELLOW);
                    else
                        matrix.get(i*gameStateSub.getDim() + j).setFill(Color.GREY);
                }

    }


    /**
     * Metodo che si attiva alla pressione del tasto "Continue".
     * Crea una nuova scena, corrispondente al menu' iniziale
     * @param event l'evento trigger*/
    public void onContinueButton(ActionEvent event) throws IOException {
        buttonSoundPlay();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
        Stage stageMenu = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stageMenu.setTitle("RoboMaze");
        stageMenu.setResizable(false);
        stageMenu.setScene(scene);
        stageMenu.show();
    }

    /**
     * Metodo che gestisce l'after game, creando una nuova finestra
     * nella quale compare la scoreboard*/
    private void afterGame() throws IOException {
        gameOverSoundPlay();
        timeline.stop();
        labelGameOver.setVisible(true);
        continueBTN.setVisible(true);
        boolean esit = saveOnFile();
        assert esit;
        showFinalScoreboard();
    }

    /**
     * Metodo che aggiorna la scoreboard corrispondente e restituisce
     * in output una rappresentazione della stessa per una scrittura in
     * ListView
     * @return ArrayList<String> lista di stringhe adatte alla scrittura su ListView*/
    private boolean saveOnFile() throws IOException {

        IFile scoreboard = new ProxyFileScoreboard(command.filename,command.nome,command.cognome,steps);
        boolean esit = false;
        try {
            esit = scoreboard.write();

            if(!esit)
                return false;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    /**
     * Metodo che aggiorna le informazioni nella TableView e le mostra
     * su schermo*/
    private void showFinalScoreboard() throws IOException {
        TableManager.tableUpdater(command.filename,playerCol,ptCol,tableFScore);
        tableFScore.setVisible(true);
    }

    /***
     * Metodo per la gestione del suono dei bottoni
     */
    public void buttonSoundPlay()
    {
        File buttonAudioSound = new File("src/main/resources/sound/buttonSound.mp3");
        AudioClip buttonSound = new AudioClip(buttonAudioSound.toURI().toString());
        buttonSound.setVolume(0.05);
        buttonSound.play();
    }

    /**
     * Metodo che riproduce il suo di game over*/
    public void gameOverSoundPlay()
    {
        File gOSound = new File("src/main/resources/sound/gameOverSound.mp3");
        AudioClip gameOverSound = new AudioClip(gOSound.toURI().toString());
        gameOverSound.setVolume(0.05);
        gameOverSound.play();
    }
}