package game.database.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import game.database.controller.model.GameData;
import game.database.service.GameService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/game_database")
@Slf4j
public class GameController {
	
	@Autowired
	private GameService gameService;
	
	@PostMapping("/game/{developerId}")
	@ResponseStatus(code = HttpStatus.CREATED)
	public GameData insertGame(@PathVariable Long developerId, @RequestBody GameData gameData) {
		log.info("Adding game {} with developer {}", gameData, developerId);
		return gameService.saveGame(gameData, developerId);
	}
	
	@DeleteMapping("/game/{gameId}")
	public Map<String, String> deleteGameById(@PathVariable Long gameId) {
		log.info("Deleting game with ID={}", gameId);
		
		gameService.deleteGameById(gameId);
		
		return Map.of("message", "Deletion of game with ID=" + gameId + " was successful");
	}
}
