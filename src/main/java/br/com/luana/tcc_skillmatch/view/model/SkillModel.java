package br.com.luana.tcc_skillmatch.view.model;

import br.com.luana.tcc_skillmatch.dao.enumerator.SkillTypeEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SkillModel {
    private Integer id;
    private String name;
    private SkillTypeEnum type;
}