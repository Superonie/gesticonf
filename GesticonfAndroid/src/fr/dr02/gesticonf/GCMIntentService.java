package fr.dr02.gesticonf;

/**
 * Created by damien on 07/02/14.
 */


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import com.google.android.gcm.GCMBaseIntentService;

/**
 * Intent which is handling GCM messages and registrations. <br/>
 * <br/>
 * {@link GCMIntentService#onMessage} will handle message from GCM4Public server - show up a
 * notification and vibrate the phone. {@link GCMIntentService#onRegistered} will send the
 * registrationId and SENDER_ID constant to the server.<br/>
 * <br/>
 * In order this class to work, don't forget to copy the gcm.jar file to libs folder.
 *
 * @author Vilius Kraujutis
 * @since 2012-12-01
 */
public class GCMIntentService extends GCMBaseIntentService {


    public GCMIntentService()
    {
        super("899182185533");
    }

    @Override
    protected void onMessage(Context context, Intent i) {
        Bundle b = i.getExtras();
        String message = b.getString("body");

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification;
        Intent inte = new Intent(context,ConferenceActivity.class);

        //PendingIntent intent = null;// = YourPendingIntent;
        PendingIntent intent = PendingIntent.getActivity(context, 0, inte, android.content.Intent.FLAG_ACTIVITY_NEW_TASK);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
        {
            Notification.Builder builder = new Notification.Builder(context);
            builder.setSmallIcon(R.drawable.orateur);
            builder.setTicker(message);
            builder.setWhen(System.currentTimeMillis());
            builder.setContentTitle(context.getString(R.string.app_name));
            builder.setContentText(message);
            builder.setContentIntent(intent);
            notification = new Notification.BigTextStyle(builder).bigText(message).build();
        }
        else
        {
            notification = new Notification(R.drawable.orateur, message, System.currentTimeMillis());
            String title = context.getString(R.string.app_name);
            notification.setLatestEventInfo(context, title, message, intent);
        }


        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(0, notification);
     }

    @Override
    protected void onError(Context context, String s) {}

    @Override
    protected void onRegistered(Context context, String s) {}

    @Override
    protected void onUnregistered(Context context, String s) {}
}
