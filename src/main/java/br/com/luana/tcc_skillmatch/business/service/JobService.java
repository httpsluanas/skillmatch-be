package br.com.luana.tcc_skillmatch.service;

import br.com.luana.tcc_skillmatch.business.service.SkillService;
import br.com.luana.tcc_skillmatch.dao.JobDao;
import br.com.luana.tcc_skillmatch.dao.SkillDao;
import br.com.luana.tcc_skillmatch.dao.objects.*;
import br.com.luana.tcc_skillmatch.view.converter.JobConverter;
import br.com.luana.tcc_skillmatch.view.model.JobModel;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobDao jobDao;
    private final SkillDao skillDao;
    private final JobConverter jobConverter;
    private final SkillService skillService;

    public List<JobModel> findAll() {
        List<Job> jobs = jobDao.findAll();
        return jobs.stream().map(jobConverter::convert).collect(Collectors.toList());
    }

    public JobModel saveJob(JobModel model) {
        Job job = new Job();
        job.setTitle(model.getTitle());
        job.setDescription(model.getDescription());

        if (model.getId() != null) {
            job = jobDao.findById(model.getId()).orElse(job);
        }

        final Job finalJob = job;

        Set<JobSkill> skills = model.getSkills().stream().map(s -> {
            JobSkillId jobSkillId = new JobSkillId();
            jobSkillId.setSkillId(s.getId());

            JobSkill jobSkill = new JobSkill();
            jobSkill.setId(jobSkillId);
            jobSkill.setJob(finalJob);

            Skill skill = skillDao.getReferenceById(s.getId());
            jobSkill.setSkill(skill);

            jobSkill.setRelevance(s.getRelevance());

            return jobSkill;
        }).collect(Collectors.toSet());

        job.getJobSkills().clear();
        job.getJobSkills().addAll(skills);

        Job savedJob = jobDao.save(job);
        return jobConverter.convert(savedJob);
    }

    public void deleteJob(Integer jobId) {
        if (!jobDao.existsById(jobId)) {
            throw new EntityNotFoundException("Vaga não encontrada com o ID: " + jobId);
        }

        jobDao.deleteById(jobId);
    }

}
