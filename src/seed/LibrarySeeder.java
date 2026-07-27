package src.seed;

import src.enums.BookGenreEnum;
import src.enums.CopyStatusEnum;
import src.enums.MembershipTypeEnum;
import src.enums.NotificationTypeEnum;
import src.model.concrets.*;
import src.service.Library;

import java.util.*;

public class LibrarySeeder {

    public static void seed(Library library) {

        // ========== BOOKS ==========
        Book book1 = new Book("The Great Gatsby", "F. Scott Fitzgerald", BookGenreEnum.FICTION, 1001);
        Book book2 = new Book("A Brief History of Time", "Stephen Hawking", BookGenreEnum.SCIENCE, 1002);
        Book book3 = new Book("The Art of Computer Programming", "Donald Knuth", BookGenreEnum.TECHNOLOGY, 1003);
        Book book4 = new Book("World War II: A History", "Antony Beevor", BookGenreEnum.HISTORY, 1004);
        Book book5 = new Book("1984", "George Orwell", BookGenreEnum.FICTION, 1005);
        Book book6 = new Book("The Selfish Gene", "Richard Dawkins", BookGenreEnum.SCIENCE, 1006);

        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);
        library.addBook(book4);
        library.addBook(book5);
        library.addBook(book6);

        // ========== MEMBERS ==========
        Member alice = new Member("Alice", "alice@email.com", MembershipTypeEnum.PREMIUM);
        Member bob = new Member("Bob", "bob@email.com", MembershipTypeEnum.REGULAR);
        Member charlie = new Member("Charlie", "charlie@email.com", MembershipTypeEnum.STUDENT);
        Member diana = new Member("Diana", "diana@email.com", MembershipTypeEnum.REGULAR);
        Member eve = new Member("Eve", "eve@email.com", MembershipTypeEnum.STUDENT);

        library.addMember(alice);
        library.addMember(bob);
        library.addMember(charlie);
        library.addMember(diana);
        library.addMember(eve);

        // ========== BRANCHES (created with empty maps first to get stable IDs) ==========
        Branch central = new Branch("Central Library", "123 Main St, Downtown", new HashMap<>());
        Branch north = new Branch("North Branch", "456 Oak Ave, Uptown", new HashMap<>());
        Branch south = new Branch("South Branch", "789 Pine Rd, Suburb", new HashMap<>());

        // Known IDs: central=1, north=2, south=3 (sequential auto-increment)

        // ========== BOOK COPIES ==========
        // Central Library copies
        BookCopy centralGatsby1 = new BookCopy(book1, central.getId(), CopyStatusEnum.AVAILABLE);
        BookCopy centralGatsby2 = new BookCopy(book1, central.getId(), CopyStatusEnum.BORROWED);
        BookCopy centralTime1 = new BookCopy(book2, central.getId(), CopyStatusEnum.AVAILABLE);
        BookCopy centralTime2 = new BookCopy(book2, central.getId(), CopyStatusEnum.AVAILABLE);
        BookCopy centralArt1 = new BookCopy(book3, central.getId(), CopyStatusEnum.BORROWED);
        BookCopy centralWW2_1 = new BookCopy(book4, central.getId(), CopyStatusEnum.AVAILABLE);
        BookCopy central1984_1 = new BookCopy(book5, central.getId(), CopyStatusEnum.AVAILABLE);
        BookCopy centralGene1 = new BookCopy(book6, central.getId(), CopyStatusEnum.AVAILABLE);

        // North Branch copies
        BookCopy northGatsby1 = new BookCopy(book1, north.getId(), CopyStatusEnum.AVAILABLE);
        BookCopy northTime1 = new BookCopy(book2, north.getId(), CopyStatusEnum.BORROWED);
        BookCopy northArt1 = new BookCopy(book3, north.getId(), CopyStatusEnum.AVAILABLE);
        BookCopy north1984_1 = new BookCopy(book5, north.getId(), CopyStatusEnum.AVAILABLE);

        // South Branch copies
        BookCopy southGatsby1 = new BookCopy(book1, south.getId(), CopyStatusEnum.IN_TRANSIT);
        BookCopy southWW2_1 = new BookCopy(book4, south.getId(), CopyStatusEnum.AVAILABLE);
        BookCopy south1984_1 = new BookCopy(book5, south.getId(), CopyStatusEnum.AVAILABLE);
        BookCopy southGene1 = new BookCopy(book6, south.getId(), CopyStatusEnum.AVAILABLE);

        // Build and set branch maps
        Map<Integer, List<BookCopy>> centralMap = new HashMap<>();
        centralMap.put(book1.getId(), new ArrayList<>(Arrays.asList(centralGatsby1, centralGatsby2)));
        centralMap.put(book2.getId(), new ArrayList<>(Arrays.asList(centralTime1, centralTime2)));
        centralMap.put(book3.getId(), new ArrayList<>(List.of(centralArt1)));
        centralMap.put(book4.getId(), new ArrayList<>(List.of(centralWW2_1)));
        centralMap.put(book5.getId(), new ArrayList<>(List.of(central1984_1)));
        centralMap.put(book6.getId(), new ArrayList<>(List.of(centralGene1)));
        central.setBookCopies(centralMap);

        Map<Integer, List<BookCopy>> northMap = new HashMap<>();
        northMap.put(book1.getId(), new ArrayList<>(List.of(northGatsby1)));
        northMap.put(book2.getId(), new ArrayList<>(List.of(northTime1)));
        northMap.put(book3.getId(), new ArrayList<>(List.of(northArt1)));
        northMap.put(book5.getId(), new ArrayList<>(List.of(north1984_1)));
        north.setBookCopies(northMap);

        Map<Integer, List<BookCopy>> southMap = new HashMap<>();
        southMap.put(book1.getId(), new ArrayList<>(List.of(southGatsby1)));
        southMap.put(book4.getId(), new ArrayList<>(List.of(southWW2_1)));
        southMap.put(book5.getId(), new ArrayList<>(List.of(south1984_1)));
        southMap.put(book6.getId(), new ArrayList<>(List.of(southGene1)));
        south.setBookCopies(southMap);

        library.addBranch(central);
        library.addBranch(north);
        library.addBranch(south);

        // ========== LOANS (added directly since borrowBook has known bugs) ==========
        // Active loans (not returned)
        Loan loan1 = new Loan(centralArt1, alice, 1);
        library.addLoan(loan1);

        Loan loan2 = new Loan(centralTime1, bob, 3);
        library.addLoan(loan2);

        Loan loan3 = new Loan(south1984_1, diana, 5);
        library.addLoan(loan3);

        Loan loan4 = new Loan(northTime1, charlie, 8);
        library.addLoan(loan4);

        // ========== FINE RECORDS (to test blacklist threshold) ==========
        // Bob already has 3 late returns
        FineRecord fine1 = new FineRecord(bob, 2.5, "Late return: A Brief History of Time (5 days late)", 8);
        FineRecord fine2 = new FineRecord(bob, 1.5, "Late return: 1984 (3 days late)", 10);
        FineRecord fine3 = new FineRecord(bob, 3.0, "Late return: The Selfish Gene (6 days late)", 14);
        library.addFineRecord(fine1);
        library.addFineRecord(fine2);
        library.addFineRecord(fine3);

        // ========== RESERVATIONS ==========
        library.addReservation(new Reservation(charlie, book2, 7));
        library.addReservation(new Reservation(eve, book2, 8));
        library.addReservation(new Reservation(bob, book1, 9));

        // ========== NOTIFICATIONS ==========
        library.addNotification(alice, new Notification(NotificationTypeEnum.DUE_SOON, "Book 'The Art of Computer Programming' is due in 3 days.", 19));
        library.addNotification(charlie, new Notification(NotificationTypeEnum.RESERVATION_READY, "Your reserved book 'A Brief History of Time' is now available.", 12));
        library.addNotification(eve, new Notification(NotificationTypeEnum.OVERDUE, "Book 'The Selfish Gene' is overdue. Please return immediately.", 15));

        // ========== TRANSFER HISTORY ==========
        library.addTransferRecord("The Great Gatsby: Central Library -> North Branch");
        library.addTransferRecord("1984: North Branch -> South Branch");
        library.addTransferRecord("The Selfish Gene: Central Library -> South Branch");

        // ========== POPULARITY & ACTIVITY ==========
        library.recordBookPopularity(book1, 10);
        library.recordBookPopularity(book5, 8);
        library.recordBookPopularity(book2, 5);

        library.recordMemberActivity(alice, 5);
        library.recordMemberActivity(bob, 3);
        library.recordMemberActivity(charlie, 7);
        library.recordMemberActivity(diana, 2);
        library.recordMemberActivity(eve, 4);
    }

}
