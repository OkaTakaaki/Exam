package ScoreManagement.M.model;


import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data

@Entity

// @Dataと@Entityがあるときはゲッター、セッターは書かなくていい

@Table(name="TEACHER")

public class TeacherModel implements UserDetails {

	@Id

	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private long id;

	@Column(length = 10, nullable = false, name = "userId")

	private String userId;

	@Column(length = 120, nullable = false, name = "password")

	private String password;
	
	@Column(length = 10, nullable = false, name = "name")

	private String name;
	
	@Column(length = 3, nullable = false, name = "schoolCd")

	private String schoolCd;

	/**

	 * 権限を返す

	 */

	@Override

	public Collection<? extends GrantedAuthority> getAuthorities() {

		Collection<GrantedAuthority> authorityList = new ArrayList<>();

		return authorityList;

	}

	/**

	 * ログイン時に使用するユーザ名を返す

	 * @return メールアドレス

	 */

	@Override
	public String getUsername() {
	    // ユーザー名を返す
	    return this.userId;
	}

	/**
	 * 教師が所属する学校の学校コードを取得するメソッド
	 * 
	 * @return 学校コード
	 */
	public String getSchoolCd() {
	    // 教師が所属する学校の学校コードを返す
	    return this.schoolCd;
	}


	/**

	 * 有効期限のチェック

	 * @return true:有効/false:無効

	 */

	@Override

	public boolean isAccountNonExpired() {

		return true;

	}

	/**

	 * アカウントのロック状態

	 * @return true:有効/false:無効

	 */

	@Override

	public boolean isAccountNonLocked() {

		return true;

	}

	/**

	 * アカウントの認証情報の有効期限

	 * @return true:有効/false:無効

	 */

	@Override

	public boolean isCredentialsNonExpired() {

		return true;

	}

	@Override

	public boolean isEnabled() {

		return true;

	}

}