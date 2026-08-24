import src.model.Employee;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

void main() {

    List<Employee> employees = new ArrayList<>();
    Path data = Path.of("data/employees.txt");
    List<String> report = new ArrayList<>();

    try (Stream<String> line = Files.lines(data)) {

        line.map(l -> l.split(","))
                .map(a -> new Employee(a[0], a[1], Double.parseDouble(a[2]), Integer.parseInt(a[3])))
                .forEach(employees::add);

    } catch (IOException e) {
        e.printStackTrace();
    }

    // task 2
    Map<String, List<Employee>> groupedByDepartment = employees.stream()
            .collect(Collectors.groupingBy(Employee::getDepartment));

    report.add("=== Employees by Department ===");
    groupedByDepartment.forEach((department, employeeList) -> {
        report.add(department + ":");
        employeeList.forEach(employee ->
                report.add("  - " + employee.getName())
        );
    });


    // task 3
    Map<String, Double> averageSalaryByDepartment = employees.stream()
            .collect(Collectors.groupingBy(
                    Employee::getDepartment,
                    Collectors.averagingDouble(Employee::getSalary)
            ));

    report.add("\n=== Average Salary by Department ===");
    averageSalaryByDepartment.forEach((department, averageSalary) ->
            report.add(department + ": " + String.format("%.2f", averageSalary))
    );


    // task 4
    List<Employee> greaterThanSalary = employees.stream()
            .filter(e -> e.getSalary() > 2000)
            .toList();

    report.add("\n=== Employees with Salary > 2000 ===");
    greaterThanSalary.forEach(employee ->
            report.add(employee.getName() + ": " + employee.getSalary())
    );


    // task 5
    List<Employee> sortBySalary = employees.stream()
            .sorted(Comparator.comparing(Employee::getSalary).reversed())
            .toList();

    report.add("\n=== Employees Sorted by Salary ===");
    sortBySalary.forEach(employee ->
            report.add(employee.getName() + ": " + employee.getSalary())
    );


    // task 6
    Optional<Employee> maxSalaryEmployee = employees.stream()
            .max(Comparator.comparing(Employee::getSalary));

    report.add("\n=== Highest Salary Employee ===");
    maxSalaryEmployee.ifPresent(employee ->
            report.add(employee.getName() + ": " + employee.getSalary())
    );


    // task 7
    String names = employees.stream()
            .map(Employee::getName)
            .collect(Collectors.joining(", "));

    report.add("\n=== Employee Names ===");
    report.add(names);


    // task 8
    Map<String, Long> employeeCountByDepartment = employees.stream()
            .collect(Collectors.groupingBy(
                    Employee::getDepartment,
                    Collectors.counting()
            ));

    report.add("\n=== Employee Count by Department ===");
    employeeCountByDepartment.forEach((department, count) ->
            report.add(department + ": " + count)
    );

    try {

        Files.write(Path.of("data/salary_report.txt"), report);

    } catch (IOException e) {
        e.printStackTrace();
    }

}
