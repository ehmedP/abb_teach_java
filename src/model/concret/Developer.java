package src.model.concret;

import src.model.abstracts.Employee;

import java.time.LocalDate;

public class Developer extends Employee {

    private String programmingLanguage;
    private Integer projectCount;

    public Developer(String programmingLanguage, Integer projectCount, String name, LocalDate offer_date, Double salary) {
        super(name, offer_date, salary);

        this.programmingLanguage = programmingLanguage;
        this.projectCount = projectCount;
    }

    public String getProgrammingLanguage() {
        return programmingLanguage;
    }

    public void setProgrammingLanguage(String programmingLanguage) {
        this.programmingLanguage = programmingLanguage;
    }

    public Integer getProjectCount() {
        return projectCount;
    }

    public void setProjectCount(Integer projectCount) {
        this.projectCount = projectCount;
    }
}
