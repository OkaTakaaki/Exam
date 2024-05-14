package ScoreManagement.M.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ScoreManagement.M.model.StudentModel;

public interface StudentRepository extends JpaRepository<StudentModel, Long> {
    List<StudentModel> findByEntYear(Integer entYear);
    List<StudentModel> findByClassNum(String classNum);
    List<StudentModel> findByIsAttend(Boolean isAttend);
	List<StudentModel> findBySchoolCd(String string);
}
