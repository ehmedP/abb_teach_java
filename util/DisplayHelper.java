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
}