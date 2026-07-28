package src.model.concret;

import src.model.abstracts.Employee;

import java.time.LocalDate;

public class Manager extends Employee {

    private Integer teamSize;
    private String department;

    public Manager(Integer teamSize, String department, String name, LocalDate startDate, Double salary) {
        super(name, startDate, salary);

        this.teamSize = teamSize;
        this.department = department;
    }

    public Integer getTeamSize() {
        return teamSize;
    }

    public void setTeamSize(Integer teamSize) {
        this.teamSize = teamSize;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Manager{" + super.toString() +
                ", department='" + department + '\'' +
                ", teamSize=" + teamSize +
                '}';
    }
}
