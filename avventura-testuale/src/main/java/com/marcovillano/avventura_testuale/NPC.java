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
public class NPC implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String description;
    private Map<String, String> dialogueOptions; 
    private boolean puzzleActive; // Indica se l'NPC ha un enigma attivo
    private String requiredItem; // Oggetto necessario per interagire o risolvere puzzle
    private Item rewardItem; // Oggetto che l'NPC può dare come ricompensa (non usato per i sigilli)
    private boolean sealGiven; // Indica se l'NPC ha già dato il sigillo (o completato la sua funzione principale)

    public NPC(String name, String description) {
        this.name = name;
        this.description = description;
        this.sealGiven = false;
        this.puzzleActive = false; // Di default nessun puzzle attivo
    }

    // Getters e Setters
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Map<String, String> getDialogueOptions() {
        return dialogueOptions;
    }

    public void setDialogueOptions(Map<String, String> dialogueOptions) {
        this.dialogueOptions = dialogueOptions;
    }

    public boolean isPuzzleActive() {
        return puzzleActive;
    }

    public void setPuzzleActive(boolean puzzleActive) {
        this.puzzleActive = puzzleActive;
    }

    public String getRequiredItem() {
        return requiredItem;
    }

    public void setRequiredItem(String requiredItem) {
        this.requiredItem = requiredItem;
    }

    public Item getRewardItem() {
        return rewardItem;
    }

    public void setRewardItem(Item rewardItem) {
        this.rewardItem = rewardItem;
    }

    public boolean hasSealGiven() {
        return sealGiven;
    }

    public void setSealGiven(boolean sealGiven) {
        this.sealGiven = sealGiven;
    }

    // Metodo generico di interazione 
    public String interact(Player player, String choice) {
        return "Interazione con " + name + ": " + choice;
    }
}
