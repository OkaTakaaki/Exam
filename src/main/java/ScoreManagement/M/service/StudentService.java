package ScoreManagement.M.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import ScoreManagement.M.model.StudentModel;
import ScoreManagement.M.model.TeacherModel;
import ScoreManagement.M.repository.StudentRepository;
import ScoreManagement.M.repository.TeacherRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class StudentService {

    @Autowired
    private StudentRepository repository;
    @Autowired
    private TeacherRepository repositoryTeacher;

    @Autowired
    private TeacherRepository repositoryClassNum;

    /**
     * 一覧の取得
     */
    public List<StudentModel> getStudentList() {
        List<StudentModel> entityList = this.repository.findAll();
        return entityList;
    }
    
    /**
     * ユーザーに関連する学生のリストを取得するメソッド
     * 
     * @param user ユーザーの詳細情報
     * @return ユーザーに関連する学生のリスト
     */
    public List<StudentModel> getResStudentList(UserDetails user) {
        // ユーザーのユーザー名に対応する教師情報をデータベースから取得する
        TeacherModel teachers = this.repositoryTeacher.findByUserIdEquals(user.getUsername());
        // 教師の所属する学校コードに関連する学生エンティティのリストを取得する
        List<StudentModel> entityList = this.repository.findBySchoolCd(teachers.getSchoolCd());
        // 学生エンティティのリストを返す
        return entityList;
    }


    /**
     * 詳細データの取得
     */
    public StudentModel get(@NonNull Long no) {
        StudentModel student = this.repository.findById(no).orElse(new StudentModel());
        return student;
    }
    
    public List<StudentModel> getAll() {
		return this.repository.findAll();
	}

    /**
     * データ保存
     */
    public void save(@NonNull StudentModel student, UserDetails user) {
        student.setIsAttend(true);
        TeacherModel teachers = this.repositoryTeacher.findByUserIdEquals(user.getUsername());
        // 教師の所属する学校コードに関連する学生エンティティのリストを取得する
        student.setSchoolCd(teachers.getSchoolCd());
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
    
    
    /**
     * 学生を絞り込むメソッド
     * 
     * @param entYear 入学年度
     * @param classNum クラス番号
     * @param isAttend 在学状況
     * @return 絞り込まれた学生のリスト
     */
    public List<StudentModel> filterStudents(Integer entYear, String classNum, Boolean isAttend,String schoolCd) {
        // 全ての学生を取得
        List<StudentModel> students = repository.findAll();
        

        // 入学年度で絞り込み
        if (entYear != null) {
            students = repository.findByEntYear(entYear);
            
        }

        // クラス番号で絞り込み
        if (classNum != null && !classNum.isEmpty()) {
            // 指定されたクラス番号に該当する学生を取得
            List<StudentModel> classNumStudents = repository.findByClassNum(classNum);
            // 絞り込まれた学生リストと共通する学生のみを残す
            students.retainAll(classNumStudents);
        }

        // 在学状況で絞り込み
        if (isAttend != null) {
            // 在学状況に該当する学生を取得
            List<StudentModel> isAttendStudents = repository.findByIsAttend(isAttend);
            // 絞り込まれた学生リストと共通する学生のみを残す
            students.retainAll(isAttendStudents);
        }
        List<StudentModel> schoolCdStudents = repository.findBySchoolCd(schoolCd);
        // 絞り込まれた学生リストと共通する学生のみを残す
        students.retainAll(schoolCdStudents);

        return students;
    }

}
