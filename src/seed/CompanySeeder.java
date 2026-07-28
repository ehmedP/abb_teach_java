package src.seed;

import src.model.abstracts.Employee;
import src.model.concret.Developer;
import src.model.concret.Intern;
import src.model.concret.Manager;
import src.service.Company;

import java.time.LocalDate;

public class CompanySeeder {

    public static void seed(Company<Employee> company) {

        company.add(new Developer(
                "Java",
                8,
                "Ahmad Aliyev",
                LocalDate.of(2021, 5, 10),
                3500.0
        ));

        company.add(new Developer(
                "PHP",
                12,
                "Murad Hasanov",
                LocalDate.of(2020, 3, 15),
                4200.0
        ));

        company.add(new Developer(
                "Python",
                5,
                "Kamran Mammadov",
                LocalDate.of(2023, 1, 8),
                2800.0
        ));

        company.add(new Developer(
                "C#",
                9,
                "Rashad Karimov",
                LocalDate.of(2019, 11, 1),
                4700.0
        ));

        company.add(new Developer(
                "JavaScript",
                4,
                "Nijat Ibrahimov",
                LocalDate.of(2022, 7, 18),
                3000.0
        ));

        company.add(new Manager(
                15,
                "IT",
                "Aysel Guliyeva",
                LocalDate.of(2018, 4, 3),
                6500.0
        ));

        company.add(new Manager(
                10,
                "HR",
                "Leyla Ahmadova",
                LocalDate.of(2020, 8, 12),
                5200.0
        ));

        company.add(new Manager(
                20,
                "Finance",
                "Elvin Safarov",
                LocalDate.of(2017, 2, 27),
                7200.0
        ));

        company.add(new Intern(
                "Farid Aliyev",
                LocalDate.of(2024, 6, 1),
                800.0,
                "Baku State University",
                6
        ));

        company.add(new Intern(
                "Nigar Ismayilova",
                LocalDate.of(2024, 7, 10),
                900.0,
                "ADA University",
                3
        ));

        company.add(new Intern(
                "Orxan Huseynov",
                LocalDate.of(2024, 5, 15),
                850.0,
                "ASOIU",
                4
        ));

        company.add(new Intern(
                "Sabina Rahimova",
                LocalDate.of(2024, 8, 5),
                950.0,
                "Khazar University",
                6
        ));
    }

}
