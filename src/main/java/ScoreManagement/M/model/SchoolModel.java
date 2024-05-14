package ScoreManagement.M.model;
 
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
 
@Data
@Entity
@Table(name = "SCHOOL")
	
public class SchoolModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private long id;
	
	@Column(length = 3, nullable = false, name = "cd")
	private String cd;
	
	@Column(length = 20, nullable = true, name = "name")
	private String name;
	
}
 