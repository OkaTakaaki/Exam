package ScoreManagement.M.repository;

 
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ScoreManagement.M.model.SubjectModel;
 
public interface SubjectRepository extends JpaRepository<SubjectModel, Long>{

	List<SubjectModel> findBySchoolCd(String string);
	SubjectModel findByName(String subjectCds);
	SubjectModel findByCd(String subjectCd);
}