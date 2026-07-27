package src.model.concret;

import src.model.abstracts.Employee;

import java.time.LocalDate;

public class Manager extends Employee {

    private Integer ownTeamSize;
    private String department;

    public Manager(Integer ownTeamSize, String department, String name, LocalDate offer_date, Double salary) {
        super(name, offer_date, salary);

        this.ownTeamSize = ownTeamSize;
        this.department = department;
    }

    public Integer getOwnTeamSize() {
        return ownTeamSize;
    }

    public void setOwnTeamSize(Integer ownTeamSize) {
        this.ownTeamSize = ownTeamSize;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
