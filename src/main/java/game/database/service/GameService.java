package game.database.service;

import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import game.database.controller.model.GameData;
import game.database.dao.DeveloperDao;
import game.database.dao.GameDao;
import game.database.entity.Developer;
import game.database.entity.Game;

@Service
public class GameService {

	@Autowired
	private GameDao gameDao;

	@Autowired
	private DeveloperDao developerDao;
	
	public GameData saveGame(GameData gameData, Long developerId) {
		
		Long gameId = gameData.getGameId();
		Developer developer = findDeveloperById(developerId);
		Game game = findOrCreateGame(gameId);
		copyGameFields(game, gameData, developer);
		
		game.setDeveloper(developer);
		developer.getGames().add(game);
		
		return new GameData(gameDao.save(game));
	}

	private void copyGameFields(Game game, GameData gameData, Developer developer) {
		game.setGameId(gameData.getGameId());
		game.setGameName(gameData.getGameName());
		game.setGameGenre(gameData.getGameGenre());
		game.setGameLocation(gameData.getGameLocation());
	}

	private Developer findDeveloperById(Long developerId) {
		return developerDao.findById(developerId)
				.orElseThrow(() -> new NoSuchElementException("Developer with ID=" + developerId + " was not found"));
	}

	private Game findOrCreateGame(Long gameId) {
		Game game;

		if (Objects.isNull(gameId)) {
			game = new Game();
		} else {
			game = findGameById(gameId);
		}
		return game;
	}

	private Game findGameById(Long gameId) {
		return gameDao.findById(gameId)
				.orElseThrow(() -> new NoSuchElementException("Game with ID=" + gameId + " was not found"));
	}

	public void deleteGameById(Long gameId) {
		Game game = findGameById(gameId);
		gameDao.delete(game);
	}

}
