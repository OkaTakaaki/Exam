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
@Table(name = "TEST")
public class TestModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 10, nullable = false, name = "STUDENT_NO")
    private String studentNo;

    @Column(length = 3, nullable = true, name = "SUBJECT_CD")
    private String subjectCd;
    
    @Column(length = 3, nullable = true, name = "SCHOOL_CD")
    private String schoolCd;

    @Column(length = 10,nullable = true, name = "NO")
    private Integer no;

    @Column(length = 10, nullable = true, name = "POINT")
    private Integer point;

    @Column(length = 120,nullable = true, name = "CLASS_NUM")
    private String classNum;
}
