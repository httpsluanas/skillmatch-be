package br.com.luana.tcc_skillmatch.view.converter;

import br.com.luana.tcc_skillmatch.dao.objects.Skill;
import br.com.luana.tcc_skillmatch.view.model.SkillModel;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class SkillConverter {

    public SkillModel convert(Skill entity) {
        if (isNull(entity)) return null;

        SkillModel.SkillModelBuilder builder = SkillModel.builder();
        builder.id(entity.getId());
        builder.name(entity.getName());
        builder.type(entity.getType());

        return builder.build();
    }
}
