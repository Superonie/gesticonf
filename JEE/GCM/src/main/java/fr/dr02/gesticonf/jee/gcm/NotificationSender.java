package fr.dr02.gesticonf.jee.gcm;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Sender;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by damien on 08/02/14.
 */
public class NotificationSender {
    // Design-Pattern singleton
    private static NotificationSender ourInstance = new NotificationSender();

    public static NotificationSender getInstance() {
        return ourInstance;
    }

    private NotificationSender() {}

    // Liste des identifiants des devices qui vont être concernés par la notification
    private Collection<String> listRegistrationIds;
    public void setListRegistrationIds(Collection<String> listRegistrationIds) {
        this.listRegistrationIds = listRegistrationIds;
    }

    // API-Key du serveur d'envoi de la notification
    private final String serverKey = "AIzaSyDy8UV1L0udj9M4nqjTt2XojfO_usFEEAs";

    // Fonction d'envoi de la notification
    public void sendNotification() {
        // Pour chaque device concerné,
        for (String idRegistration : listRegistrationIds) {
            try {
                Sender sender = new Sender(serverKey);
                Message message = new Message.Builder().collapseKey("1")
                        //.timeToLive(3)
                        //.delayWhileIdle(true)
                        .addData("body","this text will be seen in notification bar!!")
                        //.addData("registration_id", idRegistration)
                        .build();
                sender.send(message, idRegistration, 1);
            } catch (IOException e) {}
        }
    }
}
