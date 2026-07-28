package src.model.concret;

import src.model.abstracts.Employee;

import java.time.LocalDate;

public class Developer extends Employee {

    private String programmingLanguage;
    private Integer projectCount;

    public Developer(String programmingLanguage, Integer projectCount, String name, LocalDate startDate, Double salary) {
        super(name, startDate, salary);

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

    @Override
    public String toString() {
        return "Developer{" + super.toString() +
                ", programmingLanguage='" + programmingLanguage + '\'' +
                ", projectCount=" + projectCount +
                '}';
    }
}
