package ScoreManagement.M.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import ScoreManagement.M.model.SubjectModel;
import ScoreManagement.M.model.TeacherModel;
import ScoreManagement.M.repository.SubjectRepository;
import ScoreManagement.M.repository.TeacherRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private TeacherRepository repositoryTeacher;

    /**
     * 一覧の取得
     */
    public List<SubjectModel> getSubjectList() {
        List<SubjectModel> entityList = this.subjectRepository.findAll();
        return entityList;
    }
    
    public List<SubjectModel> getResSubjectList(UserDetails user) {
        // ユーザーのユーザー名に対応する教師情報をデータベースから取得する
        TeacherModel teachers = this.repositoryTeacher.findByUserIdEquals(user.getUsername());
        // 教師の所属する学校コードに関連する学生エンティティのリストを取得する
        List<SubjectModel> entityList = this.subjectRepository.findBySchoolCd(teachers.getSchoolCd());
        // 学生エンティティのリストを返す
        return entityList;
    }


    /**
     * 詳細データの取得
     */
    public SubjectModel get(@NonNull Long no) {
        SubjectModel subject = this.subjectRepository.findById(no).orElse(new SubjectModel());
        return subject;
    }
    
    public List<SubjectModel> getAll() {
		return this.subjectRepository.findAll();
	}

    /**
     * データ保存
     */
    public void save(@NonNull SubjectModel subject,UserDetails user) {
    	TeacherModel teachers = this.repositoryTeacher.findByUserIdEquals(user.getUsername());
        // 教師の所属する学校コードに関連する学生エンティティのリストを取得する
       subject.setSchoolCd(teachers.getSchoolCd());
        this.subjectRepository.save(subject);
    }

    // 受け取ったidからデータを取得して、Formを返却する
    public SubjectModel getOneBook(Long id) {
        // idを指定して本の情報を取得する
        SubjectModel subject = subjectRepository.findById(id).orElseThrow();

        // 画面返却用のFormに値を設定する
        /*
        Subject editSubject = new Subject();
        editSubject.setId(subject.getId());
        editSubject.setName(subject.getName());
        editSubject.setClassNum(subject.getClassNum());
        */

        return subject;
    }

    // 本を更新する
    public void update(SubjectModel subject) {
        // データベースを更新する
    	subjectRepository.save(subject);
    }

    /**
     * データの削除
     */
    public void delete(@NonNull Long no) {
        this.subjectRepository.deleteById(no);
    }
    
    public List<SubjectModel> getSubjectsBySchoolCd(String schoolCd) {
        return subjectRepository.findBySchoolCd(schoolCd);
    }

	public SubjectModel getSubjectsBySubjectCd(String subjectCd) {
		return subjectRepository.findByCd(subjectCd);
	}
}

