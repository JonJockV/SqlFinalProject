package game.database.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import game.database.entity.Developer;

public interface DeveloperDao extends JpaRepository<Developer, Long> {

}
