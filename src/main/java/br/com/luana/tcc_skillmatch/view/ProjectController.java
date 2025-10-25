package br.com.luana.tcc_skillmatch.view;

import br.com.luana.tcc_skillmatch.business.service.RecommendationService;
import br.com.luana.tcc_skillmatch.dao.enumerator.RecommendationType;
import br.com.luana.tcc_skillmatch.view.model.ProjectModel;
import br.com.luana.tcc_skillmatch.view.model.ProjectRecommendationModel;
import br.com.luana.tcc_skillmatch.view.model.RecommendationModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final br.com.luana.tcc_skillmatch.service.ProjectService service;
    private final RecommendationService recommendationService;

    @GetMapping("")
    public List<ProjectModel> findAll() {
        return service.findAll();
    }

    @PostMapping("")
    public ProjectModel addProject(@RequestBody ProjectModel project) {
        return service.saveProject(project);
    }

    @DeleteMapping("/{projectId}")
    public void removeProject(@PathVariable Integer projectId) {
        service.deleteProject(projectId);
    }

    @GetMapping("/recommendations/{projectId}")
    public ResponseEntity<ProjectRecommendationModel> getProjectRecommendations(@PathVariable Integer projectId, @RequestParam RecommendationType recommendationType) {
        ProjectRecommendationModel recommendation = recommendationService.getRecommendationsForProject(projectId, recommendationType);
        return ResponseEntity.ok(recommendation);
    }

}
