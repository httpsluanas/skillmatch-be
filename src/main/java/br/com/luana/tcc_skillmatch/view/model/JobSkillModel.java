package br.com.luana.tcc_skillmatch.view.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JobSkillModel {
    private Integer id;
    private String name;
    private Integer relevance;

    public JobSkillModel(Integer id, String name, Integer relevance) {
        this.id = id;
        this.name = name;
        this.relevance = relevance;
    }
}
