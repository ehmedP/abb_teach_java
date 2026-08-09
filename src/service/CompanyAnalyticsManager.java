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
        this.printDepartmentAnalytics();
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
