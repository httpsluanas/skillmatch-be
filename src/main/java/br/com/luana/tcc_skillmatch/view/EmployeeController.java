package br.com.luana.tcc_skillmatch.view;

import br.com.luana.tcc_skillmatch.business.service.EmployeeService;
import br.com.luana.tcc_skillmatch.view.model.EmployeeModel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService service;

    @GetMapping("")
    public List<EmployeeModel> findAll() {
        return service.findAll();
    }
}
