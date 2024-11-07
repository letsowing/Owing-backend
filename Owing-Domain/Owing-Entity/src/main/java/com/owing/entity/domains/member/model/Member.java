package com.owing.entity.domains.member.model;

import com.owing.entity.common.model.BaseTimeEntity;
import com.owing.entity.domains.project.model.Project;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SoftDelete;

import java.util.ArrayList;
import java.util.List;

import static com.owing.entity.common.constant.OwingPersistenceConst.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SoftDelete
public class Member extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = EMAIL_LEN, nullable = false)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(length = NAME_LEN, nullable = false)
	private String name;

	@Column(length = NICKNAME_LEN, nullable = false)
	private String nickname;

	@Column(length = PHONE_NUM_LEN)
	private String phoneNumber;

	@Column(length = URL_LEN)
	private String profileUrl;

	@Column(length = OAUTH_PROVIDER_LEN)
	@Enumerated(EnumType.STRING)
	private OauthProvider provider;

	@Column(nullable = false, columnDefinition = "bigint default 0")
	private Long aiPoint;

	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Project> projectList = new ArrayList<>();

	@PrePersist
	protected void prePersist() {
		if (aiPoint == null) {
			aiPoint = 0L;
		}
	}

	@Builder
	public Member(Long id, String email, String password, String name, String nickname, String phoneNumber,
				  String profileUrl, OauthProvider provider) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.name = name;
		this.nickname = nickname;
		this.phoneNumber = phoneNumber;
		this.profileUrl = profileUrl;
		this.provider = provider;
		this.aiPoint = 0L;
	}
}
