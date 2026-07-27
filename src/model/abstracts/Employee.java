package src.model.abstracts;

import java.time.LocalDate;

public abstract class Employee implements Comparable<Employee> {

    private static Integer nextId = 1;

    private final Integer id;
    private String name;
    private LocalDate offer_date;
    private Double salary;

    public Employee(String name, LocalDate offer_date, Double salary) {
        this.id = nextId++;

        this.name = name;
        this.offer_date = offer_date;
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

    public LocalDate getOffer_date() {
        return offer_date;
    }

    public void setOffer_date(LocalDate offer_date) {
        this.offer_date = offer_date;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    @Override
    public int compareTo(Employee other) {
        return this.getSalary().compareTo(other.getSalary());
    }
}
