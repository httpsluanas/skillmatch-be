package br.com.luana.tcc_skillmatch.view.converter;

import br.com.luana.tcc_skillmatch.dao.objects.Employee;
import br.com.luana.tcc_skillmatch.view.model.EmployeeModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class EmployeeConverter {

    private final SkillConverter skillConverter;

    public EmployeeModel convert(Employee entity) {
        if (isNull(entity)) return null;

        EmployeeModel.EmployeeModelBuilder builder = EmployeeModel.builder();
        builder.id(entity.getId());
        builder.name(entity.getName());
        builder.job(entity.getJob());
        builder.skills(entity.getSkills() != null
                ? entity.getSkills().stream()
                .map(skillConverter::convert)
                .collect(Collectors.toSet())
                : Set.of());

        return builder.build();
    }
}
