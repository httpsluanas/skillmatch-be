package br.com.luana.tcc_skillmatch.business.service;

import br.com.luana.tcc_skillmatch.dao.EmployeeDao;
import br.com.luana.tcc_skillmatch.dao.ProjectDao;
import br.com.luana.tcc_skillmatch.dao.enumerator.RecommendationType;
import br.com.luana.tcc_skillmatch.dao.objects.Employee;
import br.com.luana.tcc_skillmatch.dao.objects.Project;
import br.com.luana.tcc_skillmatch.dao.objects.ProjectSkill;
import br.com.luana.tcc_skillmatch.dao.objects.Skill;
import br.com.luana.tcc_skillmatch.view.converter.EmployeeConverter;
import br.com.luana.tcc_skillmatch.view.model.ProjectRecommendationModel;
import br.com.luana.tcc_skillmatch.view.model.RecommendationModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final ProjectDao projectDao;
    private final EmployeeDao employeeDao;
    private final EmployeeConverter employeeConverter;
    private final SkillContextService skillContextService;

    private static final int RELEVANCE_MULTIPLIER = 5; // <--- FATOR DE PESO

    // MÉTODO PRINCIPAL ATUALIZADO
    public ProjectRecommendationModel getRecommendationsForProject(Integer projectId, RecommendationType recommendationType) {
        Optional<Project> projectOptional = projectDao.findByIdWithSkills(projectId);
        if (projectOptional.isEmpty() || projectOptional.get().getProjectSkills().isEmpty()) {
            return null;
        }

        List<Employee> allEmployees = employeeDao.findAll();
        List<RecommendationModel> recommendations = new ArrayList<>();
        Set<ProjectSkill> requiredProjectSkills = projectOptional.get().getProjectSkills();

        // Lógica de Proximidade precisa destes dados pré-calculados
        Map<Integer, Map<Integer, Integer>> cooccurrenceMap = null;
        Set<Integer> requiredSkillIds = null;
        if (recommendationType.equals(RecommendationType.PROXIMITY)) {
            cooccurrenceMap = skillContextService.getCooccurrenceMap();
            requiredSkillIds = requiredProjectSkills.stream()
                    .map(projectSkill -> projectSkill.getSkill().getId())
                    .collect(Collectors.toSet());
        }

        for (Employee employee : allEmployees) {
            // 1. Calcula o componente de relevância (sempre necessário)
            ScoreComponent relevanceComponent = calculateRelevanceComponent(employee, requiredProjectSkills);

            int finalScore = 0;
            String justification = "";

            if (recommendationType.equals(RecommendationType.RELEVANCE)) {
                finalScore = relevanceComponent.score();
                justification = buildJustification(relevanceComponent.contributingSkills(), Collections.emptyList());

            } else if (recommendationType.equals(RecommendationType.PROXIMITY)) {
                // 2. Calcula o componente de contexto (apenas para PROXIMITY)
                ScoreComponent contextComponent = calculateContextComponent(employee, requiredSkillIds, cooccurrenceMap);

                finalScore = (relevanceComponent.score() * RELEVANCE_MULTIPLIER) + contextComponent.score();
                justification = buildJustification(relevanceComponent.contributingSkills(), contextComponent.contributingSkills());
            }

            if (finalScore > 0) {
                recommendations.add(new RecommendationModel(
                        employeeConverter.convert(employee),
                        finalScore,
                        justification // A justificativa agora é adicionada em ambos os casos!
                ));
            }
        }

        recommendations.sort(Comparator.comparingInt(RecommendationModel::getScore).reversed());
        ProjectRecommendationModel projectRecommendationModel = new ProjectRecommendationModel();
        projectRecommendationModel.setProjectName(projectOptional.get().getTitle());
        projectRecommendationModel.setRecommendations(recommendations);

        return projectRecommendationModel;
    }

    private record ScoreComponent(int score, List<String> contributingSkills) {}

    // MÉTODO 1: Calcula a pontuação de RELEVÂNCIA e retorna as skills
    private ScoreComponent calculateRelevanceComponent(Employee employee, Set<ProjectSkill> requiredProjectSkills) {
        int relevanceScore = 0;
        List<String> matchingSkills = new ArrayList<>();

        Set<Integer> employeeSkillIds = employee.getSkills().stream()
                .map(Skill::getId).collect(Collectors.toSet());

        for (ProjectSkill requiredSkill : requiredProjectSkills) {
            if (employeeSkillIds.contains(requiredSkill.getSkill().getId())) {
                relevanceScore += requiredSkill.getRelevance();
                matchingSkills.add(requiredSkill.getSkill().getName());
            }
        }
        return new ScoreComponent(relevanceScore, matchingSkills);
    }

    // MÉTODO 2: Calcula a pontuação de CONTEXTO e retorna as skills
    private ScoreComponent calculateContextComponent(Employee employee, Set<Integer> requiredSkillIds, Map<Integer, Map<Integer, Integer>> cooccurrenceMap) {
        Map<String, Integer> contextualSkillScores = new HashMap<>();

        for (Skill employeeSkill : employee.getSkills()) {
            if (!requiredSkillIds.contains(employeeSkill.getId())) {
                int skillContribution = 0;
                for (Integer requiredId : requiredSkillIds) {
                    skillContribution += cooccurrenceMap
                            .getOrDefault(requiredId, Collections.emptyMap())
                            .getOrDefault(employeeSkill.getId(), 0);
                }
                if (skillContribution > 0) {
                    contextualSkillScores.merge(employeeSkill.getName(), skillContribution, Integer::sum);
                }
            }
        }

        int totalContextScore = contextualSkillScores.values().stream().mapToInt(Integer::intValue).sum();

        List<String> topContextualSkills = contextualSkillScores.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(3)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        return new ScoreComponent(totalContextScore, topContextualSkills);
    }

    // MÉTODO 3: Monta a STRING de justificativa
    private String buildJustification(List<String> matchingSkills, List<String> contextualSkills) {
        StringBuilder justificationBuilder = new StringBuilder();
        if (!matchingSkills.isEmpty()) {
            justificationBuilder.append("Atende aos requisitos: ")
                    .append(String.join(", ", matchingSkills))
                    .append(". ");
        }
        if (!contextualSkills.isEmpty()) {
            justificationBuilder.append("Possui habilidades relacionadas relevantes: ")
                    .append(String.join(", ", contextualSkills))
                    .append(".");
        }
        return justificationBuilder.toString().trim();
    }
}