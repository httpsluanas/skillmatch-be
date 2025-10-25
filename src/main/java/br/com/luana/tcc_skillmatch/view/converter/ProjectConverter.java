package br.com.luana.tcc_skillmatch.view.converter;

import br.com.luana.tcc_skillmatch.dao.objects.Project;
import br.com.luana.tcc_skillmatch.view.model.ProjectModel;
import br.com.luana.tcc_skillmatch.view.model.ProjectSkillModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProjectConverter {

    public ProjectModel convert(Project entity) {
        if (entity == null) return null;

        return ProjectModel.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .skills(entity.getProjectSkills().stream()
                        .map(js -> new ProjectSkillModel(js.getSkill().getId(), js.getSkill().getName(), js.getRelevance()))
                        .collect(Collectors.toSet()))
                .build();
    }
}
