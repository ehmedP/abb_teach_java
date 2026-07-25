package model.concrets;

import enums.MembershipTypeEnum;
import interfaces.Finable;
import interfaces.Notifiable;
import model.abstracts.LibraryUser;

public class Member extends LibraryUser implements Finable, Notifiable {

    private static int nextId = 1;

    private MembershipTypeEnum type;

    public Member(String name, String email, MembershipTypeEnum type) {
        super(nextId++, name, email);
        this.type = type;
    }

    public MembershipTypeEnum getType() {
        return type;
    }

    public void setType(MembershipTypeEnum type) {
        this.type = type;
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
