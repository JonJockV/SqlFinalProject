package game.database.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import game.database.controller.model.GameData;
import game.database.controller.model.GameData.GameDeveloper;
import game.database.controller.model.GameData.GamePlayer;
import game.database.service.GameService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/game")
@Slf4j
public class GameController {
	
	@Autowired
	private GameService gameService;
	
	//Creates a game in the database
	@PostMapping("/developer/{developerId}")
	@ResponseStatus(code = HttpStatus.CREATED)
	public GameData insertGame(@PathVariable Long developerId, @RequestBody GameData gameData) {
		log.info("Adding game {} with developer {}", gameData, developerId);
		return gameService.saveGame(gameData, developerId);
	}
	
	//Updates a game
	@PutMapping("/{gameId}/developer/{developerId}")
	public GameData updateGame(@PathVariable Long gameId, @PathVariable Long developerId, @RequestBody GameData gameData) {
		gameData.setGameId(gameId);
		log.info("Updating game {}", gameId);
		return gameService.saveGame(gameData, developerId);
	}
	
	//Retrieves all games
	@GetMapping("/")
	public List<GameData> retrieveAllGames(){
		log.info("Retrieving all games");
		return gameService.getAllGames();
	}
	
	//Retrieves game based off input ID
	@GetMapping("/{gameId}")
	public GameData retrieveGameById(@PathVariable Long gameId) {
		log.info("Retrieving game with ID={}", gameId);
		return gameService.getGameById(gameId);
	}
	
	//Attempts to Delete all games
	@DeleteMapping("/")
	public void deleteAllGames() {
		log.info("Attempting to delete all games");
		throw new UnsupportedOperationException("Deleting all games is not allowed");
	}
	
	//Delete a game by ID
	@DeleteMapping("/{gameId}")
	public Map<String, String> deleteGameById(@PathVariable Long gameId) {
		log.info("Deleting game with ID={}", gameId);
		
		gameService.deleteGameById(gameId);
		
		return Map.of("message", "Deletion of game with ID=" + gameId + " was successful");
	}
	
	//Create a player
	@PostMapping("/{gameId}/player")
	@ResponseStatus(code = HttpStatus.CREATED)
	public GamePlayer createPlayer(@PathVariable Long gameId, @RequestBody GamePlayer gamePlayer) {
		log.info("Adding player");
		return gameService.savePlayer(gameId, gamePlayer);
	}
	
	//Get all developers
	@GetMapping("/developer")
	public List<GameDeveloper> retrieveAllDevelopers() {
		log.info("Retrieving all developers");
		return gameService.getAllDevelopers();
	}
	
	@GetMapping("/developer/{developerId}")
	public GameDeveloper retrieveDeveloperById(@PathVariable Long developerId) {
		log.info("Retrieving developer with ID=", developerId);
		return gameService.getDeveloperById(developerId);
	}
}
