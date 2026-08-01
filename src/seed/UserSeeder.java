package src.seed;

import src.model.Book;
import src.model.BorrowRecord;
import src.model.User;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class UserSeeder {

    public static List<User> seed(List<Book> books) {

        Book book1984 = books.get(0);
        Book animalFarm = books.get(1);
        Book homageToCatalonia = books.get(2);
        Book cleanCode = books.get(3);
        Book cleanArchitecture = books.get(4);
        Book theCleanCoder = books.get(5);
        Book effectiveJava = books.get(6);
        Book javaPuzzlers = books.get(7);
        Book pragmaticProgrammer = books.get(8);
        Book practicesOfAgileDeveloper = books.get(9);
        Book javaConcurrency = books.get(10);
        Book designPatterns = books.get(11);
        Book refactoring = books.get(12);
        Book peaa = books.get(13);
        Book domainDrivenDesign = books.get(14);
        Book introToAlgorithms = books.get(15);
        Book theHobbit = books.get(16);
        Book fellowshipOfTheRing = books.get(17);
        Book theTwoTowers = books.get(18);
        Book returnOfTheKing = books.get(19);
        Book philosophersStone = books.get(20);
        Book chamberOfSecrets = books.get(21);
        Book prisonerOfAzkaban = books.get(22);
        Book gameOfThrones = books.get(23);
        Book clashOfKings = books.get(24);
        Book sapiens = books.get(25);
        Book homoDeus = books.get(26);
        Book lessons21 = books.get(27);
        Book braveNewWorld = books.get(28);
        Book toKillAMockingbird = books.get(29);
        Book greatGatsby = books.get(30);
        Book crimeAndPunishment = books.get(31);

        User u1 = new User("Aydin", 25, Arrays.asList(
                new BorrowRecord(book1984, LocalDate.of(2025, 9, 1), LocalDate.of(2025, 9, 10)),
                new BorrowRecord(cleanCode, LocalDate.of(2025, 10, 5), null)
        ));

        User u2 = new User("Leyla", 22, Arrays.asList(
                new BorrowRecord(effectiveJava, LocalDate.of(2025, 10, 2), LocalDate.of(2025, 10, 20)),
                new BorrowRecord(javaConcurrency, LocalDate.of(2025, 10, 12), null)
        ));

        User u3 = new User("Murad", 28, Arrays.asList(
                new BorrowRecord(pragmaticProgrammer, LocalDate.of(2025, 9, 10), LocalDate.of(2025, 9, 25))
        ));

        User u4 = new User("Nigar", 30, Arrays.asList(
                new BorrowRecord(theHobbit, LocalDate.of(2025, 8, 1), LocalDate.of(2025, 8, 15)),
                new BorrowRecord(fellowshipOfTheRing, LocalDate.of(2025, 8, 20), LocalDate.of(2025, 9, 5)),
                new BorrowRecord(theTwoTowers, LocalDate.of(2025, 10, 1), null)
        ));

        User u5 = new User("Elvin", 27, Arrays.asList(
                new BorrowRecord(gameOfThrones, LocalDate.of(2025, 10, 3), LocalDate.of(2025, 10, 18)),
                new BorrowRecord(clashOfKings, LocalDate.of(2025, 10, 20), null)
        ));

        User u6 = new User("Gunel", 24, Arrays.asList(
                new BorrowRecord(sapiens, LocalDate.of(2025, 9, 5), LocalDate.of(2025, 9, 20)),
                new BorrowRecord(homoDeus, LocalDate.of(2025, 10, 1), LocalDate.of(2025, 10, 15)),
                new BorrowRecord(lessons21, LocalDate.of(2025, 10, 16), LocalDate.of(2025, 10, 30))
        ));

        User u7 = new User("Rashad", 29, Arrays.asList(
                new BorrowRecord(cleanArchitecture, LocalDate.of(2025, 7, 1), LocalDate.of(2025, 7, 20)),
                new BorrowRecord(theCleanCoder, LocalDate.of(2025, 10, 4), null)
        ));

        User u8 = new User("Sabina", 23, Arrays.asList(
                new BorrowRecord(philosophersStone, LocalDate.of(2025, 6, 1), LocalDate.of(2025, 6, 10)),
                new BorrowRecord(chamberOfSecrets, LocalDate.of(2025, 6, 11), LocalDate.of(2025, 6, 25)),
                new BorrowRecord(prisonerOfAzkaban, LocalDate.of(2025, 10, 8), null)
        ));

        User u9 = new User("Tural", 31, Arrays.asList(
                new BorrowRecord(designPatterns, LocalDate.of(2025, 5, 1), LocalDate.of(2025, 5, 30)),
                new BorrowRecord(refactoring, LocalDate.of(2025, 10, 6), LocalDate.of(2025, 10, 22))
        ));

        User u10 = new User("Fidan", 26, Arrays.asList(
                new BorrowRecord(domainDrivenDesign, LocalDate.of(2025, 10, 9), null)
        ));

        User u11 = new User("Kamran", 33, Arrays.asList(
                new BorrowRecord(introToAlgorithms, LocalDate.of(2025, 4, 1), LocalDate.of(2025, 4, 28)),
                new BorrowRecord(javaPuzzlers, LocalDate.of(2025, 10, 11), LocalDate.of(2025, 10, 25))
        ));

        User u12 = new User("Aysel", 21, Arrays.asList(
                new BorrowRecord(returnOfTheKing, LocalDate.of(2025, 10, 2), null),
                new BorrowRecord(theHobbit, LocalDate.of(2025, 10, 13), null)
        ));

        User u13 = new User("Orkhan", 34, Arrays.asList(
                new BorrowRecord(braveNewWorld, LocalDate.of(2025, 9, 1), LocalDate.of(2025, 9, 15)),
                new BorrowRecord(toKillAMockingbird, LocalDate.of(2025, 10, 14), LocalDate.of(2025, 10, 28))
        ));

        User u14 = new User("Konul", 20, Arrays.asList(
                new BorrowRecord(greatGatsby, LocalDate.of(2025, 3, 1), LocalDate.of(2025, 3, 12))
        ));

        User u15 = new User("Emin", 36, Arrays.asList(
                new BorrowRecord(crimeAndPunishment, LocalDate.of(2025, 10, 1), LocalDate.of(2025, 10, 31)),
                new BorrowRecord(homageToCatalonia, LocalDate.of(2025, 10, 15), null)
        ));

        User u16 = new User("Zeynab", 19, Arrays.asList(
                new BorrowRecord(animalFarm, LocalDate.of(2025, 2, 1), LocalDate.of(2025, 2, 10)),
                new BorrowRecord(book1984, LocalDate.of(2025, 10, 7), LocalDate.of(2025, 10, 19))
        ));

        User u17 = new User("Ilkin", 40, Arrays.asList(
                new BorrowRecord(peaa, LocalDate.of(2025, 1, 5), LocalDate.of(2025, 1, 25))
        ));

        User u18 = new User("Nərmin", 25, Arrays.asList(
                new BorrowRecord(practicesOfAgileDeveloper, LocalDate.of(2025, 10, 10), LocalDate.of(2025, 10, 24)),
                new BorrowRecord(cleanCode, LocalDate.of(2025, 10, 25), null)
        ));

        User u19 = new User("Vusal", 27, Arrays.asList());

        User u20 = new User("Sevinc", 32, Arrays.asList(
                new BorrowRecord(javaConcurrency, LocalDate.of(2025, 10, 2), LocalDate.of(2025, 10, 16)),
                new BorrowRecord(effectiveJava, LocalDate.of(2025, 10, 17), LocalDate.of(2025, 10, 29)),
                new BorrowRecord(cleanCode, LocalDate.of(2025, 10, 30), null)
        ));

        User u21 = new User("Ramin", 38, Arrays.asList(
                new BorrowRecord(gameOfThrones, LocalDate.of(2024, 11, 1), LocalDate.of(2024, 11, 20))
        ));

        User u22 = new User("Nurlan", 45, Arrays.asList());

        return Arrays.asList(
                u1, u2, u3, u4, u5, u6, u7, u8, u9, u10,
                u11, u12, u13, u14, u15, u16, u17, u18, u19, u20,
                u21, u22
        );
    }

}
