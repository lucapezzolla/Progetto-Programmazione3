package com.example.robot;

import com.example.robot.Proxy.IFile;
import com.example.robot.Proxy.ProxyFileScoreboard;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Classe astratta che contiene due metodi statici, che
 * ricopre la responsabilita' di gestire le TableViews, sia
 * da un punto di vista di constraints che di visualizzazione delle informazioni*/
public abstract class TableManager {

    /** Metodo che setta i vincoli base delle colonne di
     * una TableView
     * @param player la colonna player
     * @param points la colonna pt*/
    public static void setConstraints(TableColumn player, TableColumn points)
    {
        player.setResizable(false);
        points.setResizable(false);
        player.setReorderable(false);
        points.setReorderable(false);
        player.setSortable(false);
        points.setSortable(false);
    }

    /**
     * Metodo che si occupa di inserire le informazioni prese dalla
     * scoreboard all'interno della TableView
     * @param filename il nome del file della scoreboard
     * @param player la colonna player
     * @param points la colonna points
     * @param table la table view*/
    public static void tableUpdater(String filename, TableColumn player, TableColumn points, TableView table) throws IOException {
        IFile scoreboard = new ProxyFileScoreboard(filename);
        ArrayList<String[]> strings = scoreboard.read();
        ObservableList<PlayerProperty> data = FXCollections.observableArrayList();

        for (String[] s:strings)
            data.add(new PlayerProperty(s[0],s[1]));

        player.setCellValueFactory(new PropertyValueFactory<PlayerProperty,String>("player"));
        points.setCellValueFactory(new PropertyValueFactory<PlayerProperty,String>("pnt"));
        setConstraints(player, points);
        table.setItems(data);
    }


}
