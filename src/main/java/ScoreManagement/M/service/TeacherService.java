package ScoreManagement.M.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ScoreManagement.M.model.TeacherModel;
import ScoreManagement.M.repository.TeacherRepository;
import jakarta.transaction.Transactional;
@Service

@Transactional

public class TeacherService {

	@Autowired

	private TeacherRepository repository;

	@Autowired

	private PasswordEncoder passwordEncoder;

	/**ユーザー一覧の取得**/

	public List<TeacherModel> getUserList() {
	    List<TeacherModel> entityList = this.repository.findAll();
	    return entityList;

	}

	/**

	 * 詳細データの取得

	 * @param @NonNull Long index

	 * @return  

	 */

	public TeacherModel get(@NonNull Long index) {

		TeacherModel teacherModel = this.repository.findById(index).orElse(new TeacherModel());

		return teacherModel;

	}

	/**

	 * ユーザーデータの保存

	 * @param TeacherModel teacherModel

	 */

	public void save(@NonNull TeacherModel teacherModel) {

		teacherModel.setPassword(this.passwordEncoder.encode(teacherModel.getPassword()));

		this.repository.save(teacherModel);

	}

	/**

	 * ユーザーデータの削除

	 * @param @NonNull Long index

	 */

	public void delete(@NonNull Long index) {

		this.repository.deleteById(index);

	}

}