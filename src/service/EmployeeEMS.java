package src.service;

import src.model.abstracts.Employee;
import src.model.concret.Manager;
import src.seed.CompanySeeder;
import src.seed.SkillSeeder;
import src.util.GeneralHelper;

import java.util.*;

public class EmployeeEMS {

    public void execute() {

        Company<Employee> company = new Company<>();

        CompanySeeder.seed(company);

        // departmentId -> list
        Map<String, List<Employee>> employeesGroupByDepartment = new HashMap<>();
        Map<String, List<Employee>> employeesGroupByDepartmentTree = new TreeMap<>();

        employeesGroupByDepartment = getEmployeesGroupByDepartment(company);
        employeesGroupByDepartmentTree = getEmployeesGroupByDepartment(company);

        Set<String> skills = new TreeSet<>();
        SkillSeeder.seed(skills);

        Comparator<Employee> comparatorByOfferDate = Comparator.comparing(Employee::getOffer_date);
        Comparator<Employee> comparatorByName = Comparator.comparing(Employee::getName);

        Collections.sort(company.getEmployees(), comparatorByOfferDate);
        Collections.sort(company.getEmployees(), comparatorByName);

        GeneralHelper.printEmployees(company.getEmployees());

    }

    public Map<String, List<Employee>> getEmployeesGroupByDepartment(Company<Employee> company) {
        Map<String, List<Employee>> employeesGroupByDepartment = new HashMap<>();

        for (Manager manager : company.getManagers()) {
            String departmentId = manager.getDepartment();
            employeesGroupByDepartment.computeIfAbsent(departmentId, k -> new ArrayList<>()).add(manager);
        }

        return employeesGroupByDepartment;
    }

}
