package ScoreManagement.M.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import ScoreManagement.M.model.StudentModel;
import ScoreManagement.M.model.TeacherModel;
import ScoreManagement.M.model.TestModel;
import ScoreManagement.M.repository.StudentRepository;
import ScoreManagement.M.repository.SubjectRepository;
import ScoreManagement.M.repository.TeacherRepository;
import ScoreManagement.M.repository.TestRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class TestService {
	@Autowired
	private TestRepository testrepository;
	@Autowired
	private TeacherRepository teacherRepository;
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private SubjectRepository subjectRepository;
	/**
	 * アドレス帳一覧の取得
	 * @return List<Test>
	 */
	public List<StudentModel> getTestList() {
	    List<StudentModel> entityList = this.studentRepository.findAll();
	    return entityList;
	}
	public List<StudentModel> getResTestList(UserDetails user) {
        // ユーザーのユーザー名に対応する教師情報をデータベースから取得する
        TeacherModel teachers = this.teacherRepository.findByUserIdEquals(user.getUsername());
        // 教師の所属する学校コードに関連する学生エンティティのリストを取得する
        List<StudentModel> entityList = this.studentRepository.findBySchoolCd(teachers.getSchoolCd());
        // 学生エンティティのリストを返す
        return entityList;
    }
	
	

	
	/**
	 * 詳細データの取得
	 * @param @NonNull Long index
	 * @return  TEST
	 */
	public TestModel get(@NonNull Long index) {
		TestModel test = this.testrepository.findById(index).orElse(new TestModel());
		return test;
	}
	

	/**
	 * アドレス帳一覧の取得
	 * @param TEST TEST
	 */
	public void save(TestModel test , UserDetails user) {
	    TeacherModel teacher = this.teacherRepository.findByUserIdEquals(user.getUsername());
	    test.setSchoolCd(teacher.getSchoolCd());
	    this.testrepository.save(test);
	}
	


	/**
	 * アドレス帳データの削除
	 * @param @NonNull Long index
	 */
	public void delete(@NonNull Long index) {
		this.testrepository.deleteById(index);
	}
	// 受け取ったidからデータを取得して、Formを返却する
    public TestModel getOneBook(Long id) {
        // idを指定して本の情報を取得する
    	TestModel test = testrepository.findById(id).orElseThrow();
        return test;
    }
// 本を更新する
    public void update(TestModel edittest) {
        // データベースに登録する値を保持するインスタンスの作成
    	//Student student = new Student();
        // 画面から受け取った値を設定する
    	/*
    	student.setId(editstudent.getId());
    	student.setNAME(editstudent.getNAME());
    	student.setCLASS_NUM(editstudent.getCLASS_NUM());
    	*/
        // データベースを更新する
        testrepository.save(edittest);
    }
    public List<StudentModel> searchTests(Integer entYear, String classNum, String schoolCd) {
    	List<StudentModel> students = studentRepository.findAll();
        // 入学年度で絞り込み
        if (entYear != null) {
            students = studentRepository.findByEntYear(entYear);
        }
        // クラス番号で絞り込み
        if (classNum != null && !classNum.isEmpty()) {
            List<StudentModel> classNumStudents = studentRepository.findByClassNum(classNum);
            students.retainAll(classNumStudents);
        }
        List<StudentModel> schoolCdStudents = studentRepository.findBySchoolCd(schoolCd);
        students.retainAll(schoolCdStudents);
        return students;
     }
    
    
    public List<TestModel> searchReferences(Integer entYear, String classNum, String subjectCd) {
    	List<TestModel> references = testrepository.findAll();
    	System.out.println("!!" + references);
    	
    	List<StudentModel> studententYear = StudententYear(entYear);
        System.out.println("studententYear" + entYear + "|" + studententYear);
        
        List<String> studentNos = new ArrayList<>(); //数値を格納するリストを作成
        for (StudentModel studentModel : studententYear) {
        	studentNos.add(studentModel.getNo()); // 各 TestModel オブジェクトから SubjectCd の数値を取得し、リストに追加
        }
        System.out.println("StudentModelのstudentNo =" + studentNos);
        System.out.println("-----------------");
    	
    	// entyearで絞り込み
        if (studentNos != null && !studentNos.isEmpty()) {
            List<TestModel> entyearTests = testrepository.findByNoIn(studentNos);
            references.retainAll(entyearTests);
            System.out.println("!" + references);
        }
    	 
        // クラス番号で絞り込み
        if (classNum != null && !classNum.isEmpty()) {
            List<TestModel> classNumTests = testrepository.findByClassNum(classNum);
            references.retainAll(classNumTests);
        }
 
        // 科目で絞り込み
        if (subjectCd != null && !subjectCd.isEmpty()) {
            List<TestModel> subjectCdTests = testrepository.findBySubjectCd(subjectCd);
            references.retainAll(subjectCdTests);
        }

        return references;
     }
    
    public List<StudentModel> StudententYear(Integer entYear) {
        // 全ての学生を取得
        List<StudentModel> studententYears = studentRepository.findAll();
        
        // 入学年度で絞り込み
        if (entYear != null) {
        	studententYears = studentRepository.findByEntYear(entYear);  
        }
        return studententYears;
        }
    
    
    public List<TestModel> searchStudentNos(String studentNo) {
    	List<TestModel> references = testrepository.findAll();
 
        // 学生番号で絞り込み
        if (studentNo != null && !studentNo.isEmpty()) {
            List<TestModel> studentNoTests = testrepository.findByStudentNo(studentNo);
            references.retainAll(studentNoTests);
        }
        return references;
     }
    
}