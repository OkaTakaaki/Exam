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
	public StudentModel get(@NonNull Long index) {
		StudentModel studentmodel = this.repository.findById(index).orElse(new StudentModel());
		return studentmodel;
	}
	/**
	 * データ保存
	 */
	public void save(@NonNull StudentModel studentmodel) {
		this.repository.save(studentmodel);
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
	    public void update(StudentModel editstudent) {
			
	        // データベースに登録する値を保持するインスタンスの作成
	    	//Student student = new Student();
			
	        // 画面から受け取った値を設定する
	    	/*
	    	student.setId(editstudent.getId());
	    	student.setNAME(editstudent.getNAME());
	    	student.setCLASS_NUM(editstudent.getCLASS_NUM());
	    	*/
	        // データベースを更新する
	        repository.save(editstudent);
	    }
	
	/**
	 * データの削除
	 */
	public void delete(@NonNull Long index) {
		this.repository.deleteById(index);
	}
}