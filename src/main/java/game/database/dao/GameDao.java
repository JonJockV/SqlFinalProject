package game.database.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import game.database.entity.Game;

public interface GameDao extends JpaRepository<Game, Long> {

}
