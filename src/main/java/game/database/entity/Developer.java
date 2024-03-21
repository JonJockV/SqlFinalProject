package game.database.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
public class Developer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long developerId;
	protected String developerName;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "developer", cascade = CascadeType.ALL)
	private Set<Game> games = new HashSet<Game>();
	
}
