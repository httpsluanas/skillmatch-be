package br.com.luana.tcc_skillmatch.dao;

import br.com.luana.tcc_skillmatch.dao.objects.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillDao extends JpaRepository<Skill, Integer> {
}
