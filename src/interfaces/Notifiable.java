package src.interfaces;

import src.model.concrets.Notification;

public interface Notifiable {

    void receiveNotification(Notification n);

}
