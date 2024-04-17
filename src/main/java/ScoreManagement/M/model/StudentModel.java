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
@Table(name = "STUDENT")
public class StudentModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 10, nullable = false, name = "no")
    private String no;

    @Column(length = 10, nullable = true, name = "name")
    private String name;

    @Column(nullable = true, name = "entYear")
    private Integer entYear;

    @Column(length = 3, nullable = true, name = "classNum")
    private String classNum;

    @Column(nullable = true, name = "isAttend")
    private Boolean isAttend;

    @Column(length = 3, nullable = true, name = "schoolCd")
    private String schoolCd;
}
