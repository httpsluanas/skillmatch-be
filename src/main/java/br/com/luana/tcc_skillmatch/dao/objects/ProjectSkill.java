package br.com.luana.tcc_skillmatch.dao.objects;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Getter
@Setter
public class ProjectSkill {
    @EmbeddedId
    private ProjectSkillId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("projectId")
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("skillId")
    @JoinColumn(name = "skill_id")
    private Skill skill;

    private Integer relevance;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectSkill projectSkill = (ProjectSkill) o;
        return Objects.equals(id, projectSkill.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}