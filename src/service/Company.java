package src.service;

import src.model.abstracts.Employee;

import java.util.Collections;
import java.util.List;

public class Company<T extends Employee> {

    private List<T> employees;

    public Company() {
        //
    }

    public Company(List<T> employees) {
        this.employees = employees;
    }

    public void add(T employee) {

        getEmployees().add(employee);

    }

    public Employee findById(Integer id) {

        for (Employee employee : getEmployees()) {
            if (employee.getId().equals(id)) {
                return employee;
            }
        }

        return null;
    }

    public List<T> getAll() {
        return Collections.unmodifiableList(getEmployees());
    }

    public List<T> getEmployees() {
        return employees;
    }

    public void setEmployees(List<T> employees) {
        this.employees = employees;
    }
}
