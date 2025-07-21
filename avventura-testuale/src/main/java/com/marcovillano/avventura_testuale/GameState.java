/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.marcovillano.avventura_testuale;

import java.io.Serializable;
import java.util.Map;

/**
 *
 * @author marcovillano
 */
public class GameState implements Serializable {
    private static final long serialVersionUID = 1L;
    private Player player;
    private Map<String, Room> rooms; // Tutte le stanze del gioco
    private long startTime; 
    private boolean gameFinished;
    private String finalOutcome; // Per i finali

    public GameState(Player player, Map<String, Room> rooms, long startTime) {
        this.player = player;
        this.rooms = rooms;
        this.startTime = startTime;
        this.gameFinished = false;
        this.finalOutcome = "";
    }

    // Getters e Setters
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Map<String, Room> getRooms() {
        return rooms;
    }

    public void setRooms(Map<String, Room> rooms) {
        this.rooms = rooms;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public boolean isGameFinished() {
        return gameFinished;
    }

    public void setGameFinished(boolean gameFinished) {
        this.gameFinished = gameFinished;
    }

    public String getFinalOutcome() {
        return finalOutcome;
    }

    public void setFinalOutcome(String finalOutcome) {
        this.finalOutcome = finalOutcome;
    }
}
