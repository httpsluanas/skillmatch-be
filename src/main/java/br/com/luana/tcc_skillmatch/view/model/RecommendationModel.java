package br.com.luana.tcc_skillmatch.view.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationModel {
    private EmployeeModel employee;
    private int score;
    private String justification;
}
