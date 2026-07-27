package util;

import model.concrets.Branch;
import model.concrets.Member;
import model.concrets.Notification;

public final class DisplayHelper {

    private DisplayHelper() {
    }

    public static void printNotification(Member member, Notification notification) {
        System.out.printf("""
            ---------------------------------------------------------------
            Member : %s
            Type   : %s
            Message: %s
            ---------------------------------------------------------------
            """,
            member.getName(),
            notification.getType().getLabel(),
            notification.getMessage()
        );
    }

    public static void noNotifications(Member member) {
        System.out.println("No notifications for member: " + member.getName());
    }

    public static void printBranchReport(Branch branch, int totalBookCount, int uniqueGenreCount, int activeLoanCount, int lateLoanCount) {
        System.out.printf("""
            ---------------------------------------------------------------
            Branch Report for %s
            ---------------------------------------------------------------
            Total Book Count: %d
            Unique Genre Count: %d
            Active Loan Count: %d
            Late Loan Count: %d
            ---------------------------------------------------------------
            """, branch.getName(), totalBookCount, uniqueGenreCount, activeLoanCount, lateLoanCount);
    }

    public static void printBranchNotFound() {
        System.out.println("Branch not found.");
    }

    public static void printSearchKeywordEmpty() {
        System.out.println("Keyword cannot be empty.");
    }

    public static void printBlacklistWarn() {
        System.out.println("Member is in blacklist and cannot borrow books.");
    }

    public static void printBookNotAvailable() {
        System.out.println("Book not available, added to reservation queue.");
    }

    public static void printBookNotFound() {
        System.out.println("Book not found.");
    }

    public static void printLoanNotFound() {
        System.out.println("Loan not found.");
    }

    public static void printBookNotFoundInBranch() {
        System.out.println("Transfer failed: book not found in source branch.");
    }
}