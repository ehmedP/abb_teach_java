package service;

import enums.BookGenreEnum;
import model.concrets.*;
import util.DisplayHelper;

import java.time.LocalDate;
import java.util.*;

public class Library {

    private final Map<String, Branch> branches = new HashMap<>();

    private final Set<Book> books = new HashSet<>();

    private final Map<Member, List<Loan>> memberLoans = new HashMap<>();

    private final List<Loan> loanList = new ArrayList<>();

    private final Map<String, PriorityQueue<Reservation>> reservationsByBook = new HashMap<>();

    private final Map<Book, Integer> bookPopularityCounter = new HashMap<>();

    private final List<FineRecord> fineRecords = new ArrayList<>();

    private final Map<Member, Queue<Notification>> memberNotifications = new HashMap<>();

    private final Set<Member> blackList = new HashSet<>();

    private final Deque<String> lastTransfers = new ArrayDeque<>();

    private final Map<String, Set<Book>> booksByGenre = new TreeMap<>();

    private final Map<Member, Integer> memberActivityCounter = new HashMap<>();

    public Library() {

    }

    public boolean borrowBook(Member member, String bookId, String branchId, int currentDay) {

        if (blackList.contains(member)) {
            System.out.println("Member is in blacklist and cannot borrow books.");
            return false;
        }

        Branch branch = branches.get(branchId);

        if (branch == null) {
            System.out.println("Branch not found.");
            return false;
        }

        BookCopy bookCopy = branch.findAvailableBookCopy(bookId);

        if (bookCopy == null) {
            System.out.println("Book not available, added to reservation queue.");

            this.addReservation(
                    new Reservation(member, book1, 10)
            );

            return false;
        }

        if (true) {

        }

        return true;
    }

    public boolean returnBook(Member m, String loanId, int currentDay) {
        // Implementation for return a book
        return false;
    }

    public boolean transferBook(String bookId, String fromBranchId, String toBranchId) {
        // Implementation for transfer a book
        return false;
    }

    public List<Book> searchBooks(String keyword) {

        if (keyword.isBlank()) {
            System.out.println("Keyword cannot be empty.");
            return new ArrayList<>();
        }

        List<Book> result = new ArrayList<>();

        for (Book book : books) {

            for (String field : book.searchableFields()) {
                if (field.toLowerCase().contains(keyword.toLowerCase())) {
                    result.add(book);
                }
            }

        }

        return result;
    }

    public void generateBranchReport(String branchId) {

        Branch branch = branches.get(branchId);

        if (branch == null) {
            System.out.println("Branch not found.");
            return;
        }

        // TODO: this process isn't done
        int totalBookCount = 0,
                activeLoanCount = 0,
                lateLoanCount = 0;

        Set<BookGenreEnum> uniqueGenres = new HashSet<>();

        for (List<BookCopy> copies : branch.getBookCopies().values()) {
            totalBookCount += copies.size();

            for (BookCopy copy : copies) {
                uniqueGenres.add(copy.getBook().getGenre());
            }
        }

        DisplayHelper.printBranchReport(
                branch,
                totalBookCount,
                uniqueGenres.size(),
                activeLoanCount,
                lateLoanCount
        );
    }

    public List<Member> getTopActiveMembers(int topN) {

        if (topN == 0) {
            return new ArrayList<>();
        }

        List<Map.Entry<Member, Integer>> entries = new ArrayList<>(memberActivityCounter.entrySet());

        entries.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        List<Member> topMembers = new ArrayList<>();

        for (int i = 0; i < Math.min(topN, entries.size()); i++) {
            topMembers.add(entries.get(i).getKey());
        }

        return topMembers;
    }

    public void processNotifications(LocalDate date) {

        memberNotifications.forEach((member, notifications) -> {

            if (notifications.isEmpty()) {
                DisplayHelper.noNotifications(member);
                return;
            }

            while (!notifications.isEmpty()) {
                DisplayHelper.printNotification(member, notifications.poll());
            }

        });

    }

    public Book findBookByBookId(String bookId) {
        for (Book book : books) {
            if (book.getId().equals(bookId)) {
                return book;
            }
        }
        return null;
    }

    // Helper methods

    public void addBook(Book book) {
        books.add(book);
        booksByGenre.computeIfAbsent(book.getGenre().getLabel(), k -> new HashSet<>()).add(book);
    }

    public void addBranch(Branch branch) {
        branches.put(branch.getBranchId(), branch);
    }

    public void addLoan(Loan loan) {
        loanList.add(loan);
        memberLoans.computeIfAbsent(loan.getMember(), k -> new ArrayList<>()).add(loan);
    }

    public void addFineRecord(FineRecord record) {
        fineRecords.add(record);
    }

    public void addReservation(String bookId, Reservation reservation) {
        reservationsByBook.computeIfAbsent(bookId, k ->
                new PriorityQueue<>(
                        Comparator.comparing(Reservation::getPriorityScore)
                                .thenComparing(Reservation::getReservationDay)
                )
        ).add(reservation);
    }

    public void addNotification(Member member, Notification notification) {
        memberNotifications.computeIfAbsent(member, k -> new LinkedList<>()).add(notification);
    }

    public void recordMemberActivity(Member member, int count) {
        memberActivityCounter.merge(member, count, Integer::sum);
    }

    public void recordBookPopularity(Book book, int count) {
        bookPopularityCounter.merge(book, count, Integer::sum);
    }

    public void addTransferRecord(String record) {
        lastTransfers.push(record);
    }

}
