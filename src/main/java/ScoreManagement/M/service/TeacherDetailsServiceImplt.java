package ScoreManagement.M.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ScoreManagement.M.model.TeacherModel;
import ScoreManagement.M.repository.TeacherRepository;


@Service

public class TeacherDetailsServiceImplt implements UserDetailsService {

	@Autowired

	private TeacherRepository teacherRepository; // ユーザモデルのRepository

	/**

	 * ユーザの検索を行う

	 */

	@Override

	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

		System.out.println("serach id : " + userId);

		TeacherModel user = this.teacherRepository.findByUserIdEquals(userId); 

		System.out.println(user.toString());

		return user;

	}

}