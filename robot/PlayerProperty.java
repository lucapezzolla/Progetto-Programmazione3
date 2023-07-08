package com.example.robot;

import javafx.beans.property.SimpleStringProperty;

/**
 * Classe "Wrapper" dei valori dei player e del relativo
 * punteggio, utile per consentirne la visualizzazione
 * nelle TableViews*/
public class PlayerProperty {
    /**
     * La stringa contenente nome e cognome del player*/
    private SimpleStringProperty player;

    /**
     * La stringa contenente il punteggio del player*/
    private SimpleStringProperty pnt;

    public PlayerProperty(String player, String pnt){
        this.player = new SimpleStringProperty(player);
        this.pnt = new SimpleStringProperty(pnt);
    }

    public String getPnt() {
        return pnt.get();
    }

    public String getPlayer() {
        return player.get();
    }

    public void setPlayer(String player) {
        this.player.set(player);
    }

    public void setPnt(String pnt) {
        this.pnt.set(pnt);
    }
}
