package com.example.robot.Strategy;

import com.example.robot.LabInteractors.Cell;

/**
 * Interfaccia di servizio del pattern Strategy*/
public interface Strategy {

    /**
     * Metodo che, in base alla strategia, calcola la prossima mossa
     * @param cell la cella dalla quale computare la prossima mossa
     * @return Integer la cella di arrivo, come id*/
    Integer calculateNextMove(Cell cell);
}
