package game.database.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import game.database.entity.Player;

public interface PlayerDao extends JpaRepository<Player, Long> {
}
