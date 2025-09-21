package br.com.luana.tcc_skillmatch.dao.objects;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@RequiredArgsConstructor
public class JobSkillId implements Serializable {
    private Integer jobId;
    private Integer skillId;
}
