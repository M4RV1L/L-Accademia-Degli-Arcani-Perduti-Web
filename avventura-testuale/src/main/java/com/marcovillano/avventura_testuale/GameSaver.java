/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.marcovillano.avventura_testuale;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 *
 * @author marcovillano
 */
public class GameSaver implements Serializable { 

    private static final long serialVersionUID = 1L; 

    /**
     * Salva l'attuale stato del gioco su un file.
     * @param gameState Lo stato del gioco da salvare.
     * @param filename Il nome del file su cui salvare.
     */
    public static void saveGame(GameState gameState, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(gameState);
            System.out.println("Partita salvata in " + filename);
        } catch (IOException e) {
            System.err.println("Errore durante il salvataggio della partita: " + e.getMessage());
        }
    }

    /**
     * Carica uno stato del gioco da un file.
     * @param filename Il nome del file da cui caricare.
     * @return Lo stato del gioco caricato, o null se si verifica un errore.
     */
    public static GameState loadGame(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            GameState gameState = (GameState) ois.readObject();
            System.out.println("Partita caricata da " + filename);
            return gameState;
        } catch (FileNotFoundException e) {
            System.err.println("File di salvataggio non trovato: " + filename);
            return null;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Errore durante il caricamento della partita: " + e.getMessage());
            return null;
        }
    }
}
