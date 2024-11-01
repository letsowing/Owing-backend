package com.owing.entity.domains.member.model;

import com.owing.entity.common.constant.OwingPersistenceConst;
import com.owing.entity.common.model.BaseTimeEntity;
import com.owing.entity.domains.project.model.Project;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SoftDelete;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SoftDelete
public class Member extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = OwingPersistenceConst.EMAIL_LEN, nullable = false)
	private String email;

	@Column(length = OwingPersistenceConst.PASSWORD_LEN, nullable = false)
	private String password;

	@Column(length = OwingPersistenceConst.NAME_LEN, nullable = false)
	private String name;

	@Column(length = OwingPersistenceConst.NICKNAME_LEN, nullable = false)
	private String nickname;

	@Column(length = OwingPersistenceConst.PHONE_NUM_LEN, nullable = false)
	private String phoneNumber;

	@Column(length = OwingPersistenceConst.URL_LEN)
	private String profileUrl;

	@Column(length = OwingPersistenceConst.OAUTH_PROVIDER_LEN)
	private OauthProvider provider;

	@Column(nullable = false, columnDefinition = "bigint default 0")
	private Long aiPoint;

	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Project> projectList = new ArrayList<>();

	@PrePersist
	protected void prePersist() {
		if (aiPoint == null) {
			aiPoint = 0L;
		}
	}

}
