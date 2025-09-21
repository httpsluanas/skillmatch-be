package br.com.luana.tcc_skillmatch.view;

import br.com.luana.tcc_skillmatch.service.JobService;
import br.com.luana.tcc_skillmatch.view.model.EmployeeModel;
import br.com.luana.tcc_skillmatch.view.model.JobModel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService service;

    @GetMapping("")
    public List<JobModel> findAll() {
        return service.findAll();
    }

    @PostMapping("")
    public JobModel addJob(@RequestBody JobModel job) {
        return service.saveJob(job);
    }

    @DeleteMapping("/{jobId}")
    public void removeJob(@PathVariable Integer jobId) {
        service.deleteJob(jobId);
    }

}
