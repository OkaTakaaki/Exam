package ScoreManagement.M.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ScoreManagement.M.model.TeacherModel;

@Repository


public interface TeacherRepository extends JpaRepository<TeacherModel, Long> {

	//ログイン時のuserIdを受け取り
    TeacherModel findByUserIdEquals(String userId);
    
}
