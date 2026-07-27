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

    public Integer calculateDueDay(Integer borrowDay) {
        Integer calculatedDay = 14;

        if (getType() == MembershipTypeEnum.PREMIUM) {
            calculatedDay = 21;
        }

        return borrowDay + calculatedDay;
    }

    @Override
    public Double getDiscountRate() {
        return 0.0;
    }

    @Override
    public Double calculateFine(Integer daysLate) {
        return switch (getType()) {
            case PREMIUM -> 0.2;
            case STUDENT -> 0.3;
            case REGULAR -> 0.5;
        };
    }

    @Override
    public void receiveNotification(Notification n) {

    }
}
