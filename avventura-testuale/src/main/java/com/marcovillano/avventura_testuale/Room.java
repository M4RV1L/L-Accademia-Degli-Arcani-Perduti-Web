/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.marcovillano.avventura_testuale;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author marcovillano
 */
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "name")
public class Room implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String description;
    private Map<String, Room> exits; // Direzioni e stanze collegate
    private List<Item> items;
    private NPC npc; // Potrebbe esserci un NPC in questa stanza
    private boolean isSealedRoom; // Indica se la stanza contiene un sigillo
    private boolean puzzleSolved; // Indica se l'enigma della stanza (se presente) è stato risolto

    public Room(String name, String description) {
        this.name = name;
        this.description = description;
        this.exits = new HashMap<>();
        this.items = new ArrayList<>();
        this.isSealedRoom = false;
        this.puzzleSolved = false; // Di default nessun puzzle risolto
    }

    // Getters e Setters
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, Room> getExits() {
        return exits;
    }

    public void addExit(String direction, Room room) {
        this.exits.put(direction, room);
    }

    public List<Item> getItems() {
        return items;
    }

    public void addItem(Item item) {
        this.items.add(item);
    }

    public void removeItem(Item item) {
        // Rimuove la prima occorrenza dell'item con lo stesso nome
        this.items.removeIf(i -> i.getName().equalsIgnoreCase(item.getName()));
    }

    public NPC getNpc() {
        return npc;
    }

    public void setNpc(NPC npc) {
        this.npc = npc;
    }

    public boolean isSealedRoom() {
        return isSealedRoom;
    }

    public void setSealedRoom(boolean sealedRoom) {
        isSealedRoom = sealedRoom;
    }

    public boolean isPuzzleSolved() {
        return puzzleSolved;
    }

    public void setPuzzleSolved(boolean puzzleSolved) {
        this.puzzleSolved = puzzleSolved;
    }

    public String getLongDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append("Sei nella **").append(name).append("**.\n").append(description).append("\n");

        if (!items.isEmpty()) {
            sb.append("\nVedi i seguenti oggetti: ");
            for (int i = 0; i < items.size(); i++) {
                sb.append(items.get(i).getName());
                if (i < items.size() - 1) {
                    sb.append(", ");
                }
            }
            sb.append(".\n");
        }

        if (npc != null) {
            sb.append("\nC'è **").append(npc.getName()).append("** qui.\n");
        }

        sb.append("\nUscite: ");
        if (exits.isEmpty()) {
            sb.append("Nessuna.");
        } else {
            exits.keySet().forEach(exit -> sb.append(exit).append(" "));
        }
        sb.append("\n");
        return sb.toString();
    }
}