package com.example.robot;

import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import java.util.Objects;
import java.util.function.UnaryOperator;

/**
 * Classe astratta che contiene due metodi statici, che
 * ricopre la responsabilita' di gestire le TextFields,
 * da un punto di vista di constraints */
public abstract class TextManager {

    /**
     * Metodo che controlla lo stato delle TextFields
     * @param text1 una textfield
     * @param text2 una textfield
     * @return boolean un booleano che indica se e' stato scritto
     * del testo in entrambe le textfield contemporaneamente*/
    public static boolean textConstraints(TextField text1, TextField text2)
    {
        if(!Objects.equals(text1.getText(), "") && !Objects.equals(text2.getText(), "")) {
            return false;
        }
        else
        {
            return true;
        }
    }

    /**
     * Metodo che setta un vincolo di lunghezza massima ad una Textfield
     * @param text una textfield*/
    public static void textLength(TextField text)
    {
        int len = 12;

        UnaryOperator<TextFormatter.Change> rejectChange = c -> {
            if (c.isContentChange()) {
                if (c.getControlNewText().length() > len) {
                    final ContextMenu menu = new ContextMenu();
                    menu.getItems().add(new MenuItem("This field takes\n"+len+" characters only."));
                    menu.show(c.getControl(), Side.BOTTOM, 0, 0);
                    return null;
                }
            }
            return c;
        };

        text.setTextFormatter(new TextFormatter(rejectChange));
    }
}
