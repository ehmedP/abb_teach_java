package seed;

import enums.BookGenreEnum;
import enums.CopyStatusEnum;
import enums.NotificationTypeEnum;
import model.concrets.*;
import service.Library;

import java.time.LocalDate;
import java.util.*;

public class LibrarySeeder {

    public static void seed(Library library) {

        // BOOKS
        Book book1 = new Book(1, "The Great Gatsby", "F. Scott Fitzgerald", BookGenreEnum.FICTION, 1001);
        Book book2 = new Book(2, "A Brief History of Time", "Stephen Hawking", BookGenreEnum.SCIENCE, 1002);
        Book book3 = new Book(3, "Sapiens", "Yuval Noah Harari", BookGenreEnum.HISTORY, 1003);
        Book book4 = new Book(4, "Clean Code", "Robert C. Martin", BookGenreEnum.TECHNOLOGY, 1004);
        Book book5 = new Book(5, "To Kill a Mockingbird", "Harper Lee", BookGenreEnum.FICTION, 1005);
        Book book6 = new Book(6, "The Selfish Gene", "Richard Dawkins", BookGenreEnum.SCIENCE, 1006);
        Book book7 = new Book(7, "Guns, Germs, and Steel", "Jared Diamond", BookGenreEnum.HISTORY, 1007);
        Book book8 = new Book(8, "Design Patterns", "Gang of Four", BookGenreEnum.TECHNOLOGY, 1008);
        Book book9 = new Book(9, "1984", "George Orwell", BookGenreEnum.FICTION, 1009);
        Book book10 = new Book(10, "The Structure of Scientific Revolutions", "Thomas Kuhn", BookGenreEnum.SCIENCE, 1010);
        Book book11 = new Book(11, "The Art of War", "Sun Tzu", BookGenreEnum.HISTORY, 1011);
        Book book12 = new Book(12, "Introduction to Algorithms", "CLRS", BookGenreEnum.TECHNOLOGY, 1012);

        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);
        library.addBook(book4);
        library.addBook(book5);
        library.addBook(book6);
        library.addBook(book7);
        library.addBook(book8);
        library.addBook(book9);
        library.addBook(book10);
        library.addBook(book11);
        library.addBook(book12);

        // BOOK COPIES
        BookCopy copy1 = new BookCopy(1, "CP-001", book1, "BR-001", CopyStatusEnum.BORROWED);
        BookCopy copy2 = new BookCopy(2, "CP-002", book1, "BR-002", CopyStatusEnum.AVAILABLE);
        BookCopy copy3 = new BookCopy(3, "CP-003", book2, "BR-001", CopyStatusEnum.AVAILABLE);
        BookCopy copy4 = new BookCopy(4, "CP-004", book3, "BR-001", CopyStatusEnum.BORROWED);
        BookCopy copy5 = new BookCopy(5, "CP-005", book3, "BR-003", CopyStatusEnum.AVAILABLE);
        BookCopy copy6 = new BookCopy(6, "CP-006", book4, "BR-002", CopyStatusEnum.BORROWED);
        BookCopy copy7 = new BookCopy(7, "CP-007", book4, "BR-003", CopyStatusEnum.AVAILABLE);
        BookCopy copy8 = new BookCopy(8, "CP-008", book5, "BR-001", CopyStatusEnum.AVAILABLE);
        BookCopy copy9 = new BookCopy(9, "CP-009", book6, "BR-002", CopyStatusEnum.BORROWED);
        BookCopy copy10 = new BookCopy(10, "CP-010", book7, "BR-003", CopyStatusEnum.AVAILABLE);
        BookCopy copy11 = new BookCopy(11, "CP-011", book8, "BR-001", CopyStatusEnum.BORROWED);
        BookCopy copy12 = new BookCopy(12, "CP-012", book8, "BR-002", CopyStatusEnum.AVAILABLE);
        BookCopy copy13 = new BookCopy(13, "CP-013", book9, "BR-001", CopyStatusEnum.AVAILABLE);
        BookCopy copy14 = new BookCopy(14, "CP-014", book9, "BR-003", CopyStatusEnum.BORROWED);
        BookCopy copy15 = new BookCopy(15, "CP-015", book10, "BR-002", CopyStatusEnum.AVAILABLE);
        BookCopy copy16 = new BookCopy(16, "CP-016", book11, "BR-003", CopyStatusEnum.AVAILABLE);
        BookCopy copy17 = new BookCopy(17, "CP-017", book12, "BR-001", CopyStatusEnum.AVAILABLE);
        BookCopy copy18 = new BookCopy(18, "CP-018", book5, "BR-002", CopyStatusEnum.LOST);

        Map<String, List<BookCopy>> copies1 = new HashMap<>();
        copies1.put("CP-001", Collections.singletonList(copy1));
        copies1.put("CP-003", Collections.singletonList(copy3));
        copies1.put("CP-004", Collections.singletonList(copy4));
        copies1.put("CP-008", Collections.singletonList(copy8));
        copies1.put("CP-011", Collections.singletonList(copy11));
        copies1.put("CP-013", Collections.singletonList(copy13));
        copies1.put("CP-017", Collections.singletonList(copy17));

        Map<String, List<BookCopy>> copies2 = new HashMap<>();
        copies2.put("CP-002", Collections.singletonList(copy2));
        copies2.put("CP-006", Collections.singletonList(copy6));
        copies2.put("CP-009", Collections.singletonList(copy9));
        copies2.put("CP-012", Collections.singletonList(copy12));
        copies2.put("CP-015", Collections.singletonList(copy15));
        copies2.put("CP-018", Collections.singletonList(copy18));

        Map<String, List<BookCopy>> copies3 = new HashMap<>();
        copies3.put("CP-005", Collections.singletonList(copy5));
        copies3.put("CP-007", Collections.singletonList(copy7));
        copies3.put("CP-010", Collections.singletonList(copy10));
        copies3.put("CP-014", Collections.singletonList(copy14));
        copies3.put("CP-016", Collections.singletonList(copy16));

        // BRANCHES
        Branch branch1 = new Branch(1, "BR-001", "Downtown Branch", "123 Main Street, City Center", copies1);
        Branch branch2 = new Branch(2, "BR-002", "University Branch", "456 College Avenue, Campus Area", copies2);
        Branch branch3 = new Branch(3, "BR-003", "Westside Branch", "789 Oak Drive, Westside District", copies3);

        library.addBranch(branch1);
        library.addBranch(branch2);
        library.addBranch(branch3);

        // MEMBERS
        Member m1 = new Member(1, "Alice Johnson", "alice.johnson@email.com");
        Member m2 = new Member(2, "Bob Smith", "bob.smith@email.com");
        Member m3 = new Member(3, "Charlie Brown", "charlie.brown@email.com");
        Member m4 = new Member(4, "Diana Prince", "diana.prince@email.com");
        Member m5 = new Member(5, "Edward Norton", "edward.norton@email.com");
        Member m6 = new Member(6, "Fiona Apple", "fiona.apple@email.com");
        Member m7 = new Member(7, "George Lucas", "george.lucas@email.com");
        Member m8 = new Member(8, "Hannah Montana", "hannah.montana@email.com");
        Member m9 = new Member(9, "Ivan Petrov", "ivan.petrov@email.com");
        Member m10 = new Member(10, "Julia Roberts", "julia.roberts@email.com");

        // LOANS
        LocalDate today = LocalDate.of(2026, 7, 24);

        Loan loan1 = new Loan(1, "LN-001", copy1, m1, today.minusDays(12), today.plusDays(2), false);
        Loan loan2 = new Loan(2, "LN-002", copy6, m2, today.minusDays(20), today.minusDays(5), false);
        Loan loan3 = new Loan(3, "LN-003", copy13, m3, today.minusDays(30), today.minusDays(10), true);
        Loan loan4 = new Loan(4, "LN-004", copy4, m4, today.minusDays(5), today.plusDays(9), false);
        Loan loan5 = new Loan(5, "LN-005", copy11, m5, today.minusDays(25), today.minusDays(10), false);
        Loan loan6 = new Loan(6, "LN-006", copy9, m6, today.minusDays(3), today.plusDays(11), false);
        Loan loan7 = new Loan(7, "LN-007", copy14, m7, today.minusDays(40), today.minusDays(15), true);
        Loan loan8 = new Loan(8, "LN-008", copy2, m8, today.minusDays(1), today.plusDays(13), false);
        Loan loan9 = new Loan(9, "LN-009", copy5, m9, today.minusDays(14), today.minusDays(7), true);
        Loan loan10 = new Loan(10, "LN-010", copy7, m10, today, today.plusDays(14), false);

        library.addLoan(loan1);
        library.addLoan(loan2);
        library.addLoan(loan3);
        library.addLoan(loan4);
        library.addLoan(loan5);
        library.addLoan(loan6);
        library.addLoan(loan7);
        library.addLoan(loan8);
        library.addLoan(loan9);
        library.addLoan(loan10);

        // RESERVATIONS
        Reservation res1 = new Reservation(m3, book1, today.minusDays(2), 10);
        Reservation res2 = new Reservation(m6, book4, today.minusDays(1), 8);
        Reservation res3 = new Reservation(m9, book8, today, 12);
        Reservation res4 = new Reservation(m2, book3, today.minusDays(3), 6);
        Reservation res5 = new Reservation(m5, book9, today.minusDays(4), 9);
        Reservation res6 = new Reservation(m1, book6, today.minusDays(1), 7);

        library.addReservation(String.valueOf(book1.getId()), res1);
        library.addReservation(String.valueOf(book4.getId()), res2);
        library.addReservation(String.valueOf(book8.getId()), res3);
        library.addReservation(String.valueOf(book3.getId()), res4);
        library.addReservation(String.valueOf(book9.getId()), res5);
        library.addReservation(String.valueOf(book6.getId()), res6);

        // FINE RECORDS
        FineRecord fine1 = new FineRecord(m2, 15.50, "Book returned late (5 days)", today.minusDays(5));
        FineRecord fine2 = new FineRecord(m5, 25.00, "Book returned late (10 days)", today.minusDays(10));
        FineRecord fine3 = new FineRecord(m7, 10.00, "Book returned damaged", today.minusDays(15));
        FineRecord fine4 = new FineRecord(m3, 5.00, "Late return penalty (2 days)", today.minusDays(7));

        library.addFineRecord(fine1);
        library.addFineRecord(fine2);
        library.addFineRecord(fine3);
        library.addFineRecord(fine4);

        // NOTIFICATIONS
        Notification notif1 = new Notification(NotificationTypeEnum.DUE_SOON, "Dear Alice Johnson, your book 'The Great Gatsby' must be returned within 2 days.", today);
        Notification notif2 = new Notification(NotificationTypeEnum.OVERDUE, "Dear Bob Smith, your book 'Clean Code' is 5 days overdue. A penalty has been applied.", today);
        Notification notif3 = new Notification(NotificationTypeEnum.RESERVATION_READY, "Dear Charlie Brown, your reservation for 'The Great Gatsby' is ready.", today.minusDays(1));
        Notification notif4 = new Notification(NotificationTypeEnum.BLACKLIST_WARNING, "Dear Edward Norton, due to repeated late returns you are at risk of being blacklisted.", today);
        Notification notif5 = new Notification(NotificationTypeEnum.DUE_SOON, "Dear Diana Prince, your book 'Sapiens' must be returned within 9 days.", today);
        Notification notif6 = new Notification(NotificationTypeEnum.OVERDUE, "Dear Fiona Apple, warning for 'The Selfish Gene': please avoid late return.", today.minusDays(2));

        library.addNotification(m1, notif1);
        library.addNotification(m2, notif2);
        library.addNotification(m3, notif3);
        library.addNotification(m5, notif4);
        library.addNotification(m4, notif5);
        library.addNotification(m6, notif6);

        // POPULARITY COUNTER
        library.recordBookPopularity(book1, 15);
        library.recordBookPopularity(book4, 10);
        library.recordBookPopularity(book9, 8);
        library.recordBookPopularity(book3, 12);
        library.recordBookPopularity(book8, 7);
        library.recordBookPopularity(book5, 6);

        // MEMBER ACTIVITY
        library.recordMemberActivity(m1, 5);
        library.recordMemberActivity(m2, 3);
        library.recordMemberActivity(m3, 7);
        library.recordMemberActivity(m4, 2);
        library.recordMemberActivity(m5, 4);
        library.recordMemberActivity(m6, 6);
        library.recordMemberActivity(m7, 3);
        library.recordMemberActivity(m8, 1);

        // TRANSFERS
        library.addTransferRecord("CP-011 (Design Patterns) BR-001 -> BR-002");
        library.addTransferRecord("CP-004 (Sapiens) BR-001 -> BR-003");
        library.addTransferRecord("CP-014 (1984) BR-003 -> BR-001");

    }

}
