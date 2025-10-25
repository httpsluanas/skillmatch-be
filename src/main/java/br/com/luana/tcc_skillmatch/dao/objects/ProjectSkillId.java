package br.com.luana.tcc_skillmatch.dao.objects;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@RequiredArgsConstructor
public class ProjectSkillId implements Serializable {
    private Integer projectId;
    private Integer skillId;
}
