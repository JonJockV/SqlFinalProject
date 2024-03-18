package game.database.service;

import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import game.database.controller.model.GameData;
import game.database.dao.GameDao;
import game.database.entity.Game;

@Service
public class GameService {

	@Autowired
	private GameDao gameDao;

	public GameData saveGame(GameData gameData) {
		Long gameId = gameData.getGameId();
		Game game = findOrCreateGame(gameId);
		return new GameData(gameDao.save(game));
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
				.orElseThrow(() -> new NoSuchElementException("Game with ID=" +gameId + " was not found"));
	}

}
