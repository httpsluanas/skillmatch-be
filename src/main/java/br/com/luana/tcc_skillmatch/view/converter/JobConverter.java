package br.com.luana.tcc_skillmatch.view.converter;

import br.com.luana.tcc_skillmatch.dao.objects.Job;
import br.com.luana.tcc_skillmatch.view.model.JobModel;
import br.com.luana.tcc_skillmatch.view.model.JobSkillModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JobConverter {

    public JobModel convert(Job entity) {
        if (entity == null) return null;

        return JobModel.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .skills(entity.getJobSkills().stream()
                        .map(js -> new JobSkillModel(js.getSkill().getId(), js.getSkill().getName(), js.getRelevance()))
                        .collect(Collectors.toSet()))
                .build();
    }
}
