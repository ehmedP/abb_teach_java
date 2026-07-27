package src.model.concret;

import src.model.abstracts.Employee;

import java.time.LocalDate;

public class Intern extends Employee {

    private String university;
    private Integer internshipDuration;


    public Intern(String name, LocalDate offer_date, Double salary, String university, Integer internshipDuration) {
        super(name, offer_date, salary);

        this.university = university;
        this.internshipDuration = internshipDuration;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public Integer getInternshipDuration() {
        return internshipDuration;
    }

    public void setInternshipDuration(Integer internshipDuration) {
        this.internshipDuration = internshipDuration;
    }
}
