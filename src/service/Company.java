package src.service;

import src.model.abstracts.Employee;
import src.model.concret.Developer;
import src.model.concret.Intern;
import src.model.concret.Manager;

import java.util.*;

public class Company<T extends Employee> {

    private final List<T> employees;

    public Company() {
        this.employees = new ArrayList<>();
    }

    public Company(List<T> employees) {
        this.employees = new ArrayList<>(employees);
    }

    public void add(T employee) {
        employees.add(employee);
    }

    public Optional<T> findById(Integer id) {
        for (T employee : employees) {
            if (employee.getId().equals(id)) {
                return Optional.of(employee);
            }
        }
        return Optional.empty();
    }

    public List<T> getAll() {
        return Collections.unmodifiableList(employees);
    }

    public List<T> getEmployees() {
        return employees;
    }

    public Map<String, List<T>> groupByDepartment() {
        Map<String, List<T>> map = new HashMap<>();

        for (T employee : employees) {
            if (employee instanceof Manager mgr) {
                map.computeIfAbsent(mgr.getDepartment(), k -> new ArrayList<>()).add(employee);
            }
        }

        return map;
    }

    public Set<String> getAllSkills() {
        Set<String> skills = new HashSet<>();

        for (T employee : employees) {
            if (employee instanceof Developer dev) {
                skills.add(dev.getProgrammingLanguage());
            }
        }

        return skills;
    }

    public List<Manager> getManagers() {
        return getEmployeesByType(Manager.class);
    }

    public List<Developer> getDevelopers() {
        return getEmployeesByType(Developer.class);
    }

    public List<Intern> getInterns() {
        return getEmployeesByType(Intern.class);
    }

    private <E> List<E> getEmployeesByType(Class<E> type) {
        List<E> result = new ArrayList<>();

        for (T employee : employees) {
            if (type.isInstance(employee)) {
                result.add((E) employee);
            }
        }

        return result;
    }
}
