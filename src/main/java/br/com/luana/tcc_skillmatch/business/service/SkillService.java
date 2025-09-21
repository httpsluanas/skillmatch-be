package br.com.luana.tcc_skillmatch.business.service;

import br.com.luana.tcc_skillmatch.dao.SkillDao;
import br.com.luana.tcc_skillmatch.dao.objects.Skill;
import br.com.luana.tcc_skillmatch.view.converter.SkillConverter;
import br.com.luana.tcc_skillmatch.view.model.SkillModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SkillService {
    private final SkillDao dao;
    private final SkillConverter converter;

    public List<SkillModel> findAll() {
        List<Skill> skills = dao.findAll();
        return skills.stream().map(converter::convert).collect(Collectors.toList());
    }

    public Skill getSkillById(Integer id) {
        return dao.findById(id)
                .orElseThrow(() -> new RuntimeException("Skill não encontrada com id: " + id));
    }
}
