document.addEventListener('DOMContentLoaded', () => {
    // Elementi principali del DOM
    const gameContainer = document.getElementById('game-container'); // Il contenitore principale
    const gameOutput = document.getElementById('game-output'); // L'area dove il testo viene "scritto"
    const inputArea = document.getElementById('input-area');
    const commandInput = document.getElementById('command-input');
    const submitButton = document.getElementById('submit-button');
    const menuArea = document.getElementById('menu-area');
    const menuNewGameButton = document.getElementById('menu-new-game-button');
    const menuLoadGameButton = document.getElementById('menu-load-game-button');
    const gameActions = document.getElementById('game-actions');
    const saveButton = document.getElementById('save-game-button');
    const loadButton = document.getElementById('load-game-button');
    const newGameButton = document.getElementById('new-game-button');
    const finalMessageDiv = document.getElementById('final-message');

    let gameFinished = false;
    let typingInProgress = false; // Flag per controllare se il testo è in fase di digitazione

    // --- Funzione per l'effetto digitazione ---
    async function typeWriterEffect(text) {
        typingInProgress = true;
        commandInput.disabled = true; // Disabilita input durante la digitazione
        submitButton.disabled = true; // Disabilita bottone durante la digitazione
        gameActions.classList.add('hidden'); // Nasconde i bottoni di gioco

        let i = 0;
        // Pulisce l'output solo se è la prima "scrittura" dall'avvio del gioco o nuova partita
        if (gameOutput.textContent.trim() === '' || text.startsWith('Sei nella **')) { // Heuristic: inizia una nuova "sezione"
            gameOutput.textContent = '';
        } else {
            gameOutput.textContent += '\n\n'; // Aggiungi due nuove righe prima del nuovo testo
        }

        const initialScrollHeight = gameOutput.scrollHeight; // Altezza iniziale per lo scroll

        return new Promise(resolve => {
            const typingInterval = setInterval(() => {
                if (i < text.length) {
                    gameOutput.textContent += text.charAt(i);
                    i++;
                    // Scorri automaticamente l'output verso il basso se il testo supera l'altezza
                    if (gameOutput.scrollHeight > initialScrollHeight) {
                        gameOutput.scrollTop = gameOutput.scrollHeight;
                    }
                } else {
                    clearInterval(typingInterval);
                    typingInProgress = false;
                    commandInput.disabled = false; // Riabilita input
                    submitButton.disabled = false; // Riabilita bottone
                    commandInput.focus(); // Metti il focus sull'input
                    if (!gameFinished) { // Mostra i bottoni di gioco solo se il gioco non è finito
                        gameActions.classList.remove('hidden');
                    }
                    resolve();
                }
            }, 13); // Velocità di digitazione
        });
    }

    // Funzione per inviare comandi al backend
    async function sendCommand(command) {
        if (gameFinished || typingInProgress) return;

        // Aggiungi il comando dell'utente all'output immediatamente
        gameOutput.textContent += `\n> ${command}\n`;
        gameOutput.scrollTop = gameOutput.scrollHeight; 

        try {
            const response = await fetch('/api/game/command', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ command: command }),
            });
            const data = await response.json();
            await typeWriterEffect(data.message); 
            updateGameDisplayInfo(data.gameState); 
        } catch (error) {
            console.error('Errore nell\'invio del comando:', error);
            await typeWriterEffect('Errore di comunicazione con il server.');
        } finally {
            commandInput.value = '';
        }
    }

    // Funzione per avviare una nuova partita
    async function startNewGame() {
        try {
            const response = await fetch('/api/game/start');
            const gameState = await response.json();
            resetGameDisplay(); // Pulisce il display
            showGameScreen(); // Mostra gli elementi di gioco
            await typeWriterEffect(gameState.player.currentRoom.longDescription); // Digita la descrizione iniziale
            updateGameDisplayInfo(gameState); // Aggiorna le info
            gameFinished = false;
            finalMessageDiv.classList.add('hidden');
        } catch (error) {
            console.error('Errore nell\'avvio della partita:', error);
            await typeWriterEffect('Errore nell\'avvio della partita.');
            showMenuScreen(); // Se fallisce, torna al menu
        }
    }

    // Funzione per salvare la partita
    async function saveGame() {
        if (typingInProgress) return;
        try {
            const response = await fetch('/api/game/save', { method: 'POST' });
            const message = await response.text();
            await typeWriterEffect(`*** ${message} ***`);
        } catch (error) {
            console.error('Errore nel salvataggio della partita:', error);
            await typeWriterEffect('Errore nel salvataggio della partita.');
        }
    }

    // Funzione per caricare la partita
    async function loadGame() {
        if (typingInProgress) return;
        try {
            const response = await fetch('/api/game/load');
            const gameState = await response.json();
            resetGameDisplay(); // Pulisce il display
            showGameScreen(); // Mostra gli elementi di gioco
            await typeWriterEffect(`*** Partita caricata con successo. ***\n${gameState.player.currentRoom.longDescription}`);
            updateGameDisplayInfo(gameState); // Aggiorna le info

            gameFinished = gameState.gameFinished;
            if (gameFinished) {
                await typeWriterEffect(`*** FINE DEL GIOCO: ${gameState.finalOutcome} ***`);
                finalMessageDiv.classList.remove('hidden');
                finalMessageDiv.textContent = `*** FINE DEL GIOCO: ${gameState.finalOutcome} ***`; 
                commandInput.disabled = true;
                submitButton.disabled = true;
                gameActions.classList.add('hidden'); // Nasconde i bottoni di gioco
            } else {
                finalMessageDiv.classList.add('hidden');
                commandInput.disabled = false;
                submitButton.disabled = false;
                gameActions.classList.remove('hidden'); // Mostra i bottoni di gioco
            }
        } catch (error) {
            console.error('Errore nel caricamento della partita:', error);
            await typeWriterEffect('Errore nel caricamento della partita. Potrebbe non esserci un salvataggio.');
            showMenuScreen(); // Se fallisce, torna al menu
        }
    }

    
    function updateGameDisplayInfo(gameState) {
       
    }


    // Funzione per resettare il display 
    function resetGameDisplay() {
        gameOutput.textContent = ''; // Pulisce l'output testuale
    }


    // --- Gestione della visibilità delle schermate ---
     function showMenuScreen() {
        menuArea.classList.remove('hidden');
        inputArea.classList.add('hidden');
        gameActions.classList.add('hidden');
        finalMessageDiv.classList.add('hidden');
        gameOutput.textContent = ''; // Pulisce l'output quando si torna al menu

        const welcomeMessage = `
  
             ╔═════════════════════════════╗
             ║  ✦ Accademia degli Arcani ✦                                                  ║
             ╚═════════════════════════════╝

    BENVENUTO NELL'ACCADEMIA DEGLI ARCANI PERDUTI!
    Realizzato da Marco Villano.
    Repository https://github.com/M4RV1L/L-Accademia-Degli-Arcani-Perduti-Web
         
         
    Seleziona un'opzione per iniziare la tua avventura:
        `; 
        
        typeWriterEffect(welcomeMessage);
    }

    function showGameScreen() {
        menuArea.classList.add('hidden');
        inputArea.classList.remove('hidden');
        gameActions.classList.remove('hidden'); // Mostra i bottoni di gioco
        commandInput.focus();
    }

    // Gestori eventi
    submitButton.addEventListener('click', () => {
        const command = commandInput.value.trim();
        if (command && !typingInProgress) { // Aggiunto controllo typingInProgress
            sendCommand(command);
        }
    });

    commandInput.addEventListener('keypress', (e) => {
        if (e.key === 'Enter' && !typingInProgress) { // Aggiunto controllo typingInProgress
            submitButton.click();
        }
    });

    saveButton.addEventListener('click', saveGame);
    loadButton.addEventListener('click', loadGame);
    newGameButton.addEventListener('click', startNewGame);

    menuNewGameButton.addEventListener('click', startNewGame);
    menuLoadGameButton.addEventListener('click', loadGame);

    // Avvia l'esperienza mostrando il menu all'inizio
    showMenuScreen();
});