package sadh.sarthak.cool.com.nightwatch;

import android.content.Intent;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.widget.Toast;

import static android.app.Notification.EXTRA_TEXT;

public class NotificationService extends NotificationListenerService {

    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        Toast.makeText(NotificationService.this, sbn.getNotification().extras.getString(EXTRA_TEXT),Toast.LENGTH_LONG).show();
//        if(sbn.getPackageName().equalsIgnoreCase("com.whatsapp"))
//            Toast.makeText(NotificationService.this, sbn.getNotification().extras.getString(EXTRA_TEXT),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
    }
}