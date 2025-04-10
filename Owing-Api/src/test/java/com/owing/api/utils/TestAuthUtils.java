package com.owing.api.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.owing.common.util.JwtUtils;
import com.owing.entity.domains.member.model.Member;
import com.owing.entity.domains.member.model.OauthProvider;
import com.owing.entity.domains.member.repository.MemberRepository;
import com.owing.entity.domains.project.model.Category;
import com.owing.entity.domains.project.model.Project;
import com.owing.entity.domains.project.model.ProjectInfo;
import com.owing.entity.domains.project.repository.ProjectRepository;

@Component
public class TestAuthUtils {
	private static final String BEARER_TYPE_SPACE = "Bearer ";
	@Autowired
	private	JwtUtils jwtUtils;

	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private ProjectRepository projectRepository;

	public String getAccessToken(Member member) {
		return BEARER_TYPE_SPACE + jwtUtils.generateAccessToken(member);
	}

	public Member createMember(String memberName) {
		Member member = Member.builder()
			.email("test")
			.password("1234")
			.name(memberName)
			.nickname("nickname")
			.phoneNumber("")
			.provider(OauthProvider.GOOGLE)
			.build();
		return memberRepository.save(member);
	}

	public Project createProject(Member member) {
		Project project = Project.builder()
			.projectInfo(
				ProjectInfo.builder()
					.title("test")
					.description("test")
					.category(Category.OTHER)
					.coverUrl("test")
					.build()
			)
			.member(member)
			.build();
		return projectRepository.save(project);
	}

}
