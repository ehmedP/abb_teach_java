package src.service;

import src.enums.ExperienceGroupEnum;
import src.record.DepartmentSummary;
import src.record.Employee;
import src.seed.EmployeeSeed;
import src.support.DepartmentEmployeeCollector;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class CompanyAnalyticsManager {

    private final List<Employee> employees;

    public CompanyAnalyticsManager() {
        this.employees = EmployeeSeed.seed();
    }

    public void execute() {

        System.out.println(
                this.generateReport()
        );

    }

    public String generateReport() {

        StringBuilder reportBuilder = new StringBuilder();

        reportBuilder.append("===========================================================================================\n");
        reportBuilder.append("Task A\n\n");
        reportBuilder.append(this.generateGeneralReport()).append("\n");
        reportBuilder.append("===========================================================================================\n");
        reportBuilder.append("Task B\n\n");
        reportBuilder.append(this.generateDepartmentAnalytics()).append("\n");
        reportBuilder.append("===========================================================================================\n");
        reportBuilder.append("Task C\n\n");
        reportBuilder.append(this.generateSkillAnalytics()).append("\n");
        reportBuilder.append("===========================================================================================\n");
        reportBuilder.append("Task D\n\n");
        reportBuilder.append(this.generateAgeExperienceReport()).append("\n");
        reportBuilder.append("===========================================================================================\n");
        reportBuilder.append("Task E\n\n");
        reportBuilder.append(this.generateManagementStructureReport()).append("\n");
        reportBuilder.append("===========================================================================================\n");
        reportBuilder.append("Task F\n\n");
        reportBuilder.append(this.generateMaxAndMinSalaryReport()).append("\n");
        reportBuilder.append("===========================================================================================\n");
        reportBuilder.append("Task G\n\n");
        reportBuilder.append(this.generateGroupingEmployeesByDepartmentV2()).append("\n");
        reportBuilder.append("===========================================================================================\n");

        return reportBuilder.toString();
    }

    public String generateDepartmentAnalytics() {

        Map<String, DepartmentSummary> departmentSummaries = employees.stream()
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

        String departmentReport = departmentSummaries.entrySet().stream()
                .map(entry -> "Department: " + entry.getKey() + "\n" + entry.getValue())
                .collect(Collectors.joining("\n"));

        String mostExpensiveDepartment = departmentSummaries.entrySet().stream()
                .max(Map.Entry.comparingByValue(
                        Comparator.comparingDouble(DepartmentSummary::totalSalary)
                ))
                .map(entry -> "Most Expensive department: " + entry.getKey() + ", Total Salary: " + entry.getValue().totalSalary())
                .orElse("Most Expensive department: N/A");

        return departmentReport + "\n" + mostExpensiveDepartment;
    }

    public String generateGeneralReport() {

        double totalSalary = employees.stream()
                .mapToDouble(Employee::salary)
                .sum();

        OptionalDouble avgSalary = employees.stream()
                .mapToDouble(Employee::salary)
                .average();

        String oldestEmployee = employees.stream()
                .max(Comparator.comparingInt(Employee::age))
                .map(employee -> "Oldest Employee: " + employee.name() + ", Age: " + employee.age())
                .orElse("Oldest Employee: N/A, Age: N/A");

        String youngestEmployee = employees.stream()
                .min(Comparator.comparingInt(Employee::age))
                .map(employee -> "Youngest Employee: " + employee.name() + ", Age: " + employee.age())
                .orElse("Youngest Employee: N/A, Age: N/A");

        return String.join("\n",
                "Total Employee Count: " + employees.size(),
                "Total Salary: " + Math.round(totalSalary * 100.0) / 100.0,
                "Average Salary: " + avgSalary.orElse(0.0),
                oldestEmployee,
                youngestEmployee
        );
    }

    public String generateSkillAnalytics() {

        Map<String, Long> skillCount = employees.stream()
                .flatMap((employee -> employee.skills().stream()))
                .collect(Collectors.groupingBy(skill -> skill, Collectors.counting()));

        String skillCountReport = skillCount.entrySet().stream()
                .map(entry -> "Skill: " + entry.getKey() + ", Count: " + entry.getValue())
                .collect(Collectors.joining("\n"));

        Map<String, Long> sortByPopularity = skillCount.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));

        String popularSkillReport = sortByPopularity.entrySet().stream()
                .limit(3)
                .map(entry -> "Skill: " + entry.getKey() + ", Count: " + entry.getValue())
                .collect(Collectors.joining("\n"));

        Map<String, Set<String>> skillsByDepartment = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::department,
                        Collectors.flatMapping(employee -> employee.skills().stream(), Collectors.toSet())
                ));

        String skillsByDepartmentReport = skillsByDepartment.entrySet().stream()
                .map(entry -> "Department: " + entry.getKey() + ", Skills: " + entry.getValue())
                .collect(Collectors.joining("\n"));

        List<List<String>> noneMatchSkillDepartments = skillsByDepartment.entrySet().stream()
                .flatMap(department1 ->
                        skillsByDepartment.entrySet().stream()
                                .filter(department2 -> department1.getKey().compareTo(department2.getKey()) < 0)
                                .filter(department2 -> department1.getValue().stream().noneMatch(department2.getValue()::contains))
                                .map(department2 -> List.of(department1.getKey(), department2.getKey()))
                ).toList();

        return String.join("\n",
                " Skills by count: \n",
                skillCountReport,
                "\n Skills by popularity (limit 3): \n",
                popularSkillReport,
                "\n Skills by Department: \n",
                skillsByDepartmentReport,
                "\n None Match Skill Departments: \n",
                noneMatchSkillDepartments.toString()
        );
    }

    public String generateAgeExperienceReport() {

        Map<ExperienceGroupEnum, Double> experienceReport = employees.stream()
                .collect(
                        Collectors.groupingBy(
                                (employee) -> getExperienceGroup(employee.hireDate()),
                                Collectors.averagingDouble(Employee::salary)
                        )
                );

        return experienceReport.entrySet().stream()
                .map(entry -> "Experience Group: %s, Average Salary: %.2f".formatted(
                        entry.getKey().label(), entry.getValue()
                ))
                .collect(Collectors.joining("\n"));
    }

    public String generateManagementStructureReport() {

        Map<String, Long> managerEmployees = employees.stream()
                // filter yox flatmap isletmeyime sebeb optional null olanlari temizlemek idi burda, performans cehetden yeqinki filter daha yaxsi olardi amma
                .flatMap(employee -> employee.managerName().stream())
                .collect(
                        Collectors.groupingBy(
                                managerName -> managerName,
                                Collectors.counting()
                        )
                );

        return managerEmployees.entrySet().stream()
                .map(entry -> "Manager: " + entry.getKey() + ", Employees: " + entry.getValue())
                .collect(Collectors.joining("\n"));
    }

    public String generateMaxAndMinSalaryReport() {

        Map<String, Optional<Employee>> minMaxValues = employees.stream()
                .collect(
                        Collectors.teeing(
                                Collectors.minBy(Comparator.comparingDouble(Employee::salary).thenComparing(Employee::name)),
                                Collectors.maxBy(Comparator.comparingDouble(Employee::salary).thenComparing(Employee::name)),
                                (min, max) -> new LinkedHashMap<>(
                                        Map.of(
                                                "Min", min,
                                                "Max", max
                                        )
                                )
                        )
                );

        return minMaxValues.entrySet().stream()
                .map(entry -> entry.getValue()
                        .map(employee -> entry.getKey() + " salary: " + employee.salary() + ", Employee: " + employee.name())
                        .orElse(entry.getKey() + " salary: N/A, Employee: N/A")
                )
                .collect(Collectors.joining("\n"));
    }

    // Variant A
    public String generateGroupingEmployeesByDepartment() {

        return employees.stream()
                .collect(
                        Collectors.groupingBy(
                                Employee::department,
                                TreeMap::new,
                                Collectors.mapping(Employee::name, Collectors.joining(", "))
                        )
                ).entrySet().stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .collect(Collectors.joining("\n"));
    }

    // Variant B
    public String generateGroupingEmployeesByDepartmentV2() {

        return employees.stream()
                .collect(new DepartmentEmployeeCollector());
    }

    // Helper methods

    private ExperienceGroupEnum getExperienceGroup(LocalDate hireDate) {
        long years = ChronoUnit.YEARS.between(hireDate, LocalDate.now());

        if (years <= 2) {
            return ExperienceGroupEnum.ZERO_TO_TWO;
        }

        if (years <= 5) {
            return ExperienceGroupEnum.THREE_TO_FIVE;
        }

        return ExperienceGroupEnum.FIVE_PLUS;
    }

}
