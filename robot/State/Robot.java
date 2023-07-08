package com.example.robot.State;

import com.example.robot.LabInteractors.Cell;
import com.example.robot.LabInteractors.CellVal;
import com.example.robot.LabInteractors.Position;


/**
 * Classe che rappresenta il robot, il vero player del labirinto
 * Rappresenta il Context del pattern State*/
public class Robot{

    /**
     * Lo stato attuale del robot*/
    private State robotState;

    /**
     * La cella attuale in cui si trova il robot*/
    private Cell actualCell;

    /**
     * Costruttore, che prende in input
     * @param cell la cella iniziale del robot
     * @param robotState lo stato iniziale del robot*/
    public Robot(Cell cell, State robotState){
        this.robotState = robotState;
        this.actualCell = cell;
    }

    public void setActualCell(Cell actualCell) {
        this.actualCell = actualCell;
    }

    public Position getPositionCoords(){
        return this.actualCell.getPos();
    }

    public Cell getActualCell() {
        return actualCell;
    }

    public CellVal getActualCellValue() {
        return actualCell.getValue();
    }

    public void setRobotState(State robotState) {
        this.robotState = robotState;
    }

    public State getRobotState() {
        return robotState;
    }

    /**
     * Metodo che richiama il doAction() dello stato del robot, e in
     * particolare, computa la prossima mossa.
     * @param cell la cella dalla quale computare la prossima mossa
     * @return Integer l'id della prossima cella in cui dovra' spostarsi il robot*/
    public Integer move(Cell cell) {
        return this.robotState.doAction(cell);
    }
}
