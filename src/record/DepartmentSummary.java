package src.record;

import java.util.Optional;

public record DepartmentSummary(
        Integer employeeCount,
        Double averageSalary,
        Optional<Employee> highestSalary,
        Double totalSalary
) {

    @Override
    public String toString() {
        return """
                Employee Count : %d
                Average Salary : %.2f
                Total Salary   : %.2f
                Highest Salary Employee : \n%s
                """.formatted(
                employeeCount,
                averageSalary,
                totalSalary,
                highestSalary
                        .map(Employee::toString)
                        .orElse("Unknown")
        );
    }
}
