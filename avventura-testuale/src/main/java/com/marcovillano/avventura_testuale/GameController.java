/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.marcovillano.avventura_testuale;

import org.springframework.ui.Model;
import java.io.Serializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


/**
 *
 * @author marcovillano
 */
@Controller // Indica a Spring che questa classe è un controller REST
@RequestMapping("/api/game") // Definisce il percorso base per tutti gli endpoint di questo controller
public class GameController {

    private final GameService gameService; // Inietta il GameService

    // Costruttore con iniezione di dipendenza
    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }
    
    @GetMapping("/") // Quando l'utente visita http://localhost:8080/api/game/
    public String gamePage(Model model) {
        
        model.addAttribute("initialGameState", gameService.getGameState()); // Potrebbe essere utile per debug o prime visualizzazioni
        return "game"; // Questo cerca game.html in src/main/resources/templates/
    }
    
    /**
     * Endpoint per iniziare una nuova partita.
     * GET /api/game/start
     * @return Lo stato iniziale del gioco.
     */
    @GetMapping("/start")
    @ResponseBody
    public ResponseEntity<GameState> startGame() {
        
        return ResponseEntity.ok(gameService.getGameState());
    }

    /**
     * Endpoint per inviare un comando al gioco.
     * POST /api/game/command
     * Il corpo della richiesta deve contenere un oggetto JSON con un campo "command".
     * @param request Un oggetto che contiene il comando da eseguire.
     * @return Lo stato aggiornato del gioco dopo l'esecuzione del comando,
     * più un messaggio di output.
     */
    @PostMapping("/command")
    @ResponseBody
    public ResponseEntity<GameResponse> processCommand(@RequestBody CommandRequest request) {
        String outputMessage = gameService.processCommand(request.getCommand());
        GameState currentGameState = gameService.getGameState();

        // Incapsula lo stato del gioco e il messaggio di output
        GameResponse response = new GameResponse(currentGameState, outputMessage);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint per salvare la partita.
     * POST /api/game/save
     * @return Un messaggio di conferma del salvataggio.
     */
    @PostMapping("/save")
    public ResponseEntity<String> saveGame() {
        gameService.saveGame();
        return ResponseEntity.ok("Partita salvata con successo.");
    }

    /**
     * Endpoint per caricare una partita salvata.
     * GET /api/game/load
     * @return Lo stato del gioco caricato.
     */
    @GetMapping("/load")
    @ResponseBody
    public ResponseEntity<GameState> loadGame() {
        gameService.loadGame();
        return ResponseEntity.ok(gameService.getGameState());
    }

    // --- Classi helper per le richieste e risposte ---

    // Classe interna per la richiesta del comando
    public static class CommandRequest {
        private String command;

        public String getCommand() {
            return command;
        }

        public void setCommand(String command) {
            this.command = command;
        }
    }

    // Classe interna per la risposta combinata (GameState + messaggio)
    public static class GameResponse implements Serializable {
        private static final long serialVersionUID = 1L;
        private GameState gameState;
        private String message;

        public GameResponse(GameState gameState, String message) {
            this.gameState = gameState;
            this.message = message;
        }

        public GameState getGameState() {
            return gameState;
        }

        public String getMessage() {
            return message;
        }

        // Setters (opzionali ma utili per la deserializzazione JSON se il client dovesse reinviare)
        public void setGameState(GameState gameState) {
            this.gameState = gameState;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
