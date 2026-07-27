package src.service;

import src.model.concrets.Book;
import src.model.concrets.BookCopy;
import src.model.concrets.Branch;
import src.model.concrets.Loan;
import src.model.concrets.Member;
import src.model.concrets.Reservation;
import src.seed.LibrarySeeder;
import src.enums.CopyStatusEnum;

import java.util.*;

public class LmsManager {

    private final Scanner scanner = new Scanner(System.in);

    public void execute() {

        Library library = new Library();

        LibrarySeeder.seed(library);

        int choice;

        do {
            printHeader(library);
            printMenu();

            choice = readInt("Choice: ");

            if (choice == 0) {
                System.out.println("Exiting the system...");
                break;
            }

            handleChoice(choice, library);
        } while (true);
    }

    private void printHeader(Library library) {
        System.out.println();
        System.out.println("================================================================");
        System.out.println("           MULTI-BRANCH LIBRARY MANAGEMENT SYSTEM");
        System.out.println("================================================================");
        System.out.println("  System contains:");
        System.out.println("    - Books    : " + library.getAllBooks().size());
        System.out.println("    - Members  : " + library.getAllMembers().size());
        System.out.println("    - Branches : " + library.getAllBranches().size());
        System.out.println("    - Loans    : " + library.getAllLoans().size());
        System.out.println("================================================================");
    }

    private void printMenu() {
        System.out.println("--- OPERATIONS ---");
        System.out.println(" 1. Borrow a Book");
        System.out.println(" 2. Return a Book");
        System.out.println(" 3. Transfer Book Between Branches");
        System.out.println(" 4. Search Books by Keyword");
        System.out.println(" 5. Generate Branch Report");
        System.out.println(" 6. Top Active Members");
        System.out.println(" 7. Process Notifications");
        System.out.println("--- INFORMATION ---");
        System.out.println(" 8. Show All Books");
        System.out.println(" 9. Show All Members");
        System.out.println("10. Show All Branches");
        System.out.println("11. Show Active Loans");
        System.out.println("12. Show Transfer History");
        System.out.println("13. Show Blacklist");
        System.out.println("14. Show Reservation Queues");
        System.out.println("---");
        System.out.println(" 0. Exit");
        System.out.println("--------------------------------");
    }

    private void handleChoice(int choice, Library library) {
        switch (choice) {
            case 1 -> borrowBookMenu(library);
            case 2 -> returnBookMenu(library);
            case 3 -> transferBookMenu(library);
            case 4 -> searchBooksMenu(library);
            case 5 -> branchReportMenu(library);
            case 6 -> topMembersMenu(library);
            case 7 -> processNotificationsMenu(library);
            case 8 -> showBooks(library);
            case 9 -> showMembers(library);
            case 10 -> showBranches(library);
            case 11 -> showActiveLoans(library);
            case 12 -> showTransferHistory(library);
            case 13 -> showBlacklist(library);
            case 14 -> showReservations(library);
            default -> System.out.println("Invalid choice. Please select a number between 0 and 14.");
        }
    }

    private void borrowBookMenu(Library library) {
        System.out.println("--- BORROW A BOOK ---");

        Member member = library.getMemberById(
                readInt("Enter Member ID: ")
        );

        if (member == null) {
            System.out.println("Member not found.");
            return;
        }

        int bookId = readInt("Enter Book ID: ");
        int branchId = readInt("Enter Branch ID: ");
        int currentDay = readInt("Enter current day (integer): ");

        if (library.borrowBook(member, bookId, branchId, currentDay)) {
            System.out.println("Book borrowed successfully.");
        } else {
            System.out.println("Borrow failed.");
        }
    }

    private void returnBookMenu(Library library) {
        System.out.println("--- RETURN A BOOK ---");

        List<Loan> allLoans = library.getAllLoans();

        if (allLoans.isEmpty()) {
            System.out.println("No loans in the system.");
            return;
        }

        System.out.println("Active Loans:");
        showLoansCompact(allLoans);

        int loanId = readInt("Enter Loan ID to return: ");
        int memberId = readInt("Enter Member ID: ");

        Member member = library.getMemberById(memberId);
        if (member == null) {
            System.out.println("Member not found.");
            return;
        }

        int currentDay = readInt("Enter current day: ");
        library.returnBook(member, loanId, currentDay);
    }

    private void transferBookMenu(Library library) {
        System.out.println("--- TRANSFER BOOK ---");

        int bookId = readInt("Enter Book ID: ");
        int fromBranch = readInt("Enter source Branch ID: ");
        int toBranch = readInt("Enter destination Branch ID: ");

        if (fromBranch == toBranch) {
            System.out.println("Source and destination branches cannot be the same.");
            return;
        }

        if (library.transferBook(bookId, fromBranch, toBranch)) {
            System.out.println("Transfer successful.");
        } else {
            System.out.println("Transfer failed.");
        }
    }

    private void searchBooksMenu(Library library) {
        System.out.println("--- SEARCH BOOKS ---");

        String keyword = readString("Enter search prompt: ");
        List<Book> results = library.searchBooks(keyword);

        if (results.isEmpty()) {
            System.out.println("No books found matching: " + keyword);
            return;
        }

        System.out.println("Found " + results.size() + " book(s):");
        for (Book book : results) {
            System.out.println("  [" + book.getId() + "] " + book.getTitle()
                    + " | " + book.getAuthor()
                    + " | " + book.getGenre().getLabel());
        }
    }

    private void branchReportMenu(Library library) {
        System.out.println("--- BRANCH REPORT ---");
        library.generateBranchReport(readInt("Enter Branch ID: "));
    }

    private void topMembersMenu(Library library) {
        System.out.println("--- TOP ACTIVE MEMBERS ---");

        int topN = readInt("Enter N (how many top members to show): ");
        List<Member> topMembers = library.getTopActiveMembers(topN);

        if (topMembers.isEmpty()) {
            System.out.println("No member activity data available.");
            return;
        }

        System.out.println("Top " + topN + " Active Members:");
        int rank = 1;
        for (Member member : topMembers) {
            System.out.println("  " + rank + ". " + member.getName()
                    + " (" + member.getType().getLabel() + ")");
            rank++;
        }
    }

    private void processNotificationsMenu(Library library) {
        System.out.println("--- PROCESS NOTIFICATIONS ---");
        library.processNotifications(readInt("Enter current day: "));
    }

    private void showBooks(Library library) {
        System.out.println("--- ALL BOOKS ---");

        List<Book> allBooks = library.getAllBooks();
        if (allBooks.isEmpty()) {
            System.out.println("No books in the system.");
            return;
        }

        System.out.printf("%-4s %-35s %-22s %-15s %-6s%n",
                "ID", "Title", "Author", "Genre", "ISBN");
        System.out.println("--------------------------------------------------------------------");

        for (Book book : allBooks) {
            System.out.printf("%-4d %-35s %-22s %-15s %-6d%n",
                    book.getId(),
                    truncate(book.getTitle(), 34),
                    truncate(book.getAuthor(), 21),
                    book.getGenre().getLabel(),
                    book.getIsbn());
        }
    }

    private void showMembers(Library library) {
        System.out.println("--- ALL MEMBERS ---");

        Collection<Member> allMembers = library.getAllMembers();
        if (allMembers.isEmpty()) {
            System.out.println("No members in the system.");
            return;
        }

        System.out.printf("%-4s %-10s %-22s %-10s%n", "ID", "Name", "Email", "Type");
        System.out.println("----------------------------------------------------------");

        for (Member member : allMembers) {
            boolean blacklisted = library.isBlacklisted(member);

            if (blacklisted) {
                System.out.printf("%-4d %-10s %-22s %-10s %s%n",
                        member.getId(),
                        truncate(member.getName(), 9),
                        truncate(member.getEmail(), 21),
                        member.getType().getLabel(),
                        "[BLACKLISTED]");
            } else {
                System.out.printf("%-4d %-10s %-22s %-10s%n",
                        member.getId(),
                        truncate(member.getName(), 9),
                        truncate(member.getEmail(), 21),
                        member.getType().getLabel());
            }
        }
    }

    private void showBranches(Library library) {
        System.out.println("--- ALL BRANCHES ---");

        List<Branch> allBranches = library.getAllBranches();
        if (allBranches.isEmpty()) {
            System.out.println("No branches in the system.");
            return;
        }

        for (Branch branch : allBranches) {
            System.out.println("[" + branch.getId() + "] " + branch.getName() + " - " + branch.getAddress());

            Map<Integer, List<BookCopy>> copiesByBookId = branch.getBookCopies();
            if (copiesByBookId.isEmpty()) {
                System.out.println("     No books at this branch.");
                continue;
            }

            for (Map.Entry<Integer, List<BookCopy>> entry : copiesByBookId.entrySet()) {
                int availableCount = 0;
                for (BookCopy copy : entry.getValue()) {
                    if (copy.getStatus() == CopyStatusEnum.AVAILABLE) {
                        availableCount++;
                    }
                }

                System.out.println("     Book ID " + entry.getKey() + ": "
                        + truncate(entry.getValue().get(0).getBook().getTitle(), 30)
                        + " | Copies: " + entry.getValue().size()
                        + " | Available: " + availableCount);
            }
        }
    }

    private void showActiveLoans(Library library) {
        System.out.println("--- ACTIVE LOANS ---");

        List<Loan> allLoans = library.getAllLoans();
        boolean hasActiveLoan = false;

        System.out.printf("%-4s %-18s %-10s %-10s %-10s%n",
                "ID", "Book", "Member", "Borrow Day", "Due Day");
        System.out.println("--------------------------------------------------------------------");

        for (Loan loan : allLoans) {
            if (loan.isNotReturned()) {
                hasActiveLoan = true;
                System.out.printf("%-4d %-18s %-10s %-10d %-10d%n",
                        loan.getId(),
                        truncate(loan.getBookCopy().getBook().getTitle(), 17),
                        loan.getMember().getName(),
                        loan.getBorrowDay(),
                        loan.getDueDay());
            }
        }

        if (!hasActiveLoan) {
            System.out.println("No active loans.");
        }
    }

    private void showTransferHistory(Library library) {
        System.out.println("--- TRANSFER HISTORY (Last 5) ---");

        List<String> history = library.getTransferHistoryList();
        if (history.isEmpty()) {
            System.out.println("No transfer records.");
            return;
        }

        for (String record : history) {
            System.out.println("  " + record);
        }
    }

    private void showBlacklist(Library library) {
        System.out.println("--- BLACKLIST ---");

        Set<Member> blacklistedMembers = library.getBlacklistedMembers();
        if (blacklistedMembers.isEmpty()) {
            System.out.println("Blacklist is empty.");
            return;
        }

        for (Member member : blacklistedMembers) {
            System.out.println("  [" + member.getId() + "] " + member.getName() + " - " + member.getEmail());
        }
    }

    private void showReservations(Library library) {
        System.out.println("--- RESERVATION QUEUES ---");

        Map<Integer, PriorityQueue<Reservation>> reservations = library.getReservationsByBook();
        if (reservations.isEmpty()) {
            System.out.println("No reservations.");
            return;
        }

        for (Map.Entry<Integer, PriorityQueue<Reservation>> entry : reservations.entrySet()) {
            System.out.println("  Book ID " + entry.getKey() + " (" + entry.getValue().size() + " queued):");

            PriorityQueue<Reservation> queueCopy = new PriorityQueue<>(entry.getValue());
            while (!queueCopy.isEmpty()) {
                Reservation r = queueCopy.poll();
                System.out.println("     -> [" + r.getMember().getName()
                        + "] priority=" + r.getPriorityScore()
                        + " day=" + r.getReservationDay());
            }
        }
    }

    private void showLoansCompact(List<Loan> loans) {
        System.out.printf("%-4s %-20s %-10s %-10s %-10s %-8s%n",
                "ID", "Book", "Member", "Borrow", "Due", "Status");
        System.out.println("----------------------------------------------------------------");

        for (Loan loan : loans) {
            String status = loan.isNotReturned() ? "ACTIVE" : "RETURNED";

            System.out.printf("%-4d %-20s %-10s %-10d %-10d %-8s%n",
                    loan.getId(),
                    truncate(loan.getBookCopy().getBook().getTitle(), 19),
                    loan.getMember().getName(),
                    loan.getBorrowDay(),
                    loan.getDueDay(),
                    status);
        }
    }

    private int readInt(String prompt) {
        System.out.print(prompt);

        while (!scanner.hasNextInt()) {

            System.out.println("Invalid input: '" + scanner.next() + "' is not a number.");
            System.out.print(prompt);
        }

        int value = scanner.nextInt();

        scanner.nextLine();

        return value;
    }

    private String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private String truncate(String text, int maximumLength) {
        if (text.length() <= maximumLength) {
            return text;
        }

        return text.substring(0, maximumLength - 3) + "...";
    }

}
