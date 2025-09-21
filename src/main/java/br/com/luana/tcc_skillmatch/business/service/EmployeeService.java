package br.com.luana.tcc_skillmatch.business.service;

import br.com.luana.tcc_skillmatch.dao.EmployeeDao;
import br.com.luana.tcc_skillmatch.dao.objects.Employee;
import br.com.luana.tcc_skillmatch.view.converter.EmployeeConverter;
import br.com.luana.tcc_skillmatch.view.model.EmployeeModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeDao dao;
    private final EmployeeConverter converter;

    public List<EmployeeModel> findAll() {
        List<Employee> employees = dao.findAll();
        return employees.stream().map(converter::convert).collect(Collectors.toList());
    }
}
