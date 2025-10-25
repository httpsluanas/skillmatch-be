package br.com.luana.tcc_skillmatch.view.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectRecommendationModel {
    private String projectName;
    List<RecommendationModel> recommendations;
}
