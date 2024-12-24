package com.owing.entity.domains.ai.log.story.model;

import com.owing.core.BaseEntity;
import com.owing.entity.domains.story.model.Story;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SpellCheckLog extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(nullable = false)
    private List<SpellCheckOutput> output;

    @ManyToOne
    @JoinColumn(name = "story_id", nullable = false)
    private Story story;

    public SpellCheckLog(Story story, List<SpellCheckOutput> output) {
        this.story = story;
        this.output = output;
    }

}
