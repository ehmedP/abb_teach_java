package model.concrets;

import interfaces.Finable;
import interfaces.Notifiable;
import model.abstracts.LibraryUser;

public class Member extends LibraryUser implements Finable, Notifiable {

    public Member(Integer id, String name, String email) {
        super(id, name, email);
    }

    @Override
    public double getDiscountRate() {
        return 0;
    }

    @Override
    public double calculateFine(int daysLate) {
        return 0;
    }

    @Override
    public void receiveNotification(Notification n) {

    }
}
