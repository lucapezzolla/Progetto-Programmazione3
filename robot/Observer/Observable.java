package com.example.robot.Observer;

/**
 * Interfaccia che implementa i metodi di subscribe e notify,
 * Observable nel pattern Observer*/
public interface Observable {
    /**
     * Iscrive un oggetto PositionSubscriber alla "newsletter"
     * dell'Observable*/
    public void subscribe(PositionSubscriber o);

    /**
     * Notifica tutti gli oggetti PositionSubscriber iscritti
     * alla "newsletter"*/
    public void notifySubscribers();
}
