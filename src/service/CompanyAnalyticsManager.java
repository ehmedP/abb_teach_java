package src.service;

import src.record.DepartmentSummary;
import src.record.Employee;
import src.seed.EmployeeSeed;

import java.util.*;
import java.util.stream.Collectors;

public class CompanyAnalyticsManager {

    private final List<Employee> employees;

    public CompanyAnalyticsManager() {
        this.employees = EmployeeSeed.seed();
    }

    public void execute() {

        System.out.println("===========================================================================================");
        System.out.println("Task A\n");
        this.printGeneralReport();
        System.out.println("===========================================================================================");
        System.out.println("Task B\n");
        this.printDepartmentAnalytics();
        System.out.println("===========================================================================================");
        System.out.println("Task C\n");
        this.printSkillAnalytics();
        System.out.println("===========================================================================================");
    }

    public void printDepartmentAnalytics() {

        Map<String, DepartmentSummary> departmentSummaries = getGroupedDepartmentSummaries();

        departmentSummaries.forEach((department, summary) -> {
            System.out.println("Department: " + department);
            System.out.println(summary);
        });

        departmentSummaries.entrySet().stream()
                .max(Map.Entry.comparingByValue(
                        Comparator.comparingDouble(DepartmentSummary::totalSalary)
                ))
                .ifPresentOrElse(
                        (entry) -> System.out.println("Most Expensive department: " + entry.getKey() + ", Total Salary: " + entry.getValue().totalSalary()),
                        () -> System.out.println("Most Expensive department: N/A")
                );
    }

    public void printGeneralReport() {

        System.out.println("Total Employee Count: " + this.getTotalEmployeeCount());
        System.out.println("Total Salary: " + this.getTotalSalary());
        System.out.println("Average Salary: " + this.getAvgSalary().orElse(0.0));

        this.getOldestEmployee().ifPresentOrElse(
                (employee) -> System.out.println("Oldest Employee: " + employee.name() + ", Age: " + employee.age()),
                () -> System.out.println("Oldest Employee: N/A, Age: N/A")
        );

        this.getYoungestEmployee().ifPresentOrElse(
                (employee) -> System.out.println("Youngest Employee: " + employee.name() + ", Age: " + employee.age()),
                () -> System.out.println("Youngest Employee: N/A, Age: N/A")
        );
    }

    public void printSkillAnalytics() {

        System.out.println(" Skils by cound: \n");

        Map<String, Long> skillCount = employees.stream()
                .flatMap((employee -> employee.skills().stream()))
                .collect(Collectors.groupingBy(skill -> skill, Collectors.counting()));

        skillCount.forEach((skill, count) -> {
            System.out.println("Skill: " + skill + ", Count: " + count);
        });

        Map<String, Long> sortByPopularity = skillCount.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));

        System.out.println("\n\n Skils by popularity (limit 3): \n");

        sortByPopularity.entrySet().stream().limit(3).forEach(entry -> {
            System.out.println("Skill: " + entry.getKey() + ", Count: " + entry.getValue());
        });

        Map<String, Set<String>> skillsByDepartment = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::department,
                        Collectors.flatMapping(employee -> employee.skills().stream(), Collectors.toSet())
                ));

        System.out.println("\n\n Skills by Department: \n");
        skillsByDepartment.forEach((department, skills) -> {
            System.out.println("Department: " + department + ", Skills: " + skills);
        });

        List<List<String>> noneMatchSkillDepartments = skillsByDepartment.entrySet().stream()
                .flatMap(department1 ->
                        skillsByDepartment.entrySet().stream()
                                .filter(department2 -> department1.getKey().compareTo(department2.getKey()) < 0)
                                .filter(department2 -> department1.getValue().stream().noneMatch(department2.getValue()::contains))
                                .map(department2 -> List.of(department1.getKey(), department2.getKey()))
                ).toList();

        System.out.println("\n\n None Match Skill Departments: \n");
        System.out.println(noneMatchSkillDepartments);

    }

    // Helper methods start

    private Map<String, DepartmentSummary> getGroupedDepartmentSummaries() {
        return employees.stream()
                .collect(
                        Collectors.groupingBy(
                                Employee::department,
                                Collectors.collectingAndThen(
                                        Collectors.toList(),
                                        (employeeList) -> new DepartmentSummary(
                                                employeeList.size(),
                                                employeeList.stream().mapToDouble(Employee::salary).average().orElse(0.0),
                                                employeeList.stream().max(Comparator.comparingDouble(Employee::salary)),
                                                employeeList.stream().mapToDouble(Employee::salary).sum()
                                        ))
                        )
                );
    }

    private Integer getTotalEmployeeCount() {
        return employees.size();
    }

    private Double getTotalSalary() {
        return Math.round(
                employees.stream()
                        .mapToDouble(Employee::salary)
                        .sum() * 100.0) / 100.0;
    }

    private OptionalDouble getAvgSalary() {
        return employees.stream()
                .mapToDouble(Employee::salary)
                .average()
                .stream()
                .map(avg -> Math.round(avg * 100.0) / 100.0)
                .findFirst();
    }

    private Optional<Employee> getOldestEmployee() {
        return employees.stream()
                .max(Comparator.comparingInt(Employee::age));
    }

    private Optional<Employee> getYoungestEmployee() {
        return employees.stream()
                .min(Comparator.comparingInt(Employee::age));
    }

    // Helper methods end

}
