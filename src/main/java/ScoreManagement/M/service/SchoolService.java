package ScoreManagement.M.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import ScoreManagement.M.model.SchoolModel;
import ScoreManagement.M.repository.SchoolRepository;
import jakarta.transaction.Transactional;
@Service
@Transactional
public class SchoolService {
	@Autowired
	private SchoolRepository repository;
	/**
	 * 一覧の取得
	 */
	public List<SchoolModel> getSchoolList() {
	    List<SchoolModel> entityList = this.repository.findAll();
	    return entityList;
	}
	/**
	 * 詳細データの取得
	 */
	public SchoolModel get(@NonNull Long index) {
		SchoolModel studentmodel = this.repository.findById(index).orElse(new SchoolModel());
		return studentmodel;
	}
	/**
	 * データ保存
	 */
	public void save(@NonNull SchoolModel schoolModel) {
		this.repository.save(schoolModel);
	}
	/**
	 * データの削除
	 */
	public void delete(@NonNull Long index) {
		this.repository.deleteById(index);
	}
	
}