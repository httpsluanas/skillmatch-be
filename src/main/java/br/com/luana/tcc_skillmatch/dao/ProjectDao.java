package br.com.luana.tcc_skillmatch.dao;

import br.com.luana.tcc_skillmatch.dao.objects.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectDao extends JpaRepository<Project, Integer> {
    @Query("SELECT p FROM Project p LEFT JOIN FETCH p.projectSkills WHERE p.id = :id")
    Optional<Project> findByIdWithSkills(Integer id);
}
