package kdt.dev.ecommerce.user.domain;

import static jakarta.persistence.GenerationType.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@Column(unique = true)
	private String oauthId;

	private String name;

	private String nickname;

	private User(
		String oauthId,
		String name,
		String nickname
	) {
		this.oauthId = oauthId;
		this.name = name;
		this.nickname = nickname;
	}

	//==Factory method==//
	public static User of(
		String oauthId,
		String name,
		String nickname
	) {
		return new User(oauthId, name, nickname);
	}
}
