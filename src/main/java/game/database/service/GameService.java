package game.database.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import game.database.controller.model.GameData;
import game.database.controller.model.GameData.GameDeveloper;
import game.database.controller.model.GameData.GamePlayer;
import game.database.dao.DeveloperDao;
import game.database.dao.GameDao;
import game.database.dao.PlayerDao;
import game.database.entity.Developer;
import game.database.entity.Game;
import game.database.entity.Player;

@Service
public class GameService {

	@Autowired
	private GameDao gameDao;

	@Autowired
	private DeveloperDao developerDao;

	@Autowired
	private PlayerDao playerDao;

	public GameData saveGame(GameData gameData, Long developerId) {

		Long gameId = gameData.getGameId();
		Developer developer = findDeveloperById(developerId);
		Game game = findOrCreateGame(gameId);
		copyGameFields(game, gameData);

		game.setDeveloper(developer);
		developer.getGames().add(game);

		return new GameData(gameDao.save(game));
	}

	private void copyGameFields(Game game, GameData gameData) {
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

	public List<GameData> getAllGames() {
		List<Game> games = gameDao.findAll();
		List<GameData> gameData = new LinkedList<>();

		for (Game game : games) {
			GameData gd = new GameData(game);

			gameData.add(gd);
		}
		return gameData;
	}

	public GameData getGameById(Long gameId) {
		return new GameData(findGameById(gameId));
	}

	@Transactional(readOnly = false)
	public GamePlayer savePlayer(Long gameId, GamePlayer gamePlayer) {
		Game game = findGameById(gameId);
		Player player = findOrCreatePlayer(gamePlayer.getPlayerId(), gameId);
		copyPlayerFields(player, gamePlayer);
		player.getGames().add(game);
		game.getPlayers().add(player);
		return new GamePlayer(playerDao.save(player));
	}

	private void copyPlayerFields(Player player, GamePlayer gamePlayer) {
		player.setPlayerId(gamePlayer.getPlayerId());
		player.setPlayerName(gamePlayer.getPlayerName());
		player.setPlayerAge(gamePlayer.getPlayerAge());
		player.setPlayerEmail(gamePlayer.getPlayerEmail());
	}

	private Player findOrCreatePlayer(Long playerId, Long gameId) {
		Player player;

		if (Objects.isNull(playerId)) {
			player = new Player();
		} else {
			player = findPlayerById(playerId);
		}
		return player;
	}

	public Player findPlayerById(Long playerId) {
		return playerDao.findById(playerId)
				.orElseThrow(() -> new NoSuchElementException("Player with ID=" + playerId + " was not found."));
	}

	@Transactional(readOnly = false)
	public List<GameDeveloper> getAllDevelopers() {
		List<Developer> developers = developerDao.findAll();
		List<GameDeveloper> response = new LinkedList<>();
		
		for(Developer developer : developers) {
			response.add(new GameDeveloper(developer));
		}
		return response;
	}

	public GameDeveloper getDeveloperById(Long developerId) {
		return new GameDeveloper(findDeveloperById(developerId));
	}
}
