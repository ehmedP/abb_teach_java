package util;

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
}