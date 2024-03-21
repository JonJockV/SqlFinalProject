package game.database.controller.model;

import java.util.HashSet;
import java.util.Set;

import game.database.entity.Developer;
import game.database.entity.Game;
import game.database.entity.Player;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GameData {
	
	private Long gameId;
	private String gameName;
	private String gameGenre;
	private String gameLocation;
	private GameDeveloper developer;
	private Set<GamePlayer> gamePlayers = new HashSet<>();
	
	public GameData (Game game) {
		gameId = game.getGameId();
		gameName = game.getGameName();
		gameGenre = game.getGameGenre();
		gameLocation = game.getGameLocation();
		developer = new GameDeveloper(game.getDeveloper());
		
		for(Player player : game.getPlayers()) {
			gamePlayers.add(new GamePlayer(player));
		}
	}
	
	@Data
	@NoArgsConstructor
	public static class GameDeveloper{
		private Long developerId;
		private String developerName;
		
		public GameDeveloper (Developer developer) {
			developerId = developer.getDeveloperId();
			developerName = developer.getDeveloperName();
		}
	}
	
	@Data
	@NoArgsConstructor
	public static class GamePlayer {
		private Long playerId;
		private String playerName;
		private int playerAge;
		private String playerEmail;
		
		public GamePlayer(Player player) {
			playerId = player.getPlayerId();
			playerName = player.getPlayerName();
			playerAge = player.getPlayerAge();
			playerEmail = player.getPlayerEmail();
		}
	}
}
