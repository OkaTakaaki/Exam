package ScoreManagement.M.repository;

 
import org.springframework.data.jpa.repository.JpaRepository;

import ScoreManagement.M.model.StudentModel;
 
public interface StudentRepository extends JpaRepository<StudentModel, Long>{
    
}