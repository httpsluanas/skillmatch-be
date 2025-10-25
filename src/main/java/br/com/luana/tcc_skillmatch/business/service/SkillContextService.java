package br.com.luana.tcc_skillmatch.business.service;

import br.com.luana.tcc_skillmatch.dao.EmployeeDao;
import br.com.luana.tcc_skillmatch.dao.objects.Employee;
import br.com.luana.tcc_skillmatch.dao.objects.Skill;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SkillContextService {

    private final EmployeeDao employeeDao;

    private Map<Integer, Map<Integer, Integer>> cooccurrenceMap;

    public SkillContextService(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    public Map<Integer, Map<Integer, Integer>> getCooccurrenceMap() {
        if (cooccurrenceMap == null) {
            calculateOccurrence();
        }
        return cooccurrenceMap;
    }

    //passa por todos os employees, encontra os pares de skills e conta quantas vezes cada par aparece
    private synchronized void calculateOccurrence() {
        if (cooccurrenceMap != null) {
            return;
        }

        Map<Integer, Map<Integer, Integer>> tempMap = new ConcurrentHashMap<>();
        List<Employee> employees = employeeDao.findAll();

        for (Employee employee : employees) {
            List<Skill> skills = new ArrayList<>(employee.getSkills());
            for (int i = 0; i < skills.size(); i++) {
                for (int j = i + 1; j < skills.size(); j++) {
                    Skill skill1 = skills.get(i);
                    Skill skill2 = skills.get(j);

                    tempMap.computeIfAbsent(skill1.getId(), k -> new HashMap<>()).merge(skill2.getId(), 1, Integer::sum);
                    tempMap.computeIfAbsent(skill2.getId(), k -> new HashMap<>()).merge(skill1.getId(), 1, Integer::sum);
                }
            }
        }
        this.cooccurrenceMap = tempMap;
    }
}