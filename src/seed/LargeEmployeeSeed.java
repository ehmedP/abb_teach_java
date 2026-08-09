package src.seed;

import src.record.Employee;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;

public class LargeEmployeeSeed {

    private static final List<String> DEPARTMENTS = List.of(
            "Engineering", "Sales", "HR", "Marketing", "Finance"
    );

    private static final List<String> SKILLS = List.of(
            "Java", "Python", "SQL", "AWS", "React", "Docker", "Excel", "Sales",
            "Negotiation", "Leadership", "Recruitment", "Accounting", "SEO", "Analytics", "CRM"
    );

    private static final LocalDate HIRE_DATE_FROM = LocalDate.of(2014, 1, 1);
    private static final LocalDate HIRE_DATE_TO = LocalDate.of(2024, 12, 31);
    private static final long HIRE_DATE_RANGE_IN_DAYS = ChronoUnit.DAYS.between(HIRE_DATE_FROM, HIRE_DATE_TO);

    private static final int MANAGER_RATIO = 100;

    public static List<Employee> seed(int count) {

        Random random = new Random(42);

        int managerCount = Math.max(1, count / MANAGER_RATIO);

        List<String> managerNames = IntStream.range(0, managerCount)
                .mapToObj(index -> "Manager_" + index)
                .toList();

        return IntStream.range(0, count)
                .mapToObj(index -> new Employee(
                        "Employee_" + index,
                        DEPARTMENTS.get(random.nextInt(DEPARTMENTS.size())),
                        3000.0 + random.nextInt(15_001),
                        22 + random.nextInt(39),
                        randomSkills(random),
                        HIRE_DATE_FROM.plusDays(random.nextLong(HIRE_DATE_RANGE_IN_DAYS)),
                        index % MANAGER_RATIO == 0
                                ? Optional.empty()
                                : Optional.of(managerNames.get(random.nextInt(managerCount)))
                ))
                .toList();
    }

    private static List<String> randomSkills(Random random) {

        int skillCount = 2 + random.nextInt(4); // 2 - 5 skill

        return random.ints(0, SKILLS.size())
                .distinct()
                .limit(skillCount)
                .mapToObj(SKILLS::get)
                .toList();
    }
}
