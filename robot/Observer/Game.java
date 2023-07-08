package com.example.robot.Observer;

import com.example.robot.FactoryMethod.*;
import com.example.robot.LabInteractors.Cell;
import com.example.robot.LabInteractors.CellVal;
import com.example.robot.LabInteractors.Position;
import com.example.robot.State.Robot;
import com.example.robot.State.EvadeState;
import com.example.robot.State.FleeState;
import com.example.robot.State.PursuitState;
import com.example.robot.State.SeekState;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
/**
 * Classe ad alto livello che gestisce il gioco - Crea un labirinto,
 * gestisce le mosse del robot/cambiamenti delle celle. Al suo interno ha anche
 * un modulo per consentirgli di notificare le classi che vogliono conoscere
 * lo stato attuale del labirinto (pattern Observer)*/
public class Game implements Observable {

    /**
     * Oggetto della classe Labyrinth - il labirinto*/
    private Labyrinth lab;

    /**
     * Robot - il player*/
    private Robot robot;

    /**
     *ArrayList che contiene tutte le classi iscritte alla "newsletter"
     * di Game - pattern Observer*/
    private ArrayList<PositionSubscriber> subscribers;

    /**
     *Variabile booleana che indica se la mossa sia la prima o meno*/
    private Boolean firstMove;



    /**
     * Il costruttore della classe Game prende in input soltanto la difficolta',
     * passata come intero. In base a quest'ultima avviene la generazione del labirinto,
     * mediante l'utilizzo del FactoryMethod.*/
    public Game(int difficolta){

        LabCreator labi;

        //switch case - In base alla difficolta', differenti labirinti.
        switch (difficolta) {
            case 1 -> {
                labi = new EasyLabCreator();
                this.lab = labi.createLab();
            }
            case 2 -> {
                labi = new MediumLabCreator();
                this.lab = labi.createLab();
            }
            case 3 -> {
                labi = new HardLabCreator();
                this.lab = labi.createLab();
            }
        }
        //per sicurezza, assert lab !=null ci evita di entrare nel caso in cui
        //la creazione sia stata fallace
        assert lab != null;
        this.robot = new Robot(lab.getCellById(0),new PursuitState(this.lab.getEdges(), lab.getCellById(0)));
        subscribers = new ArrayList<>();
        firstMove = true;

    }

    @Override
    /**
     * Metodo che iscrive un oggetto di tipo PositionSubscriber (Subscriber)
     * alla "newsletter" di Game (il Publisher) - pattern Observer*/
    public void subscribe(PositionSubscriber o){
        subscribers.add(o);
    }


    @Override
    /**
     * Metodo che aggiorna i Subscribers del cambio di stato del labirinto,
     * aggiornandoli sulla posizione del Robot, sullo stato dello stesso e
     * sullo stato delle Celle*/
    public void notifySubscribers(){
        for (PositionSubscriber o:subscribers) {
            o.update(this.robot.getPositionCoords(),this.getLab(),this.lab.getDim(), this.robot.getRobotState());
        }
    }

    public Cell[][] getLab() {
        return this.lab.getLab();
    }

    /**
     * Metodo che aggiorna i colori delle celle (gli oggetti all'interno di esse).
     * Scorre la matrice e assegna, per ogni cella, un nuovo colore, in base a una
     * probabilita'.*/
    public void updateCells(){
        int randomNum;
        Cell[][] maze = this.lab.getLab();
        for(int i=0; i<this.lab.getDim(); i++)
            for(int j=0; j<this.lab.getDim(); j++) {

                //se e' la prima mossa, non cambiare colore alla cella di partenza
               if (firstMove) {
                   firstMove = false;
                if( i == 0 && j ==0)
                    continue;
               }


                if (maze[i][j].getValue() != CellVal.wall && maze[i][j] != maze[this.lab.getDim() - 1][this.lab.getDim() - 1]) {
                    randomNum = ThreadLocalRandom.current().nextInt(0, 12);
                    if (randomNum < 4)
                        maze[i][j].setValue(CellVal.empty);
                    else if (randomNum < 6)
                        maze[i][j].setValue(CellVal.yellow);
                    else if (randomNum < 8)
                        maze[i][j].setValue(CellVal.red);
                    else if (randomNum < 10)
                        maze[i][j].setValue(CellVal.cyan);
                    else if (randomNum < 12)
                        maze[i][j].setValue(CellVal.green);
                }
            }
    }

    /**
     * Metodo che resistuisce la Position dell'obiettivo
     * @return Position la position dell'obiettivo*/
    public Position goal(){
        return this.lab.getEndPos();
    }

    /**
     * Metodo che, sfruttando lo stato attuale del robot, calcola la sua prossima
     * mossa.
     * @return Integer un intero che indica l'id della prossima cella su cui spostarsi*/
    private Integer nextMove(){
        return this.robot.move(this.robot.getActualCell());
    }

    public Position getRobotPos(){
        return this.robot.getPositionCoords();
    }

    /**
     * Metodo che coordina il movimento del Robot, avvalendosi anche di nextMove().
     * Sfrutta il colore della cella su cui si trova ora il robot per determinarne
     * la prossima mossa.*/
    public void move() {
        Integer nextMove;

        //se la cella attuale e' vuota, muoviti tramite lo stato che gia' possiedi;
        //aggiorna poi la posizione corrente del robot.
        if (this.robot.getActualCellValue() == CellVal.empty) {

            nextMove = nextMove();
            this.robot.setActualCell(this.lab.getCellById(nextMove));

        }
        //se la cella attuale e' verde, cambia stato in "PursuitState" e calcola la prossima mossa;
        //aggiorna poi la posizione corrente del robot.
        else if (this.robot.getActualCellValue() == CellVal.green) {

            this.robot.setRobotState(new PursuitState(this.lab.getEdges(), this.robot.getActualCell()));
            nextMove = nextMove();
            this.robot.setActualCell(this.lab.getCellById(nextMove));

        }
        //se la cella attuale e' rossa, cambia stato in "SeekState" e calcola la prossima mossa;
        //aggiorna poi la posizione corrente del robot.
        else if (this.robot.getActualCellValue() == CellVal.red) {

            this.robot.setRobotState(new SeekState(this.lab.getEdges(), this.robot.getActualCell()));
            nextMove = nextMove();
            this.robot.setActualCell(this.lab.getCellById(nextMove));

        }
        //se la cella attuale e' gialla, cambia stato in "FleeState" e calcola la prossima mossa;
        //aggiorna poi la posizione corrente del robot.
        else if (this.robot.getActualCellValue() == CellVal.yellow) {

            this.robot.setRobotState(new FleeState(this.lab.getEdges(), this.robot.getActualCell()));
            nextMove = nextMove();
            this.robot.setActualCell(this.lab.getCellById(nextMove));

        }
        //se la cella attuale e' ciano, cambia stato in "EvadeState" e calcola la prossima mossa;
        //aggiorna poi la posizione corrente del robot.
        else if (this.robot.getActualCellValue() == CellVal.cyan) {

            this.robot.setRobotState(new EvadeState(this.robot.getActualCell(), this.lab.getEdges()));
            nextMove = nextMove();
            this.robot.setActualCell(this.lab.getCellById(nextMove));

        }

    }
}
