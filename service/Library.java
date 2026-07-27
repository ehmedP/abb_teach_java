package service;

import enums.BookGenreEnum;
import enums.NotificationTypeEnum;
import model.concrets.*;
import util.DisplayHelper;

import java.util.*;

public class Library {

    // branchId -> Branch
    private final Map<Integer, Branch> branches = new HashMap<>();

    private final Set<Book> books = new HashSet<>();

    private final Map<Member, List<Loan>> memberLoans = new HashMap<>();

    private final List<Loan> loanList = new ArrayList<>();

    // bookId -> List of Reservations
    private final Map<Integer, PriorityQueue<Reservation>> reservationsByBook = new HashMap<>();

    // book -> book popularity count
    private final Map<Book, Integer> bookPopularityCounter = new HashMap<>();

    private final List<FineRecord> fineRecords = new ArrayList<>();

    private final Map<Member, Queue<Notification>> memberNotifications = new HashMap<>();

    private final Set<Member> blackList = new HashSet<>();

    private final Deque<String> transferHistory = new ArrayDeque<>();

    private final Map<String, Set<Book>> booksByGenre = new TreeMap<>();

    private final Map<Member, Integer> memberActivityCounter = new HashMap<>();

    public Library() {

    }

    public boolean borrowBook(Member member, Integer bookId, Integer branchId, Integer currentDay) {

        Book book = findBookByBookId(bookId);

        if (book == null) {
            DisplayHelper.printBookNotFound();
            return false;
        }

        if (blackList.contains(member)) {
            DisplayHelper.printBlacklistWarn();
            return false;
        }

        Branch branch = branches.get(branchId);

        if (branch == null) {
            DisplayHelper.printBranchNotFound();
            return false;
        }

        BookCopy bookCopy = branch.findAvailableBookCopy(bookId);

        if (bookCopy == null) {
            DisplayHelper.printBookNotAvailable();

            this.addReservation(new Reservation(member, book, currentDay));

            return false;
        }

        bookCopy.markAsBorrowed();

        addLoan(
                new Loan(bookCopy, member, currentDay)
        );

        bookPopularityCounter.put(book, bookPopularityCounter.get(book) + 1);

        return true;
    }

    public void returnBook(Member member, Integer loanId, int currentDay) {

        Loan loan = this.loanList.get(loanId);

        if (loan == null) {
            DisplayHelper.printLoanNotFound();
            return;
        }

        loan.markAsReturned();

        if (currentDay > loan.getDueDay()) {
            Integer daysLate = currentDay - loan.getDueDay();

            this.addFineRecord(new FineRecord(member, daysLate * member.calculateFine(daysLate), daysLate));

            if (getLateCountByMember(member) > 3) {
                addBlackListMember(member);
                notifyBlackListMember(member, currentDay);
            }
        }

        Reservation reservation = pollReservationByBook(loan.getBookCopy().getBook());

        if (reservation != null) {

            addLoan(new Loan(loan.getBookCopy(), member, currentDay));
            notifyReservationReady(member, currentDay);
        } else {
            loan.getBookCopy().markAsAvailable();
        }

    }

    public boolean transferBook(Integer bookId, Integer fromBranchId, Integer toBranchId) {

        Branch fromBranch = branches.get(fromBranchId);
        Branch toBranch = branches.get(toBranchId);

        if (fromBranch == null || toBranch == null) {
            DisplayHelper.printBranchNotFound();
            return false;
        }

        BookCopy bookCopy = fromBranch.getBookCopies().get(bookId).getFirst();

        if (bookCopy == null) {
            DisplayHelper.printBookNotFoundInBranch();
            return false;
        }

        bookCopy.markAsInTransit();

        bookCopy.setBranchId(toBranchId);

        fromBranch.getBookCopies().get(bookId).remove(bookCopy);
        toBranch.getBookCopies().get(bookId).add(bookCopy);

        bookCopy.markAsAvailable();

        addTransferRecord("Book " + bookCopy.getBook().getTitle() + ": " + fromBranchId + " to branch " + toBranchId);

        return true;
    }

    public List<Book> searchBooks(String keyword) {

        if (keyword.isBlank()) {
            DisplayHelper.printSearchKeywordEmpty();
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

    public void generateBranchReport(Integer branchId) {

        Branch branch = branches.get(branchId);

        if (branch == null) {
            DisplayHelper.printBranchNotFound();
            return;
        }

        int totalBookCount = 0,
                activeLoanCount = 0;

        Set<BookGenreEnum> uniqueGenres = new HashSet<>();

        for (List<BookCopy> copies : branch.getBookCopies().values()) {
            totalBookCount += copies.size();

            for (BookCopy copy : copies) {
                uniqueGenres.add(copy.getBook().getGenre());
            }
        }

        for (Loan loan : loanList) {

            if (loan.isNotReturned()) {
                activeLoanCount++;
            }
        }

        DisplayHelper.printBranchReport(
                branch,
                totalBookCount,
                uniqueGenres.size(),
                activeLoanCount,
                fineRecords.size()
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

    public void processNotifications(Integer currentDay) {

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

    // Helper methods

    public Book findBookByBookId(Integer bookId) {
        for (Book book : books) {
            if (book.getId().equals(bookId)) {
                return book;
            }
        }

        return null;
    }

    private int getLateCountByMember(Member member) {
        int count = 0;

        for (FineRecord fineRecord : fineRecords) {
            if (fineRecord.getMember().equals(member)) {
                count++;
            }
        }

        return count;
    }

    private Reservation pollReservationByBook(Book book) {
        return reservationsByBook.get(book.getId()).poll();
    }

    // Add methods

    public void addBook(Book book) {
        books.add(book);
        booksByGenre.computeIfAbsent(book.getGenre().getLabel(), k -> new HashSet<>()).add(book);
    }

    public void addBlackListMember(Member member) {
        blackList.add(member);
    }

    public void addBranch(Branch branch) {
        branches.put(branch.getId(), branch);
    }

    public void addLoan(Loan loan) {
        loanList.add(loan);
        memberLoans.computeIfAbsent(loan.getMember(), k -> new ArrayList<>()).add(loan);
    }

    public void addFineRecord(FineRecord record) {
        fineRecords.add(record);
    }

    public void addReservation(Reservation reservation) {
        reservationsByBook.computeIfAbsent(reservation.getBook().getId(), k ->
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

        if (transferHistory.size() >= 5) {
            transferHistory.removeLast();
        }

        transferHistory.push(record);
    }

    // Notification methods

    public void notifyReservationReady(Member member, Integer currentDay) {
        addNotification(
                member,
                new Notification(
                        NotificationTypeEnum.RESERVATION_READY,
                        "A reserved book is now available for pickup.",
                        currentDay
                )
        );
    }

    public void notifyBlackListMember(Member member, Integer currentDay) {
        addNotification(
                member,
                new Notification(
                        NotificationTypeEnum.BLACKLIST_WARNING,
                        "You have been added to the blacklist due to excessive late returns.",
                        currentDay
                )
        );
    }

}
