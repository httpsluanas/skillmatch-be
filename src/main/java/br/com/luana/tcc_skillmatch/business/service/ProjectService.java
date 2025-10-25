package br.com.luana.tcc_skillmatch.service;

import br.com.luana.tcc_skillmatch.business.service.SkillService;
import br.com.luana.tcc_skillmatch.dao.ProjectDao;
import br.com.luana.tcc_skillmatch.dao.SkillDao;
import br.com.luana.tcc_skillmatch.dao.objects.*;
import br.com.luana.tcc_skillmatch.view.converter.ProjectConverter;
import br.com.luana.tcc_skillmatch.view.model.ProjectModel;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectDao projectDao;
    private final SkillDao skillDao;
    private final ProjectConverter projectConverter;
    private final SkillService skillService;

    public List<ProjectModel> findAll() {
        List<Project> projects = projectDao.findAll();
        return projects.stream().map(projectConverter::convert).collect(Collectors.toList());
    }

    public ProjectModel saveProject(ProjectModel model) {
        Project project = new Project();
        project.setTitle(model.getTitle());
        project.setDescription(model.getDescription());

        if (model.getId() != null) {
            project = projectDao.findById(model.getId()).orElse(project);
        }

        final Project finalProject = project;

        Set<ProjectSkill> skills = model.getSkills().stream().map(s -> {
            ProjectSkillId projectSkillId = new ProjectSkillId();
            projectSkillId.setSkillId(s.getId());

            ProjectSkill projectSkill = new ProjectSkill();
            projectSkill.setId(projectSkillId);
            projectSkill.setProject(finalProject);

            Skill skill = skillDao.getReferenceById(s.getId());
            projectSkill.setSkill(skill);

            projectSkill.setRelevance(s.getRelevance());

            return projectSkill;
        }).collect(Collectors.toSet());

        project.getProjectSkills().clear();
        project.getProjectSkills().addAll(skills);

        Project savedProject = projectDao.save(project);
        return projectConverter.convert(savedProject);
    }

    public void deleteProject(Integer projectId) {
        if (!projectDao.existsById(projectId)) {
            throw new EntityNotFoundException("Vaga não encontrada com o ID: " + projectId);
        }

        projectDao.deleteById(projectId);
    }

}
