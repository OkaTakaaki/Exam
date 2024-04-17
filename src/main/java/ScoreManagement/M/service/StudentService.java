package ScoreManagement.M.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import ScoreManagement.M.model.StudentModel;
import ScoreManagement.M.repository.StudentRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class StudentService {

    @Autowired
    private StudentRepository repository;

    /**
     * 一覧の取得
     */
    public List<StudentModel> getStudentList() {
        List<StudentModel> entityList = this.repository.findAll();
        return entityList;
    }

    /**
     * 詳細データの取得
     */
    public StudentModel get(@NonNull Long no) {
        StudentModel student = this.repository.findById(no).orElse(new StudentModel());
        return student;
    }

    /**
     * データ保存
     */
    public void save(@NonNull StudentModel student) {
        student.setIsAttend(true);
        this.repository.save(student);
    }

    // 受け取ったidからデータを取得して、Formを返却する
    public StudentModel getOneBook(Long id) {
        // idを指定して本の情報を取得する
        StudentModel student = repository.findById(id).orElseThrow();

        // 画面返却用のFormに値を設定する
        /*
        Student editstudent = new Student();
        editstudent.setId(student.getId());
        editstudent.setNAME(student.getNAME());
        editstudent.setCLASS_NUM(student.getCLASS_NUM());
        */

        return student;
    }

    // 本を更新する
    public void update(StudentModel student) {
        // データベースを更新する
        repository.save(student);
    }

    /**
     * データの削除
     */
    public void delete(@NonNull Long no) {
        this.repository.deleteById(no);
    }
    
    
    public List<StudentModel> filterStudents(Integer entYear, String classNum, Boolean isAttend) {
        List<StudentModel> students = repository.findAll();
 
        // 入学年度で絞り込み
        if (entYear != null) {
            students = repository.findByEntYear(entYear);
        }
 
        // クラス番号で絞り込み
        if (classNum != null && !classNum.isEmpty()) {
            List<StudentModel> classNumStudents = repository.findByClassNum(classNum);
            students.retainAll(classNumStudents);
        }
 
        // 在学状況で絞り込み
        if (isAttend != null) {
            List<StudentModel> isAttendStudents = repository.findByIsAttend(isAttend);
            students.retainAll(isAttendStudents);
        }
 
        return students;
    }
}
