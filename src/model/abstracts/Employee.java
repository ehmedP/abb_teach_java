package src.model.abstracts;

import java.time.LocalDate;

public abstract class Employee implements Comparable<Employee> {

    private static Integer nextId = 1;

    private final Integer id;
    private String name;
    private LocalDate startDate;
    private Double salary;

    public Employee(String name, LocalDate startDate, Double salary) {
        this.id = nextId++;

        this.name = name;
        this.startDate = startDate;
        this.salary = salary;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    @Override
    public int compareTo(Employee other) {
        return this.salary.compareTo(other.salary);
    }

    @Override
    public String toString() {
        return "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", salary=" + salary;
    }
}
