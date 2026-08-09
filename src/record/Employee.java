package src.record;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public record Employee(
        String name,
        String department,
        Double salary,
        Integer age,
        List<String> skills,
        LocalDate hireDate,
        Optional<String> managerName
) {

    @Override
    public String toString() {
        return """
                Name       : %s
                Department : %s
                Salary     : %.2f
                Age        : %d
                Skills     : %s
                Hire Date  : %s
                Manager    : %s
                """.formatted(
                name,
                department,
                salary,
                age,
                skills,
                hireDate,
                managerName.orElse("N/A")
        );
    }

}