package com.example.robot.Proxy;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Classe Proxy del pattern Proxy, che imlementa l'interfaccia di servizio
 * e permette la gestione dei files relativi alle scoreboards*/
public class ProxyFileScoreboard implements IFile{

    /**
     * Il file della scoreboard*/
    private File scoreboard;

    /**
     * Nome del player*/
    private String nome;

    /**
     * Cognome del player*/
    private String cognome;

    /**
     * Punteggio del player*/
    private Integer punt;

    /**
     * Stringa che verra' inserita nel file*/
    private String playerName;

    /**
     * Costruttore, che prende in input
     * @param filename il nome del file della scoreboard
     * @param nome nome del player
     * @param cognome cognome del player
     * @param punt punteggio del player*/
    public ProxyFileScoreboard(String filename, String nome, String cognome, Integer punt) throws IOException {
        Path path = Paths.get(System.getProperty("user.home")+"/"+"RoboMazeScbrds");
        Files.createDirectories(path);
        scoreboard = new File(path+"/"+filename);
        this.nome = nome;
        this.cognome = cognome;
        this.punt = punt;
        playerName = nome + " " + cognome;


    }

    /**
     * Costruttore alternativo, utilizzato per operazioni
     * di sola lettura, che prende in input
     * @param filename il nome del file della scoreboard*/
    public ProxyFileScoreboard(String filename) throws IOException {
        Path path = Paths.get(System.getProperty("user.home")+"/"+"RoboMazeScbrds");
        Files.createDirectories(path);
        scoreboard = new File(path+"/"+filename);
    }


    /**
     * Metodo che crea un nuovo file se non esistente,
     * altrimenti non fa nulla*/
    private void open() throws IOException {
        scoreboard.createNewFile();
    }

    @Override
    public Boolean write() throws IOException {
        this.open();
        ArrayList<String[]> lineStrings = this.read();
        if(lineStrings.size() < 1){ //se il file e' vuoto, scrivi

            try (BufferedWriter out = new BufferedWriter(new FileWriter(scoreboard))) {
                //scrivi la stringa e una nuova linea sul file
                out.write(playerName+ "," + punt.toString());
                out.newLine();

            } catch (IOException e) {

                System.out.println("Exception ");
                return false;

            }
            return true;
        }

        else {
            Map<String,String> map = new HashMap<>();
            String[] array = new String[2];
            for (String[] s: lineStrings) {
                map.put(s[0],s[1]);
                if(s[0].equals(playerName)){
                    array=s;
                }
            }
            if (map.containsKey(playerName)){ //se esiste un altro player con lo stesso nome
                if (Integer.parseInt(map.get(playerName)) < punt) { //se il suo score e' peggiore
                    return true;
                }
                else{
                    lineStrings.remove(array); //togli il vecchio punteggio dalla lista
                    int index = 0;
                    for (String[] s: lineStrings){
                        if(Integer.parseInt(s[1]) > punt) //se hai trovato il suo nuovo posto in classifica
                            break;
                        index++;
                    }
                    array[1] = punt.toString();
                    lineStrings.add(index,array); //aggiungilo alla posizione giusta

                    try (BufferedWriter out = new BufferedWriter(new FileWriter(scoreboard))) {

                        for (int i = 0; i< lineStrings.size(); i++){
                            out.append(lineStrings.get(i)[0]);
                            out.append(",");
                            out.append(lineStrings.get(i)[1]);
                            out.newLine();
                        }
                    } catch (IOException e) {
                        System.out.println("Exception ");
                        return false;
                    }
                    return true;
                }
            }
            else{ //altrimenti trova comunque il suo posto nella classifica
                int index = 0;
                for (String[] s: lineStrings){
                    if(Integer.parseInt(s[1]) > punt)
                        break;
                    index++;
                }
                array[0] = playerName;
                array[1] = punt.toString();
                lineStrings.add(index,array);
                try (BufferedWriter out = new BufferedWriter(new FileWriter(scoreboard))) {

                    for (int i = 0; i< lineStrings.size(); i++){
                        out.append(lineStrings.get(i)[0]);
                        out.append(",");
                        out.append(lineStrings.get(i)[1]);
                        out.newLine();
                    }
                } catch (IOException e) {
                    System.out.println("Exception ");
                    return false;
                }
                return true;
            }
        }
    }



    @Override
    public ArrayList<String[]> read() throws IOException {
        this.open();
        BufferedReader reader;
        ArrayList<String[]> lineStrings = new ArrayList<>();
        String[] arraySplit;
        try {
            reader = new BufferedReader(new FileReader(scoreboard));
            String line = reader.readLine();

            while (line != null) {
                if(!line.isBlank()) { //se la riga letta non e' vuota / fatta solo di spazi
                    arraySplit = line.split(",");
                    lineStrings.add(arraySplit);
                }
                line = reader.readLine();
            }
            reader.close();
            return lineStrings;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lineStrings;
    }









}
