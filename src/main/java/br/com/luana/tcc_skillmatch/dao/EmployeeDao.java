package br.com.luana.tcc_skillmatch.dao;

import br.com.luana.tcc_skillmatch.dao.objects.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeDao extends JpaRepository<Employee, Integer> {
}
