package ScoreManagement.M.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ScoreManagement.M.model.TestModel;

@Repository
public interface TestRepository extends JpaRepository<TestModel, Long> {
	List<TestModel> findByNoIn(List<String> nos);
	List<TestModel> findByClassNum(String classNum);
	List<TestModel> findBySubjectCd(String subjectCd);
	List<TestModel> findByStudentNo(String studentNo);
	List<TestModel> findBySchoolCd(String schoolCd);
	
}

