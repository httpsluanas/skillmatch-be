package br.com.luana.tcc_skillmatch.view.model;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class JobModel {
    private Integer id;
    private String title;
    private String description;
    private Set<JobSkillModel> skills;
}
