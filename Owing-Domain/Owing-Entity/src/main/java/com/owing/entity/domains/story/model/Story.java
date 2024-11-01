package com.owing.entity.domains.story.model;


import com.owing.entity.common.constant.OwingPersistenceConst;
import com.owing.entity.common.model.BaseTimeEntity;
import com.owing.entity.folders.story.model.StoryFolder;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SoftDelete;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SoftDelete
public class Story extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = OwingPersistenceConst.FILE_NAME_LEN, nullable = false)
	private String title;

	@Column(length = OwingPersistenceConst.DESC_LEN)
	private String description;

	@Column(columnDefinition = "int default 0", nullable = false)
	private Integer textCount;

	@Column(nullable = false)
	private Long position;

	@ManyToOne
	@JoinColumn(name = "story_folder_id", nullable = false)
	private StoryFolder storyFolder;

	@Builder
	Story(String title, String description, Long position, Integer textCount, StoryFolder storyFolder) {
		this.title = title;
		this.description = description;
		this.position = position;
		this.textCount = textCount;
		this.storyFolder = storyFolder;
	}

}
