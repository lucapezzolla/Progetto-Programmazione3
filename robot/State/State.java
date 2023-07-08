package com.example.robot.State;

import com.example.robot.LabInteractors.Cell;

/**
 * Interfaccia del pattern State*/
public interface State {

    /**
     * Metodo che consente al robot di muoversi in base al suo stato
     * @param cell la cella dalla quale computare la prossima mossa
     * @return Integer id della prossima cella*/
    Integer doAction(Cell cell);
}
