package br.com.luana.tcc_skillmatch.view.model;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class EmployeeModel {
    private Integer id;
    private String name;
    private Set<SkillModel> skills;
}