package src.seed;

import src.record.Employee;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class EmployeeSeed {

    public static List<Employee> seed() {

        return List.of(
                // Engineering
                new Employee(
                        "Alice",
                        "Engineering",
                        7500.0,
                        30,
                        List.of("Java", "Spring"),
                        LocalDate.of(2014, 3, 4),
                        Optional.empty()
                ),
                new Employee(
                        "Bob",
                        "Engineering",
                        11000.0,
                        42,
                        List.of("Java", "Spring", "SQL", "Docker"),
                        LocalDate.of(2016, 7, 12),
                        Optional.empty()
                ),
                new Employee(
                        "Charlie",
                        "Engineering",
                        6500.0,
                        27,
                        List.of("Python", "SQL", "Docker"),
                        LocalDate.of(2021, 2, 18),
                        Optional.of("Bob")
                ),
                new Employee(
                        "David",
                        "Engineering",
                        9000.0,
                        35,
                        List.of("Java", "AWS", "Docker"),
                        LocalDate.of(2018, 9, 25),
                        Optional.of("Bob")
                ),
                new Employee(
                        "Emma",
                        "Engineering",
                        13500.0,
                        48,
                        List.of("Java", "AWS", "Kubernetes", "Docker", "SQL"),
                        LocalDate.of(2015, 1, 20),
                        Optional.empty()
                ),
                new Employee(
                        "Frank",
                        "Engineering",
                        5800.0,
                        24,
                        List.of("Python", "React", "SQL"),
                        LocalDate.of(2023, 6, 10),
                        Optional.of("Emma")
                ),

                // Sales
                new Employee(
                        "Grace",
                        "Sales",
                        5500.0,
                        35,
                        List.of("Negotiation", "CRM"),
                        LocalDate.of(2017, 4, 15),
                        Optional.empty()
                ),
                new Employee(
                        "Henry",
                        "Sales",
                        8000.0,
                        41,
                        List.of("Sales", "Negotiation", "CRM"),
                        LocalDate.of(2016, 8, 22),
                        Optional.empty()
                ),
                new Employee(
                        "Ivy",
                        "Sales",
                        4200.0,
                        26,
                        List.of("Sales", "Communication"),
                        LocalDate.of(2022, 3, 5),
                        Optional.of("Henry")
                ),
                new Employee(
                        "Jack",
                        "Sales",
                        6800.0,
                        33,
                        List.of("Sales", "Negotiation", "CRM", "Excel"),
                        LocalDate.of(2019, 11, 14),
                        Optional.of("Henry")
                ),
                new Employee(
                        "Kate",
                        "Sales",
                        9500.0,
                        45,
                        List.of("Sales", "Negotiation", "Leadership"),
                        LocalDate.of(2014, 6, 30),
                        Optional.empty()
                ),
                new Employee(
                        "Leo",
                        "Sales",
                        3700.0,
                        23,
                        List.of("Sales", "Communication"),
                        LocalDate.of(2024, 1, 12),
                        Optional.of("Kate")
                ),

                // HR
                new Employee(
                        "Mia",
                        "HR",
                        5000.0,
                        40,
                        List.of("Recruitment", "Employee Relations"),
                        LocalDate.of(2015, 5, 8),
                        Optional.empty()
                ),
                new Employee(
                        "Noah",
                        "HR",
                        7200.0,
                        38,
                        List.of("Recruitment", "Excel", "Communication"),
                        LocalDate.of(2018, 2, 19),
                        Optional.empty()
                ),
                new Employee(
                        "Olivia",
                        "HR",
                        4500.0,
                        29,
                        List.of("Recruitment", "Communication"),
                        LocalDate.of(2021, 10, 3),
                        Optional.of("Noah")
                ),
                new Employee(
                        "Peter",
                        "HR",
                        6200.0,
                        34,
                        List.of("Employee Relations", "Excel", "Recruitment"),
                        LocalDate.of(2019, 7, 16),
                        Optional.of("Noah")
                ),
                new Employee(
                        "Quinn",
                        "HR",
                        10500.0,
                        52,
                        List.of("Leadership", "Recruitment", "Employee Relations", "Negotiation"),
                        LocalDate.of(2014, 11, 27),
                        Optional.empty()
                ),
                new Employee(
                        "Rachel",
                        "HR",
                        3900.0,
                        25,
                        List.of("Recruitment", "Excel"),
                        LocalDate.of(2023, 4, 21),
                        Optional.of("Quinn")
                ),

                // Marketing
                new Employee(
                        "Sam",
                        "Marketing",
                        6000.0,
                        28,
                        List.of("SEO", "Content Creation"),
                        LocalDate.of(2016, 3, 17),
                        Optional.empty()
                ),
                new Employee(
                        "Tina",
                        "Marketing",
                        8500.0,
                        36,
                        List.of("SEO", "Marketing", "Analytics"),
                        LocalDate.of(2017, 9, 9),
                        Optional.empty()
                ),
                new Employee(
                        "Uma",
                        "Marketing",
                        4800.0,
                        24,
                        List.of("Content Creation", "Social Media"),
                        LocalDate.of(2022, 5, 13),
                        Optional.of("Tina")
                ),
                new Employee(
                        "Victor",
                        "Marketing",
                        7300.0,
                        31,
                        List.of("SEO", "Google Ads", "Analytics", "Content Creation"),
                        LocalDate.of(2020, 8, 7),
                        Optional.of("Tina")
                ),
                new Employee(
                        "Wendy",
                        "Marketing",
                        12000.0,
                        47,
                        List.of("Marketing", "Leadership", "SEO", "Analytics"),
                        LocalDate.of(2015, 12, 1),
                        Optional.empty()
                ),
                new Employee(
                        "Xavier",
                        "Marketing",
                        4100.0,
                        22,
                        List.of("Social Media", "Content Creation"),
                        LocalDate.of(2024, 2, 15),
                        Optional.of("Wendy")
                ),

                // Finance
                new Employee(
                        "Yara",
                        "Finance",
                        7000.0,
                        32,
                        List.of("Accounting", "Financial Analysis"),
                        LocalDate.of(2016, 4, 11),
                        Optional.empty()
                ),
                new Employee(
                        "Zach",
                        "Finance",
                        9800.0,
                        44,
                        List.of("Accounting", "Excel", "Financial Analysis"),
                        LocalDate.of(2017, 10, 20),
                        Optional.empty()
                ),
                new Employee(
                        "Adam",
                        "Finance",
                        5400.0,
                        29,
                        List.of("Accounting", "Excel"),
                        LocalDate.of(2021, 6, 8),
                        Optional.of("Zach")
                ),
                new Employee(
                        "Bella",
                        "Finance",
                        11500.0,
                        50,
                        List.of("Financial Analysis", "Accounting", "Leadership", "Excel"),
                        LocalDate.of(2014, 8, 19),
                        Optional.empty()
                ),
                new Employee(
                        "Chris",
                        "Finance",
                        4600.0,
                        26,
                        List.of("Excel", "Accounting"),
                        LocalDate.of(2023, 9, 14),
                        Optional.of("Bella")
                ),
                new Employee(
                        "Diana",
                        "Finance",
                        18000.0,
                        58,
                        List.of("Financial Analysis", "Accounting", "Leadership", "Excel", "Negotiation"),
                        LocalDate.of(2014, 2, 6),
                        Optional.empty()
                )
        );
    }
}