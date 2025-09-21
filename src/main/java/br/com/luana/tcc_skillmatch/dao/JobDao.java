package br.com.luana.tcc_skillmatch.dao;

import br.com.luana.tcc_skillmatch.dao.objects.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobDao extends JpaRepository<Job, Integer> {
}
