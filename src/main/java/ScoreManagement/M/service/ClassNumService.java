package ScoreManagement.M.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ScoreManagement.M.model.ClassNumModel;
import ScoreManagement.M.repository.ClassNumRepository;


@Service
public class ClassNumService {

    @Autowired
    private ClassNumRepository classNumRepository;

    // クラス番号を登録するメソッド
    public ClassNumModel registerClassNum(ClassNumModel ClassNumModel) {
        return classNumRepository.save(ClassNumModel);
    }

    // クラス番号を更新するメソッド
    public ClassNumModel updateClassNum(ClassNumModel ClassNumModel) {
        return classNumRepository.save(ClassNumModel);
    }


    // 全てのクラス番号を取得するメソッド
    public List<ClassNumModel> getAllClassNums() {
        return classNumRepository.findAll();
    }
    
    // 学校コードに対応するクラス番号を取得
    public List<ClassNumModel> getClassNumsBySchoolCd(String schoolCd) {
        return classNumRepository.findBySchoolCd(schoolCd);
    }
    
}


