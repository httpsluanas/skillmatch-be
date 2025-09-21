package br.com.luana.tcc_skillmatch.view;

import br.com.luana.tcc_skillmatch.business.service.SkillService;
import br.com.luana.tcc_skillmatch.dao.objects.Skill;
import br.com.luana.tcc_skillmatch.view.model.SkillModel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/skills")
@RequiredArgsConstructor
public class SkillController {
    private final SkillService skillService;

    @GetMapping("")
    public List<SkillModel> findAll() {
        return skillService.findAll();
    }
}
