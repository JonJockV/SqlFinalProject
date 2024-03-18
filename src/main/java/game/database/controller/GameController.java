package game.database.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
	
	@PostMapping("/game")
	@ResponseStatus(code = HttpStatus.CREATED)
	public GameData insertGame(@RequestBody GameData gameData) {
		log.info("Adding game {}", gameData);
		return gameService.saveGame(gameData);
	}
}
