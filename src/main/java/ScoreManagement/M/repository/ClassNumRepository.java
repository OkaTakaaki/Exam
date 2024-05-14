package ScoreManagement.M.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ScoreManagement.M.model.ClassNumModel;
 
 
@Repository
public interface ClassNumRepository extends JpaRepository<ClassNumModel, Long> {
	List<ClassNumModel> findByClassNum(String classNum);
	List<ClassNumModel> findBySchoolCd(String schoolCd);
}
