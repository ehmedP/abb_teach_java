package service;

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

    public boolean borrowBook(Member m, String bookId, String branchId, int currentDay) {
        // Implementation for borrowing a book
        return false;
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
        // Implementation for searching books
        return new ArrayList<>();
    }

    public void generateBranchReport(String branchId) {
        // Implementation for generating branch report
    }

    public List<Member> getTopActiveMembers(int topN) {
        // Implementation for getting top active members
        return new ArrayList<>();
    }

    public void processNotifications(LocalDate date) {

        memberNotifications.forEach((member, notifications) -> {

            if (notifications.isEmpty()) {
                System.out.println("No notifications for member: " + member.getName());
                return;
            }

            while (!notifications.isEmpty()) {
                DisplayHelper.printNotification(member, notifications.poll());
            }

        });

    }

}
