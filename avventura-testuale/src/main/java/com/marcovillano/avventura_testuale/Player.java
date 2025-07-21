/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.marcovillano.avventura_testuale;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author marcovillano
 */
public class Player implements Serializable {
    private static final long serialVersionUID = 1L; // Per la serializzazione
    private String playerName;
    private Room currentRoom;
    private List<Item> inventory;
    private int sealsCollected; // Contatore per i Sigilli della Conoscenza
    private String moralChoice; 

    public Player(String playerName, Room startingRoom) {
        this.playerName = playerName;
        this.currentRoom = startingRoom;
        this.inventory = new ArrayList<>();
        this.sealsCollected = 0;
        this.moralChoice = "indefinito"; // Valore iniziale
    }

    // Getters e Setters
    public String getPlayerName() {
        return playerName;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public void addItem(Item item) {
        this.inventory.add(item);
    }

    public void removeItem(Item item) {
        // Rimuove la prima occorrenza dell'item con lo stesso nome
        this.inventory.removeIf(i -> i.getName().equalsIgnoreCase(item.getName()));
    }

    public boolean hasItem(String itemName) {
        return inventory.stream().anyMatch(item -> item.getName().equalsIgnoreCase(itemName));
    }

    public Item getItem(String itemName) {
        return inventory.stream()
                .filter(item -> item.getName().equalsIgnoreCase(itemName))
                .findFirst()
                .orElse(null);
    }

    public int getSealsCollected() {
        return sealsCollected;
    }

    public void addSeal() {
        this.sealsCollected++;
    }

    public String getMoralChoice() {
        return moralChoice;
    }

    public void setMoralChoice(String moralChoice) {
        this.moralChoice = moralChoice;
    }
}
