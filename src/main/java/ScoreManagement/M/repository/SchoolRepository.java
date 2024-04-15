package ScoreManagement.M.repository;

 
import org.springframework.data.jpa.repository.JpaRepository;

import ScoreManagement.M.model.SchoolModel;
 
public interface SchoolRepository extends JpaRepository<SchoolModel, Long>{
    
}