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
@Table(name = "SUBJECT")
	
public class SubjectModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private long id;
	
	@Column(length = 3, nullable = false, name = "schoolCd")
	private String schoolCd;
	
	@Column(length = 3, nullable = false, name = "cd")
	private String cd;
	
	@Column(length = 20, nullable = true, name = "name")
	private String name;
	
	
	// ゲッター
    public String getCd() {
        return cd;
    }

    public String getName() {
        return name;
    }

    public String getSchoolCd() {
        return schoolCd;
    }

    // セッター
    public void setCd(String cd) {
        this.cd = cd;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSchoolCd(String schoolCd) {
        this.schoolCd = schoolCd;
    }
}

	
 